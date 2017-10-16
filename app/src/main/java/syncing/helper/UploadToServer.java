package syncing.helper;

import android.content.Context;

import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;
import java.util.HashMap;

import application.common.Functions;
import application.controller.UserController;
import application.entity.User;
import application.network.webclientconfig.client.ApiClient;
import database.DatabaseHelperORM;
import de.greenrobot.event.EventBus;
import syncing.interfaces.SyncCallbackInterface;
import syncing.model.SyncList;

/**
 * Created by chathuranga on 7/17/2015. SFAAndroid
 *
 * @email chathunbandara@gmail.com
 * @phone +94716271637
 */
public class UploadToServer {

    private DatabaseHelperORM databaseHelper = null;
    private Context appContext;
    private Functions functions;
    private DownloadFromServer server;
    private int user_Id;
    private int methodCount = 0;
    private ArrayList<SyncList> syncResultList;
    private SyncCallbackInterface callback;
    private UserController userController;
    private User user;
    private EventBus bus;
    private HashMap<String, int[]> responseResult;

    int totalMethodCount =0;
    static int executedMethodCount=0;

    public UploadToServer(Context context) {
        this.setAppContext(context);
        functions = new Functions(context);
        bus = EventBus.getDefault();
        responseResult = new HashMap<String, int[]>();
        syncResultList = new ArrayList<SyncList>();

        /*controllers*/
        userController          = new UserController(context);
        user                    = userController.getCurrentAppUser();


    }

    public Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public DatabaseHelperORM getHelper() {

        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getAppContext(), DatabaseHelperORM.class);
        }
        return databaseHelper;

    }

    public void setHelper(DatabaseHelperORM databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    private void syncNotification(SyncList resObjects, String previewLabel, String InsertCount, String updateCount) {
        resObjects.setDataPreview(previewLabel);
        resObjects.setDataInsertCount(String.valueOf(InsertCount));
        resObjects.setDataUpdatedCount(String.valueOf(updateCount));
        callback.liveDownloadCount(syncResultList);
    }

    public SyncCallbackInterface getCallback() {
        return callback;
    }

    public void setCallback(SyncCallbackInterface callback) {
        this.callback = callback;
    }

    public interface onUpload {
        void onFinish(ArrayList<SyncList> resObjects);
        void onFailed(ArrayList<SyncList> resObjects);
    }

    public static int getExecutedMethodCount() {
        return executedMethodCount;
    }

    public static void setExecutedMethodCount(int executedMethodCount) {
        UploadToServer.executedMethodCount = executedMethodCount;
    }

    public int getTotalMethodCount() {
        return totalMethodCount;
    }

    public void setTotalMethodCount(int totalMethodCount) {
        this.totalMethodCount = totalMethodCount;

    }

    public interface OnUploadCallback {
        void onUploadComplete(boolean isDone, String msg);
        void onUploadFailed(Throwable throwable);
    }
}

