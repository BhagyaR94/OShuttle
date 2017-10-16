package core.controller;

import android.content.Context;

import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import database.DatabaseHelperORM;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bhagya Rathnayake on 9/19/17.
 * Email: rathnayake.bhagya94@gmail.com
 * Contact No.: +94 778170779
 * Company: Orange IT Solution(PVT.) LTD
 * Project Name: olead-app
 */

public class InquiryController extends DatabaseHelperORM{
    Context context;
    private DatabaseHelperORM databaseHelper = null;

    public InquiryController(Context context) {
        super(context);
    }

    private DatabaseHelperORM getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelperORM.class);
        }
        return databaseHelper;
    }

    public int saveInquiry()
    {
        return 1;
    }

}
