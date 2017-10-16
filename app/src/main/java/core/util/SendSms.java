package core.util;

import android.content.Context;
import android.telephony.SmsManager;

/**
 * Created by chathuranga on 7/28/2015. SFAAndroid
 *
 * @email chathunbandara@gmail.com
 * @phone +94716271637
 */
public class SendSms {

    private Context context;

    public SendSms(Context context){
        this.context = context;
    }

    public void sendSms(String msg, String[]numberList){
        if(numberList!= null && numberList[0].length()>0 ) {
            for (String number : numberList) {
                if(number != null) {
                    SmsManager.getDefault().sendTextMessage(number, null, msg, null, null);
                }
            }
        }
    }


}
