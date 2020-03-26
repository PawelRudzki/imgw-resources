package dao;

import beans.ClimaticRecordBean;
import com.opencsv.bean.CsvToBeanBuilder;
import utils.HtmlUtils;
import utils.Unzip;

import java.io.*;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;


public class ClimaticRecordDao implements RecordDao{

    private final String ENCODING = "windows-1250";

    public void pullData() throws Exception {

        HtmlUtils htmlUtils = new HtmlUtils();
        Unzip unzip = new Unzip();
        List<ClimaticRecordBean> pathsList = new LinkedList();
        TreeMap<String, LinkedList<String>> filesMap = (TreeMap<String, LinkedList<String>>) htmlUtils.pullFilesMap("https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/dobowe/klimat/");

        filesMap.forEach((key, value) -> {
            value.forEach(file -> {
                InputStream is = null;

                try {
                    is = new URL("https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/dobowe/klimat/" + key + file).openStream();
                    unzip.unzip(is, "output/");
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }
    public List<ClimaticRecordBean> readFromCSV(InputStream is) throws Exception {

        Reader reader = new BufferedReader(
                new InputStreamReader(
                        is, ENCODING));
        List<ClimaticRecordBean> beans = new CsvToBeanBuilder(reader)
                .withType(ClimaticRecordBean.class)
                .withIgnoreQuotations(true)
                .build().parse();
        return beans;
    }
    public Set<ClimaticRecordBean> readData() {

        Set<ClimaticRecordBean> crbSet = new TreeSet<>();
        try (Stream<Path> walk = Files.walk(Paths.get("output/"))) {
            walk
                    .filter(b -> b.toString().length()>11 && b.toString().charAt(11)!='t')
                    .forEach(a -> {
                        InputStream is = null;
                        try {
                            is = new FileInputStream(a.toFile());
                            crbSet.addAll(readFromCSV(is));
                            is.close();
                            Files.delete(a);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
            return crbSet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void createNewTable(Connection con) throws SQLException {

        Statement stmt = con.createStatement();
        String query = "DROP TABLE IF EXISTS t_climatic_records";
        stmt.executeUpdate(query);



        stmt = con.createStatement();
        query = "   CREATE TABLE `imgw_db`.`t_climatic_records` (\n" +
                "  `station_id` VARCHAR(9) NOT NULL,\n" +
                "  `station_name` VARCHAR(30) NOT NULL,\n" +
                "  `year` VARCHAR(4) NOT NULL,\n" +
                "  `month` VARCHAR(2) NOT NULL,\n" +
                "  `day` VARCHAR(2) NOT NULL,\n" +
                "  `max_tmp` VARCHAR(8) NOT NULL,\n" +
                "  `max_tmp_status` VARCHAR(1) NOT NULL,\n" +
                "  `min_tmp` VARCHAR(8) NOT NULL,\n" +
                "  `min_tmp_status` VARCHAR(1) NOT NULL,\n" +
                "  `avg_tmp` VARCHAR(10) NOT NULL,\n" +
                "  `avg_tmp_status` VARCHAR(1) NOT NULL,\n" +
                "  `min_ground_tmp` VARCHAR(8) NOT NULL,\n" +
                "  `min_ground_tmp_status` VARCHAR(1) NOT NULL,\n" +
                "  `total_precipitation` VARCHAR(10) NOT NULL,\n" +
                "  `total_precipitation_status` VARCHAR(1) NOT NULL,\n" +
                "  `precipitation_kind` VARCHAR(1) NOT NULL,\n" +
                "  `snow_layer_height` VARCHAR(5) NOT NULL,\n" +
                "  `snow_layer_height_status` VARCHAR(1) NOT NULL,\n" +
                "            PRIMARY KEY (`station_id`, `year`, `month`, `day`),\n" +
                "            UNIQUE INDEX `climatic_record_UNIQUE` (`station_id`, `year`, `month`, `day` ASC) VISIBLE)";

        stmt.executeUpdate(query);

    }
    public boolean readToDatabase(Connection con) throws SQLException {


        Set<ClimaticRecordBean> crbSet = new TreeSet<>();
        try (Stream<Path> walk = Files.walk(Paths.get("output/"))) {
            walk
                    .filter(b -> b.toString().length()>11 && b.toString().charAt(11)!='t')
                    .forEach(a -> {
                        InputStream is = null;
                        try {
                            is = new FileInputStream(a.toFile());
                            crbSet.addAll(readFromCSV(is));
                            is.close();
                            for (ClimaticRecordBean record : crbSet) {
                                String preparedQuery = "INSERT INTO `imgw_db`.`t_climatic_records` (station_id, station_name, year, month, day," +
                                        " max_tmp, max_tmp_status, min_tmp, min_tmp_status, avg_tmp, avg_tmp_status, min_ground_tmp, min_ground_tmp_status," +
                                        " total_precipitation, total_precipitation_status, precipitation_kind, snow_layer_height, snow_layer_height_status\n)"
                                        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                                PreparedStatement preparedStmt = con.prepareStatement(preparedQuery);
                                preparedStmt.setString(1, record.getId());
                                preparedStmt.setString(2, record.getName());
                                preparedStmt.setString(3, record.getYear());
                                preparedStmt.setString(4, record.getMonth());
                                preparedStmt.setString(5, record.getDay());
                                preparedStmt.setString(6, record.getTmpMax());
                                preparedStmt.setString(7, record.getTmpMaxStatus());
                                preparedStmt.setString(8, record.getTmpMin());
                                preparedStmt.setString(9, record.getTmpMinStatus());
                                preparedStmt.setString(10, record.getTmpAvg());
                                preparedStmt.setString(11, record.getTmpAvgStatus());
                                preparedStmt.setString(12, record.getTmpOfGroundMin());
                                preparedStmt.setString(13, record.getTmpOfGroundMinStatus());
                                preparedStmt.setString(14, record.getTotalPrecipitation());
                                preparedStmt.setString(15, record.getTotalPrecipitationStatus());
                                preparedStmt.setString(16, record.getKindOfPrecipitation());
                                preparedStmt.setString(17, record.getSnowLayerHeight());
                                preparedStmt.setString(18, record.getSnowLayerHeightStatus());
                                preparedStmt.execute();
                            }

                            Files.delete(a);
                            crbSet.removeAll(crbSet);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

