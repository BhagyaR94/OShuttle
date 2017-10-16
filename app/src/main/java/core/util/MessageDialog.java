package core.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by tharindumac on 7/14/2016.
 */
public class MessageDialog {

    Context context;
    private OnAlertDialogClick onAlertDialogClick;

    public MessageDialog(Context context) {
        this.context = context;

    }

    public void newAlertYesNoDialog(String title, String message, OnAlertDialogClick alertDialogClick){
        this.onAlertDialogClick = alertDialogClick;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set title
        alertDialogBuilder.setTitle(title);
        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onAlertDialogClick.OnAlertDialogPositiveClick(dialog,id);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onAlertDialogClick.OnAlertDialogNegativeClick(dialog,id);
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    public void newLongToastDialog(String title){
        Toast.makeText(context,title, Toast.LENGTH_LONG).show();
    }

    public void newShortToastDialog(String title){
        Toast.makeText(context,title, Toast.LENGTH_SHORT).show();
    }


    public interface OnAlertDialogClick {
        void OnAlertDialogPositiveClick(DialogInterface dialog, int id);
        void OnAlertDialogNegativeClick(DialogInterface dialog, int id);
    }
}
