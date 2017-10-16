package application.common;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.entity.User;
import core.util.Criptor;
import database.DatabaseHelperORM;


public class Functions extends DatabaseHelperORM {
    private Context context;

    public Functions(Context context) {
        super(context);
        this.context = context;
    }

    public boolean CheckDataIsExist(int primaryKeyValue, String tableName, String primaryKeyName, String selectRealPrimaryKey) {
        boolean status = true;
        Cursor data = this.getrawQuery("SELECT id from " + tableName + " WHERE " + primaryKeyName + " = " + primaryKeyValue, null);

        if (data.getCount() < 0) {
            status = false;
            data.close();
        } else {
            data.close();
        }
        return status;
    }

    public boolean checkUserIsValid(String userName, String password) {
        boolean status = false;
        Criptor criptor = new Criptor();
        try {
            Dao<User, Integer> userDao = super.getUserDao();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("USER_NAME", userName);
            map.put("USER_PASSWORD", criptor.getMD5HashVal(password));
            List<User> userList = userDao.queryForFieldValues(map);
            if (userList.size() > 0) {
                status = true;
            }
            userDao.closeLastIterator();
            userDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }

        return status;
    }

    public User getUserById(int UserId) {
        Dao<User, Integer> userDao = null;
        try {
            userDao = super.getUserDao();
            List<User> users = userDao.queryForEq("USER_ID", UserId);
            if (users.size() > 0) {
                return users.get(0);
            } else {
                userDao.clearObjectCache();
                userDao.closeLastIterator();
                return new User();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
            return null;
        } finally {
            userDao.clearObjectCache();
            try {
                userDao.closeLastIterator();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public String getLastDownloadedTimestamp(String type) {
        String timeStamp = "0";
        try {
            Cursor data = this.getrawQuery("SELECT TIMESTAMP FROM DOWNLOAD_STATUS WHERE TYPE = '" + type + "' ORDER BY `ID` DESC  LIMIT 1 ", null);
            if (data.moveToFirst()) {
                timeStamp = data.getString(0);
                data.close();

            } else {
                data.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());

        }
        return timeStamp;

    }



    public User getUserByUserName(String userName) {
        try {
            Dao<User, Integer> userDao = super.getUserDao();
            List<User> users = userDao.queryForEq("USER_NAME", userName);
            if (users.size() > 0) {
                userDao.closeLastIterator();
                userDao.clearObjectCache();
                return users.get(0);
            } else {
                userDao.closeLastIterator();
                userDao.clearObjectCache();
                return new User();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
            return null;
        }


    }





}