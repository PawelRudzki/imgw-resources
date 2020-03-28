package utils;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CoordinatesUtils {

    public double distanceBetweenPoints(int lat1deg, int lat1min, int long1deg, int long1min,
                                        int lat2deg, int lat2min, double long2deg, int long2min) {

        double longitude1 = long1deg + ((double)long1min / 60);
        double latitude1 = lat1deg + ((double)lat1min / 60);

        double longitude2 = long2deg + ((double)long2min / 60);
        double latitude2 = lat2deg + ((double)lat2min / 60);

        double earthDiameter = 12756.274;
        double a = (longitude2 - longitude1) * Math.cos(latitude1 * Math.PI / 180);
        double b = latitude2 - latitude1;
        return Math.sqrt(a * a + b * b) * Math.PI * earthDiameter / 360;
        //result in kilometers
    }

    public Map<Integer, Double> distanceBetweenPointsDBFunction(Connection con, int numberOfStations, double searchRadius, int latitude_degrees, int latitude_minutes, int longitude_degrees, int longitude_minutes) throws SQLException {

        String preparedQuery =
                "SELECT * FROM"+
                        " (SELECT `short_station_id`,imgw_db.`distance`(`t_coordinates`.`latitude_degrees`, `t_coordinates`.`latitude_minutes`, `t_coordinates`.`longitude_degrees`, `t_coordinates`.`longitude_minutes`, ?, ?, ?, ?) AS `distance` FROM `t_coordinates`) AS `distances` WHERE `distance` < ? ORDER BY distance LIMIT ?";

        PreparedStatement preparedStmt = con.prepareStatement(preparedQuery);
        preparedStmt.setInt(1, latitude_degrees);
        preparedStmt.setInt(2, latitude_minutes);
        preparedStmt.setInt(3, longitude_degrees);
        preparedStmt.setInt(4, longitude_minutes);
        preparedStmt.setDouble(5, searchRadius);
        preparedStmt.setInt(6, numberOfStations);

        ResultSet rs = preparedStmt.executeQuery();

        HashMap<Integer, Double> resultMap = new HashMap<>();
        while (rs.next()) {
            resultMap.put(rs.getInt(1), rs.getDouble(2));
        }

        return resultMap;
    }
}

