package syncing.helper;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.common.Functions;
import application.constance.AppConstance;
import application.controller.UserController;
import application.entity.User;
import application.network.webclientconfig.client.ApiClient;
import database.DatabaseHelperORM;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import syncing.interfaces.SyncCallbackInterface;
import syncing.model.SyncList;


/**
 * Created by chathuranga on 7/17/2015. SFAAndroid
 *
 * @email chathunbandara@gmail.com
 * @phone +94716271637
 * tel
 */
public class DownloadFromServer {
    private DatabaseHelperORM databaseHelper = null;
    private Context appContext;
    private Functions functions;
    private int user_id = 0 ;
    private SyncCallbackInterface syncCallbackInterface;
    private ArrayList<SyncList> syncResultList ;
    private String loggerTAG = DownloadFromServer.this.getClass().getSimpleName();
    private UserController userController;
    private User user;

    /* Controllers */

    int segmentCount=0;
    int totalMethodCount =0;
    static int executedMethodCount=0;


    public DownloadFromServer(Context context) {
        this.appContext = context;
        functions = new Functions(this.getAppContext());
        setSyncResultList(new ArrayList<SyncList>());
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getAppContext(), DatabaseHelperORM.class);
        }
        userController          = new UserController(context);
        user                    = userController.getCurrentAppUser();
        totalMethodCount = 1;

    }

    /* INSERT MASTER DATA */
    public Context getAppContext() {
        return appContext;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    private void syncNotification(SyncList resObjects, String previewLabel, String InsertCount, String updateCount) {
        resObjects.setDataPreview(previewLabel);
        resObjects.setDataInsertCount(String.valueOf(InsertCount));
        resObjects.setDataUpdatedCount(String.valueOf(updateCount));
        syncCallbackInterface.liveDownloadCount(getSyncResultList());
    }

    public SyncCallbackInterface getSyncCallbackInterface() {
        return syncCallbackInterface;
    }

    public void setSyncCallbackInterface(SyncCallbackInterface syncCallbackInterface) {
        this.syncCallbackInterface = syncCallbackInterface;
    }

    public int getTotalMethodCount() {
        return totalMethodCount;
    }

    public int getExecutedMethodCount() {
        return executedMethodCount;
    }

    public void setExecutedMethodCount(int executedMethodCount) {
        this.executedMethodCount = executedMethodCount;
    }

    public ArrayList<SyncList> getSyncResultList() {
        return syncResultList;
    }

    public void setSyncResultList(ArrayList<SyncList> syncResultList) {
        this.syncResultList = syncResultList;
    }



}
