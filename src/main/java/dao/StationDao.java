package dao;

import beans.StationBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class StationDao {

    private final String ENCODING = "windows-1250";

    public Set<StationBean> readFromCSV(InputStream is) throws Exception {

        Reader reader = new BufferedReader(
                new InputStreamReader(
                        is,ENCODING));

        List<StationBean> beans = new CsvToBeanBuilder(reader)
                .withType(StationBean.class)
                .withIgnoreQuotations(true)
                .build().parse();

        Set uniqueBeans = new TreeSet<>();
        for(StationBean station : beans) {
            uniqueBeans.add(station);
        }
        return uniqueBeans;
    }

    public boolean readToDatabase(){

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/imgw_db?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8&allowPublicKeyRetrieval=true", "root", "Mandok01");
            //here imgw_db is database name, root is username and password

            //
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


            StationDao sd = new StationDao();
            InputStream is = new URL(
                    "https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/wykaz_stacji.csv")
                    .openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            Set<StationBean> stationBeans = sd.readFromCSV(is);

            for (StationBean station : stationBeans) {

                // the mysql insert statement
                String preparedQuery = "insert into `imgw_db`.`t_stations` (station_id, station_name)"
                        + " values (?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = con.prepareStatement(preparedQuery);
                preparedStmt.setString(1, station.getId());
                preparedStmt.setString(2, station.getName());

                // execute the preparedstatement
                preparedStmt.execute();
            }

            // station G³ubczyce II has no id number so we get rid of it
            stmt = con.createStatement();
            query = "DELETE FROM `t_stations` WHERE station_id=\"\"";
            stmt.executeUpdate(query);

            is.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}
