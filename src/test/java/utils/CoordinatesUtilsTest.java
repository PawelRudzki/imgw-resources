package utils;

import beans.CoordinatesBean;
import org.junit.Test;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CoordinatesUtilsTest {

    @Test
    public void testsDistanceBetweenPoints() {

        //given
        CoordinatesUtils cu = new CoordinatesUtils();


        //when

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/imgw_db?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8&allowPublicKeyRetrieval=true", "root", "Mandok01");

            Statement stmt = con.createStatement();
            String query = "SELECT * FROM imgw_db.t_coordinates WHERE short_station_id=\"100\" OR short_station_id=\"160\"";


            ResultSet rs = stmt.executeQuery(query);
            List<CoordinatesBean> coordinatesList = new LinkedList<>();
            while (rs.next()) {
                coordinatesList.add(new CoordinatesBean(
                        rs.getString(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5)
                ));
            }

            CoordinatesBean coord1 = coordinatesList.get(0);
            CoordinatesBean coord2 = coordinatesList.get(1);

            double distance = 0.0;

            distance = cu.distanceBetweenPoints(
                    coord1.getLatitudeDeg(), coord1.getLatitudeMin(), coord1.getLongitudeDeg(), coord1.getLongitudeMin(),
                    coord2.getLatitudeDeg(), coord2.getLatitudeMin(), coord2.getLongitudeDeg(), coord2.getLongitudeMin());

            //then
            assertEquals(270.6, distance, 0.1);

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testsdistanceBetweenPointsDBFunction() {

        //given
        CoordinatesUtils cu = new CoordinatesUtils();


        //when

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/imgw_db?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8&allowPublicKeyRetrieval=true", "root", "Mandok01");


            Map<Integer, Double> resultMap = cu.distanceBetweenPointsDBFunction(con, 3, 100, 54, 13, 19, 32 );

            //then
            assertEquals("{160=0.0, 1505=19.325045964406772, 1502=11.131949079327514}", resultMap.toString());

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
