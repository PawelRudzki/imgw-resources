package dao;

import beans.CoordinatesBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CoordinatesDao {

    private final String ENCODING = "windows-1250";

    public Set<CoordinatesBean> readFromCSV(InputStream is) throws Exception {

        Reader reader = new BufferedReader(
                new InputStreamReader(
                        is, ENCODING));

        List<CoordinatesBean> beans = new CsvToBeanBuilder<CoordinatesBean>(reader)
                .withType(CoordinatesBean.class)
                .withIgnoreQuotations(false)
                .build().parse();

        Set<CoordinatesBean> uniqueBeans = new TreeSet<>();
        uniqueBeans.addAll(beans);
        return uniqueBeans;
    }

    public boolean readToDatabase(Connection con) throws Exception {

        //make sure we read data to appriopriate table, without previous content
        createNewTable(con);

        CoordinatesDao cd = new CoordinatesDao();
        FileInputStream is = new FileInputStream("csv/stations-coordinates.csv");
        Set<CoordinatesBean> coordinatesBeans = cd.readFromCSV(is);

        for (CoordinatesBean coordinate : coordinatesBeans) {
            String preparedQuery = "insert into `imgw_db`.`t_coordinates` (short_station_id, longitude_degrees, longitude_minutes, latitude_degrees, latitude_minutes)"
                    + " values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(preparedQuery);
            preparedStmt.setString(1, coordinate.getShortStationId());
            preparedStmt.setInt(2, coordinate.getLongitudeDeg());
            preparedStmt.setInt(3, coordinate.getLongitudeMin());
            preparedStmt.setInt(4, coordinate.getLatitudeDeg());
            preparedStmt.setInt(5, coordinate.getLatitudeMin());
            preparedStmt.execute();
        }
        is.close();
        return true;
    }

    public void createNewTable(Connection con) throws SQLException {

        Statement stmt = con.createStatement();
        String query = "DROP TABLE IF EXISTS t_coordinates";
        stmt.executeUpdate(query);

        stmt = con.createStatement();
        query = "   CREATE TABLE `imgw_db`.`t_coordinates` (\n" +
                "  `short_station_id` VARCHAR(5) NOT NULL,\n" +
                "  `longitude_degrees` INT(2) NOT NULL,\n" +
                "  `longitude_minutes` INT(2) NOT NULL,\n" +
                "  `latitude_degrees` INT(2) NOT NULL,\n" +
                "  `latitude_minutes` INT(2) NOT NULL,\n" +
                "            PRIMARY KEY (`short_station_id`),\n" +
                "            UNIQUE INDEX `coordinate_UNIQUE` (`short_station_id` ASC) VISIBLE)";
        stmt.executeUpdate(query);

    }
}
