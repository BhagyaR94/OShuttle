package core.util;

/**
 * Created by chathuranga on 6/18/2015.
 */

/*
 * Author ChathurangaBandara
 * this is Json helper class
 * This is use for call remot Json
 */

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Criptor {

    public String getMD5HashVal(String strToBeEncrypted) {
        String encryptedString = null;
        byte[] bytesToBeEncrypted;

        try {
            // convert string to bytes using a encoding scheme
            bytesToBeEncrypted = strToBeEncrypted.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] theDigest = md.digest(bytesToBeEncrypted);
            // convert each byte to a hexadecimal digit
            Formatter formatter = new Formatter();
            for (byte b : theDigest) {
                formatter.format("%02x", b);
            }
            encryptedString = formatter.toString().toLowerCase();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedString;
    }

}
