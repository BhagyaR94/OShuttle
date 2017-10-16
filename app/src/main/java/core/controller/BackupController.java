package core.controller;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import database.DatabaseHelperORM;

/**
 * Created by Mihiran Dushyantha Rajasekara
 * mihiran.neth@gmail.com
 */
public class BackupController extends DatabaseHelperORM {
    Context context;
    private DatabaseHelperORM databaseHelper = null;

    public BackupController(Context context) {
        super(context);
        this.context=context;
    }

    private DatabaseHelperORM getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelperORM.class);
        }
        return databaseHelper;
    }



   //*******  check last update date with current date time

    public Integer getLastBackup(String date1) {
        int sts=0;
        String query = new StringBuilder()
                .append("SELECT date(BACKUP_DETAIL.CREATED_DATE ) as backupdate FROM BACKUP_DETAIL where backupdate ='"+date1+"'").toString();
        Log.i("Query", query);
        Cursor data = this.getrawQuery(query, null);
        if (data.getCount()>0)
        {
            sts=1;
        }

        data.close();
        return sts;
    }

}
