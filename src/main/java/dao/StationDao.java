package dao;

import beans.StationBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class StationDao {

    private final String ENCODING = "windows-1250";

    public Set<StationBean> readFromCSV(InputStream is) throws Exception {

        Reader reader = new BufferedReader(
                new InputStreamReader(
                        is, ENCODING));

        List<StationBean> beans = new CsvToBeanBuilder<StationBean>(reader)
                .withType(StationBean.class)
                .withIgnoreQuotations(true)
                .build().parse();

        Set<StationBean> uniqueBeans = new TreeSet<>();
        uniqueBeans.addAll(beans);
        return uniqueBeans;
    }

    public boolean readToDatabase(Connection con) throws Exception {

        //make sure we read data to appriopriate table, without previous content
        createNewTable(con);

        StationDao sd = new StationDao();
        InputStream is = new URL("https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/wykaz_stacji.csv").openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Set<StationBean> stationBeans = sd.readFromCSV(is);

        for (StationBean station : stationBeans) {
            String preparedQuery = "insert into `imgw_db`.`t_stations` (station_id, station_name)"
                    + " values (?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(preparedQuery);
            preparedStmt.setString(1, station.getId());
            preparedStmt.setString(2, station.getName());
            preparedStmt.execute();
        }

        // station G³ubczyce II has no id number so we get rid of it
        deleteOrphans(con);

        is.close();
        return true;
    }

    public void createNewTable(Connection con) throws SQLException {

        Statement stmt = con.createStatement();
        String query = "DROP TABLE IF EXISTS t_stations";
        stmt.executeUpdate(query);

        stmt = con.createStatement();
        query = "   CREATE TABLE `imgw_db`.`t_stations` (\n" +
                "  `station_id` VARCHAR(9) NOT NULL,\n" +
                "  `station_name` VARCHAR(30) NOT NULL,\n" +
                "            PRIMARY KEY (`station_id`),\n" +
                "            UNIQUE INDEX `station_id_UNIQUE` (`station_id` ASC) VISIBLE)";
        stmt.executeUpdate(query);

    }

    public void deleteOrphans(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        String query = "DELETE FROM `t_stations` WHERE station_id=\"\"";
        stmt.executeUpdate(query);
    }
}
