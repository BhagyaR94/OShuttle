package core.util;

import android.content.Context;
import android.database.Cursor;

import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

import application.entity.User;
import database.DatabaseHelperORM;

/*
 * @author chathuranga Bandara
 * @Email chathunBandara@gmail.com
 */
public class ClearAppData extends DatabaseHelperORM {


    public ClearAppData(Context context) {
        super(context);
    }

    public int clearOldGpsData(){
        int deletedRecordCount = 0;
        String countQuery = "SELECT COUNT(ID) FROM  GPS_TRACKING_DATA WHERE GPS_TRACKING_SYNC_STATUS = 1 date(CREATED_DATE) <= date('now' , '-5 day')";
        String query= " DELETE FROM GPS_TRACKING_DATA   WHERE GPS_TRACKING_SYNC_STATUS = 1 date(CREATED_DATE) <= date('now' , '-5 day') ";
        this.getWritableDatabase().execSQL(query);
        Cursor cursor = this.getrawQuery(countQuery, null);

        if(cursor.moveToFirst()){
            deletedRecordCount = cursor.getInt(1);
            cursor.close();
            return deletedRecordCount;

        }else{
            cursor.close();
            return  deletedRecordCount;
        }
    }

    public int clearOldSyncStatusData(){
        int deletedRecordCount = 0;
        String countQuery = "SELECT COUNT(ID) FROM  DOWNLOAD_STATUS WHERE  date(CREATED_DATE) <= date('now' , '-5 day')";
        String query= " DELETE FROM GPS_TRACKING_DATA   WHERE GPS_TRACKING_SYNC_STATUS = 1 date(CREATED_DATE) <= date('now' , '-5 day') ";
        this.getWritableDatabase().execSQL(query);
        Cursor cursor = this.getrawQuery(countQuery, null);

        if(cursor.moveToFirst()){
            deletedRecordCount = cursor.getInt(1);
            cursor.close();
            return deletedRecordCount;

        }else{
            cursor.close();
            return  deletedRecordCount;
        }
    }

    public void clearAllTables(){
        try {
            TableUtils.clearTable(connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
