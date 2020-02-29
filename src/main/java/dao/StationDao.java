package dao;

import beans.CsvBean;
import beans.StationBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.util.List;

public class StationDao {


    public static List<StationBean> stationBuilder(String uri) throws Exception {

        Reader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(uri),"windows-1250"));

        List<StationBean> beans = new CsvToBeanBuilder(reader)
                .withType(StationBean.class)
                .withIgnoreQuotations(true)
                .build().parse();
        return beans;
    }

}
