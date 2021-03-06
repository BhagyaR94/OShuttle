package core.util;

import android.graphics.PointF;

/**
 * Created by rasika on 3/8/16.
 */
public class Location {

   // double radius=1000;

    //PointF center = new PointF(6.870541f, 79.905273f);
   // PointF pointForCheck=new PointF(6.914816f,79.972900f);
   // Toast.makeText(LocationActivity.this, "This Location is 1000 in : "+pointIsInCircle(pointForCheck,center,radius), Toast.LENGTH_SHORT).show();
    public static boolean pointIsInCircle(PointF pointForCheck, PointF center, double radius) {

        if (getDistanceBetweenTwoPoints(pointForCheck, center) <= radius)
            return true;
        else
            return false;
    }

    public static double getDistanceBetweenTwoPoints(PointF p1, PointF p2) {
        double R = 6371000; // m
        double dLat = Math.toRadians(p2.x - p1.x);
        double dLon = Math.toRadians(p2.y - p1.y);
        double lat1 = Math.toRadians(p1.x);
        double lat2 = Math.toRadians(p2.x);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2)
                * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }


}
