package dao;

import beans.ClimaticRecordBean;
import beans.StationBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class ClimaticRecordDao {

    public List<ClimaticRecordBean> climaticRecordBuilder(String uri) throws Exception {
        Reader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(uri),"windows-1250"));

        List<ClimaticRecordBean> beans = new CsvToBeanBuilder(reader)
                .withType(ClimaticRecordBean.class)
                .withIgnoreQuotations(true)
                .build().parse();
        return beans;
    }
}
