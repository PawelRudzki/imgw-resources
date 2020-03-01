package dao;

import beans.StationBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.util.List;

public class StationDao {

    public List<StationBean> stationBuilder(InputStream is) throws Exception {
        Reader reader = new BufferedReader(
                new InputStreamReader(
                        is,"windows-1250"));

        List<StationBean> beans = new CsvToBeanBuilder(reader)
                .withType(StationBean.class)
                .withIgnoreQuotations(true)
                .build().parse();
        return beans;
    }
}
