package utils;

public class CoordinatesUtils {

    public double distanceBetweenPoints(int lat1deg, int lat1min, int long1deg, int long1min,
                                        int lat2deg, int lat2min, double long2deg, int long2min) {


        double longitude1 = long1deg + (long1min / 60);
        double latitude1 = lat1deg + (lat1min / 60);

        double longitude2 = long2deg + (long2min / 60);
        double latitude2 = lat2deg + (lat2min / 60);

        double earthDiameter = 12756.274;
        double a = (longitude2 - longitude1) * Math.cos(latitude1 * Math.PI / 180);
        double b = latitude2 - latitude1;

        return Math.sqrt(a * a + b * b) * Math.PI * earthDiameter / 360;
        //result in kilometers
    }
}

