package core.util;

/*
 * @author chathuranga Bandara
 * @Email chathunBandara@gmail.com
 */

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.constance.AppConstance;
import application.constance.Uri;
import application.entity.User;
import database.DatabaseHelperORM;


public class Utility extends Activity {
    private Context contex;
    private boolean internetStatus;

    public Utility(Context contex) {
        // TODO Auto-generated constructor stub
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.contex = contex;
    }

    // /////////////////////////change Network Available START
    // ///////////////////////////
    public boolean isNetworkAvailable(String a) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.contex.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isInternetAvailable() {
        Boolean status = new PingHost().doInBackground();
        return status;

    }

    // /////////////////////////change Network Available END
    // //////////////////////////////

    // ///////////////////////////////GET BattryLevel
    // ////////////////////////////////////
    // //without Broadcast Receiver (No live Update)
    public float getBatteryLevel() {
        Intent batteryIntent = registerReceiver(null, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        // Error checking that probably isn't needed but I added just in case.
        if (level == -1 || scale == -1) {
            return 50.0f;
        }

        return ((float) level / (float) scale) * 100.0f;
    }

    // //////////////////With Broadcast Receiver
    // use This inside activity Copy and past into Your activity
    public void batteryLevel() {
        BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,
                        -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int level = -1;
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }
                String s = "Battery Level Remaining: " + level + "%";
                // //set your value
            }

        };
        IntentFilter batteryLevelFilter = new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, batteryLevelFilter);
    }

    public String batteryLevelPrecentage(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
        int percent = (level * 100) / scale;
        return String.valueOf(percent) + "%";
    }

    // ///////////////////////////////GET BattryLevel
    // ////////////////////////////////////

    public void saveImageToSD(Bitmap bmp, String fileName, String folder) {
        FileOutputStream fos = null;
		/*--- this method will save your downloaded image to SD card ---*/

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		/*--- you can select your preferred CompressFormat and quality.
		 * I'm going to use JPEG and 100% quality ---*/
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		/*--- create a new file on SD card ---*/

        String filePath = Environment.getExternalStorageDirectory()
                //+ File.separator + DatabaseStatement.hypadSdFolderName
                + File.separator + "images" + File.separator + folder
                + File.separator + fileName;
        Log.i("INFO", "Write File To " + filePath);
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
		/*--- create a new FileOutputStream and write bytes to file ---*/
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bytes.toByteArray());
            fos.close();
            Toast.makeText(this.getContex(), "Image saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String successSavedMsg(int saved, int updated) {
        String savedStr = String.valueOf(saved);
        String updatesStr = String.valueOf(updated);
        if (saved == 0) {
            savedStr = "No";
        }
        if (updated == 0) {
            updatesStr = "No";
        }
        String msg = savedStr + " Records Insert and " + updatesStr + " Records Updated";
        return msg;
    }

    public static void putPref(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPref(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }




    public void setMobileDataEnabled() throws Throwable, Throwable {

        ConnectivityManager dataManager;
        dataManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Method dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
        dataMtd.setAccessible(true);
        dataMtd.invoke(dataManager, true);
    }

    public void setMobileDataDisabled() throws Throwable, Throwable {

        ConnectivityManager dataManager;
        dataManager = (ConnectivityManager) getContex().getSystemService(Context.CONNECTIVITY_SERVICE);
        Method dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
        dataMtd.setAccessible(true);
        dataMtd.invoke(dataManager, false);
    }


    public Context getContex() {
        return contex;
    }

    public void setContex(Context contex) {
        this.contex = contex;
    }

    public int uploadFileToServer(String sourceFileUri, String upLoadServerUri) {
        int serverResponseCode = 0;
        FileInputStream fileInputStream;
        URL url;
        try {
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(sourceFileUri);

            if (sourceFile.isFile()) {

                try {

                    // open a URL connection to the Servlet
                    fileInputStream = new FileInputStream(sourceFile);
                    url = new URL(upLoadServerUri);
                    // Open a HTTP connection to the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("fileName", sourceFileUri);
                    dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"fileName\";filename=\"" + sourceFileUri + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    // create a buffer of maximum size
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];
                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    // send multipart form data necesssary after file
                    // data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                    // Responses from the server (code and message)
                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    if (serverResponseCode == 200) {
                        Log.i("File send to server 200","RES 200");
                    }else{
                        Log.i("File send to server ", String.valueOf(serverResponseCode));
                    }

                    // close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return serverResponseCode;
    }

    public int uploadFileToServer(String dealer_id, String cheque_no, String sourceFileUri, String upLoadServerUri) {
        int serverResponseCode = 0;
        FileInputStream fileInputStream;
        URL url;
        try {
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(sourceFileUri);

            if (sourceFile.isFile()) {

                try {
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("dealer_id", dealer_id);
                    jsonParam.put("cheque_no", cheque_no);

                    // open a URL connection to the Servlet
                    fileInputStream = new FileInputStream(sourceFile);
                    url = new URL(upLoadServerUri);
                    // Open a HTTP connection to the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("fileName", sourceFileUri);
                    dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"fileName\";filename=\"" + sourceFileUri + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes("Content-Type: application/json");
                    dos.writeBytes("Content-Disposition: form-data; name=\"data\";data=\"" + jsonParam.toString() + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    // create a buffer of maximum size
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];
                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    // send multipart form data necesssary after file
                    // data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                    // Responses from the server (code and message)
                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    if (serverResponseCode == 200) {
                        Log.i("File send to server 200","RES 200");
                    }else{
                        Log.i("File send to server ", String.valueOf(serverResponseCode));
                    }

                    // close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return serverResponseCode;
    }


    /* Async Task For Check Internet Availability */
    class PingHost extends AsyncTask<String, Void, Boolean> {
        InetAddress inetAddress;


        @Override
        protected Boolean doInBackground(String... params) {
            try {
                inetAddress = InetAddress.getByName("google.com");

                if (inetAddress.equals("")) {
                    internetStatus =false;
                } else {
                    internetStatus= true;
                }

            } catch (Exception e) {
                e.printStackTrace();
                internetStatus = false;
            }
            return internetStatus;


        }

    }

    public void exportDB(User user) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
            String filename = sdf.format(new Date()) + "_" + user.getUserId()+"-"+user.getUserName() + "_" + DatabaseHelperORM.DATABASE_NAME;

            if (sd.canWrite()) {
                String currentDBPath = AppConstance.appDbLocation;

                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, filename);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();

                    String filenamePaths = Environment.getExternalStorageDirectory() + "/" + filename;
                    uploadFileToServer(filenamePaths, Uri.dbBackupUploadFile);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean isTextBoxNumber(EditText editText){
        String regex ="^[1-9]+";
        Matcher matcher = Pattern.compile( regex ).matcher(editText.getText());
        if (matcher.find( )) {
            return true;
        }else{
            return false;
        }
    }

    public static boolean isTextBoxNumberDouble(EditText editText){
        String regex ="-+]?[0-9]*\\\\.?[0-9]+([eE][-+]?[0-9]+)?";
        Matcher matcher = Pattern.compile( regex ).matcher(editText.getText());
        if (matcher.find( )) {
            return true;
        }else{
            return false;
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static Date getDateNow(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(format.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDateStringNow(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String toDateString(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Date toDate(String date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String customFormat(String pattern, double value ) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        myFormatter.setMinimumFractionDigits(2);
        String output = myFormatter.format(value);
        return output;
    }

    public static String getPath(Context context, android.net.Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }
}
