package application.controller;

import android.content.Context;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import application.constance.AppConstance;
import application.entity.User;
import core.util.Utility;
import database.DatabaseHelperORM;

/**
 * Created by Chathun on 7/8/2016.
 */
public class UserController extends DatabaseHelperORM {
    private Context context;

    public UserController(Context context) {
        super(context);
        this.context = context;
    }


    public User getCurrentAppUser() {
        String user_id = Utility.getPref(AppConstance.SHAREDPREFERENCES_USER_ID,context);
        User user = null;
        try {
            List<User> users = super.getUserDao().queryForEq("USER_ID",user_id);
            if (users.size() > 0) {
                user =  users.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return user;
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


}
