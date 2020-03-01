package dao;

import beans.ClimaticRecordBean;
import beans.StationBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.util.List;

public class ClimaticRecordDao {

    public List<ClimaticRecordBean> climaticRecordBuilder(InputStream is) throws Exception {
        Reader reader = new BufferedReader(
                new InputStreamReader(
                        is,"windows-1250"));

        List<ClimaticRecordBean> beans = new CsvToBeanBuilder(reader)
                .withType(ClimaticRecordBean.class)
                .withIgnoreQuotations(true)
                .build().parse();
        return beans;
    }
}
