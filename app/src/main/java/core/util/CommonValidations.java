package core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Chathun on 6/20/2016.
 */
public class CommonValidations {


    public Object[] SriLankanNumberValiation(String number) {
        Object[] result = new Object[3];

        Pattern PHONENO_PATTERN = Pattern.compile("0{1}[1-9]{1}\\d{8}|\\+94[1-9]{1}\\d{8}");

        Matcher m = PHONENO_PATTERN.matcher(number);
        if (!m.matches()) {
            /* Set Validation false */
            result[0] = false;
            if (!(number.startsWith("+94") || number.startsWith("0"))) {
                result[1] = "Number should start with +94 or 0 ";
                return result;

            } else if (number.startsWith("0")) {
                if (number.length() > 1 && Integer.valueOf(number.substring(1, 2)) < 1) {
                    result[1] = "Number should start with 0 ";
                    return result;
                }
                if (number.length() < 10 || number.length() > 10) {
                    result[1] = "Number length should 10 numbers";
                    return result;
                }
            } else if (number.startsWith("+94")) {
                if (number.length() > 3 && Integer.valueOf(number.substring(3, 4)) < 1) {
                    result[1] = "Number should start with +94 ";
                    return result;
                }
                if (number.length() < 12 || number.length() > 12) {
                    result[1] = "Number length should 12 numbers with country code ";
                    return result;
                }
            }
        }

        result[0] = true;
        result[1] = "Valid Number";
        return result;
    }
}

