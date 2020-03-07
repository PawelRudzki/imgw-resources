package dao;

import beans.StationBean;
import com.opencsv.bean.CsvToBeanBuilder;
import utils.Unzip;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class StationDao {

    private final String ENCODING = "windows-1250";

    public List<StationBean> stationBuilder(InputStream is) throws Exception {

        Reader reader = new BufferedReader(
                new InputStreamReader(
                        is,ENCODING));

        List<StationBean> beans = new CsvToBeanBuilder(reader)
                .withType(StationBean.class)
                .withIgnoreQuotations(true)
                .build().parse();
        return beans;
    }
}
