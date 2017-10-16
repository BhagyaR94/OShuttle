package core.app;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhagyar.oshuttle.R;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import application.common.Functions;
import core.eventbus.ApiResultEvent;
import core.eventbus.NoInternetEvent;
import core.util.Utility;
import de.greenrobot.event.EventBus;
import syncing.helper.DownloadFromServer;
import syncing.helper.UploadToServer;
import syncing.interfaces.SyncCallbackInterface;
import syncing.model.SyncList;

public class SyncPanel extends AppCompatActivity {
    private Functions functions;
    private DownloadFromServer downloadFromServer;
    private UploadToServer uploadToServer;
    private FloatingActionButton btButton;
    private ListView listView;
    private BaseAdapter baseAdapter ;
    private ArrayList<SyncList> resObjects;
    private TextView lastSyncStatus;
    private ImageView btn_back;
    private Utility utility;


    /*Process watcher*/
    private int elementCount =0;
    private int totalProcessCount = 0;
    private int totalEndProcessCount =0;
    boolean isProcessFinish= true;
    private ProgressBar progressBar;
    private EventBus busEvent;
    private SyncCallbackInterface syncCallbackInterface;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sync_panel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setIcon(R.drawable.carriage);
            getSupportActionBar().setTitle("Synchronize");

//            listView = (ListView) findViewById(R.id.syncPanelListView);
//            progressBar = (ProgressBar) findViewById(R.id.totalProgressBar);
            //lastSyncStatus = (TextView) findViewById(R.id.LastSyncStatus);
            resObjects=new ArrayList<SyncList>();
            functions = new Functions(SyncPanel.this);
            downloadFromServer = new DownloadFromServer(getApplicationContext());
            uploadToServer = new UploadToServer(getApplicationContext());
            utility = new Utility(getApplicationContext());
           /* SET Bus Event */
        busEvent = EventBus.getDefault();
        if (!busEvent.isRegistered(this)) {
            busEvent.register(this);
        }


//        btButton = (FloatingActionButton) findViewById(R.id.btDownload);

        /* Set Info */
        //lastSyncStatus.setText(functions.getLastSyncTime());

        btButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(utility.isInternetAvailable()){
                    btButton.setEnabled(false);
                    btButton.setMax(0);
                    resObjects=new ArrayList<SyncList>();
                    downloadFromServer.setSyncResultList(new ArrayList<SyncList>());
                    if(isProcessFinish){
                        isProcessFinish=false;


                        setListView();
                        syncCallbackInterface = new SyncCallbackInterface() {

                            @Override
                            public void liveDownloadCount(final ArrayList<SyncList> resObjects1) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        try {

                                            resObjects.clear();
                                            resObjects.addAll(resObjects1);
                                            baseAdapter.notifyDataSetChanged();
                                            ProgressWatcher(resObjects);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        };


                        uploadToServer.setCallback(syncCallbackInterface);
                        //uploadToServer.upload();



                        final Timer timer = new Timer();

                        /* Time space is 2 sec */
                        TimerTask myTask = new TimerTask() {
                            @Override
                            public void run() {

                                if((downloadFromServer.getTotalMethodCount() == downloadFromServer.getExecutedMethodCount())){
                                    // &&
                                    //uploadToServer.getTotalMethodCount() == uploadToServer.getExecutedMethodCount()
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            downloadFromServer.setExecutedMethodCount(0);
                                            btButton.setEnabled(true);
                                            btButton.setProgress(0,true);
                                            isProcessFinish = true;
                                            progressBar.setProgress(0);
                                            downloadFromServer.setSyncResultList(new ArrayList<SyncList>());
                                           // uploadToServer.setExecutedMethodCount(0);
                                           // uploadToServer.setTotalMethodCount(0);

                                            //checkForUpdates();


                                        }

                                    });
                                    timer.cancel();
                                }


                            }
                        };

                        timer.schedule(myTask, 2000, 5000);

                        // }else{

                    }

                }else{
                    Toast.makeText(SyncPanel.this,"Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /*btn_back = (ImageView)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!isProcessFinish) {
                    inProgressDialog();
                }else{
                    finish();
                }
            }
        });*/
    }

    /*private void checkForUpdates() {
        //final VersionResponse versionResponse = downloadFromServer.checkUpdate(BuildConfig.VERSION_NAME);

        final Call<VersionResponse> versionResponseCall = ApiClient.get().checkUpdate(BuildConfig.VERSION_NAME);
        versionResponseCall.enqueue(new Callback<VersionResponse>() {

            @Override
            public void onResponse(Call<VersionResponse> dealerCall, final Response<VersionResponse> dealerResponse) {

                final VersionResponse body = dealerResponse.body();

                if(body.getStatus() == 1 && body.getVersionCode() != null &&  !body.getVersionCode().equals("0")){

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SyncPanel.this);
                    alertDialogBuilder.setTitle("Update");
                    alertDialogBuilder
                            .setMessage("A New Version Is Available (" + body.getVersionCode() + ") Please Download And Install" )//
                            .setCancelable(false)
                            .setNegativeButton("ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("" + body.getApkUrl() + ""));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            dialog.dismiss();
                                            finish();

                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                }


            }

            @Override
            public void onFailure(Call<VersionResponse> call, Throwable throwable) {
                throwable.printStackTrace();
            }

        });








    }*/

    private void ProgressWatcher(ArrayList<SyncList> progressList) {
        for (SyncList syncList : progressList){
            if(syncList.getTotalCount() == null || syncList.getDataInsertCount() == null || syncList.getDataUpdatedCount() == null){
                return;
            }
            totalProcessCount += Integer.valueOf(syncList.getTotalCount());
            totalEndProcessCount += (Integer.valueOf(syncList.getDataInsertCount())) + (Integer.valueOf(syncList.getDataUpdatedCount()));
        }
        progressBar.setMax(totalProcessCount);
        btButton.setMax(totalProcessCount);
        progressBar.setProgress(totalEndProcessCount);
        btButton.setProgress(totalEndProcessCount,true);




    }

    private void setListView() {
        try {
            listView.setAdapter(baseAdapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return resObjects.size();
                }

                @Override
                public SyncList getItem(int i) {
                    return resObjects.get(i);
                }

                @Override
                public long getItemId(int i) {
                    return i;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    final SyncList syncList = getItem(i);
                    final SyncListHolder syncListHolder;

                    if (view == null) {
                        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                        view = layoutInflater.inflate(R.layout.sync_panel_custom_row_view, viewGroup, false);
//                        syncListHolder = new SyncListHolder();
//                        syncListHolder.elementName   = (TextView) view.findViewById(R.id.syncViewElementName);
//                        syncListHolder.itemPreview   = (TextView) view.findViewById(R.id.syncViewElementPreview);
//                        syncListHolder.insetCount    = (TextView) view.findViewById(R.id.syncViewNewItem);
//                        syncListHolder.updatedCount  = (TextView) view.findViewById(R.id.syncViewUpdatedItem);
//                        syncListHolder.totalCount    = (TextView) view.findViewById(R.id.syncViewElementTotalItems);
//                        syncListHolder.progressBar   = (ProgressBar) view.findViewById(R.id.syncProgressBar);
//                        syncListHolder.progressLabel = (TextView) view.findViewById(R.id.syncViewSyncProgressLabel);
//                        syncListHolder.linearLayout = (LinearLayout) view.findViewById(R.id.lll);
//                        view.setTag(syncListHolder);
                    } else {
                        syncListHolder = (SyncListHolder) view.getTag();
                    }

//                    syncListHolder.elementName.setText(syncList.getTypeOfElement());
//                    syncListHolder.itemPreview.setText(syncList.getDataPreview());
//                    syncListHolder.insetCount.setText(syncList.getDataInsertCount());
//                    syncListHolder.updatedCount.setText(syncList.getDataUpdatedCount());
//
//                    syncListHolder.totalCount.setText(syncList.getTotalCount());

                    if(syncList.getDataInsertCount() != null && syncList.getDataUpdatedCount() != null  ) {
                        if ((Integer.parseInt(syncList.getTotalCount())) == (Integer.parseInt(syncList.getDataInsertCount())+ Integer.parseInt(syncList.getDataUpdatedCount()) )) {//+ Integer.parseInt(syncList.getDataUpdatedCount())
                            if(syncList.getSuccessOrFail() != null){
                             //CHANGE IMAGE
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                                    syncListHolder.linearLayout.setBackground(getResources().getDrawable(R.drawable.circle_sync_done));
                                }
                            }
                        } else {
                             //CHANGE IMAGE
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                                syncListHolder.linearLayout.setBackground(getResources().getDrawable(R.drawable.circle_sync_done));
                            }
                        }
                    }

                    /* Progress */
                    if(syncList.getTotalCount() != null && syncList.getTotalCount().length() > 0) {
//                        syncListHolder.progressBar.setMax(Integer.valueOf(syncList.getTotalCount()));
                    }
                    if(syncList.getDataInsertCount() != null && syncList.getDataUpdatedCount() != null  ) {
                        runOnUiThread(new Runnable() {
                            public void run() {
//                                syncListHolder.progressBar.setProgress(Integer.valueOf(syncList.getDataInsertCount())+ Integer.valueOf(syncList.getDataUpdatedCount()));
                                //if(syncListHolder.progressBar!= null && syncListHolder.progressLabel!= null) {
                                double totalCount= Double.parseDouble(syncList.getTotalCount());
                                double totalProcess = Double.parseDouble(syncList.getDataInsertCount())+ Integer.parseInt(syncList.getDataUpdatedCount());
                                double percentageDouble = (totalProcess/totalCount)*100;
                                int percentageInt = (int) percentageDouble;
//                                syncListHolder.progressLabel.setText(String.valueOf(percentageInt)+"%");
                            }
                        });

                   }

                    return view;
                }

            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (busEvent.isRegistered(this)) {
            busEvent.unregister(this);
        }
    }


    public static class SyncListHolder {

        TextView elementName;
        TextView itemPreview;
        TextView insetCount;
        TextView updatedCount;
        TextView totalCount;
        ProgressBar progressBar;
        TextView progressLabel;
        LinearLayout linearLayout;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.sync_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(!isProcessFinish) {
                    inProgressDialog();
                }else{
                    super.onBackPressed();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        if(!isProcessFinish) {
        inProgressDialog();
        }else{
            super.onBackPressed();
        }
    }


    public void onEvent(NoInternetEvent event) {
        Toast.makeText(SyncPanel.this, "Please check your internet connection", Toast.LENGTH_SHORT);
    }

    public void onEvent(ApiResultEvent apiResultEvent) {
        if (apiResultEvent != null && apiResultEvent.getResponse().equals("OK")) {
            if(true) {
                downloadFromServer.setSyncCallbackInterface(syncCallbackInterface);
//                downloadFromServer.getProducts();
//                downloadFromServer.getTour();
//                downloadFromServer.getInvoice();
//                downloadFromServer.getReasons();
            }else{
                new AlertDialog.Builder(SyncPanel.this)
                        .setTitle("Syncing in progress")
                        .setMessage("You can't start another sync process until finish first process")
                        .setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false)
                        .show();
            }
        }

    }

   public void inProgressDialog(){
       new AlertDialog.Builder(SyncPanel.this)
               .setTitle("Syncing in progress")
               .setMessage("You can't leave this screen until download all data")
               .setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {

                   }
               })
               .setIcon(android.R.drawable.ic_dialog_alert)
               .setCancelable(false)
               .show();
   }






}
