package core.util;

/*
 * @author chathuranga Bandara
 * @Email chathunBandara@gmail.com
 */

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumbeFormater {

    public static String FormatDoubleToCurrency(Double number) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        String angleFormated = df.format(number);
        return angleFormated;
    }


    public static String FormatIntegerToDecimal(int number) {
        DecimalFormat df = new DecimalFormat("#.00");
        String angleFormated = df.format(number);
        return angleFormated;
    }

    public static String FormatDoubleToDecimal(Double number) {
        DecimalFormat df = new DecimalFormat("#.00");
        String angleFormated = df.format(number);
        return angleFormated;
    }

    public static double FormatDoubleToDouble(Double number) {
        DecimalFormat df = new DecimalFormat("#.00");
        String angleFormated = df.format(number);
        return Double.parseDouble(angleFormated);
    }

    public static String FractionDigitsTowFormat(Object value) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        return nf.format(value);
    }

    public static String formatDoubleToClean(double d) {

        if (d == (long) d) {
            return String.format("%d", (long) d);
        } else{
            return String.format("%s", d);
        }
    }

}