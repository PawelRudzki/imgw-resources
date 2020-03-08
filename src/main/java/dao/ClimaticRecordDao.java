package dao;

import beans.ClimaticRecordBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.util.List;

public class ClimaticRecordDao {

    private final String ENCODING = "windows-1250";

    public List<ClimaticRecordBean> climaticRecordBuilder(InputStream is) throws Exception {

        Reader reader = new BufferedReader(
                new InputStreamReader(
                        is, ENCODING));

        List<ClimaticRecordBean> beans = new CsvToBeanBuilder(reader)
                .withType(ClimaticRecordBean.class)
                .withIgnoreQuotations(true)
                .build().parse();
        return beans;
    }

//    public List<ClimaticRecordBean> pullClimaticRecordsFromZip(InputStream is) throws Exception {
//
//        Unzip unzipper = new Unzip();
//
//        return climaticRecordBuilder(new FileInputStream(
//                unzipper.unzip(is, "output/")));
//    }
//
//
//    //test with bytearrays instead of InputStreams
//
//    public List<ClimaticRecordBean> pullClimaticRecordsFromImgwSiteAsBytes(String stationsZip) throws Exception {
//        System.setProperty("file.encoding", ENCODING);
//
//        Unzip unzipper = new Unzip();
//
//        InputStream fileInputStream = new FileInputStream(unzipper.unzip(new URL(stationsZip).openStream(), "output/"));
//        byte[] byteStream = fileInputStream.readAllBytes();
//
//        return climaticRecordBuilderFromByteArray(byteStream);
//    }

//
//    public List<ClimaticRecordBean> climaticRecordBuilderFromByteArray(byte[] ba) throws Exception {
//        System.setProperty("file.encoding", ENCODING);
//
//        Reader reader = new BufferedReader(
//                new InputStreamReader(
//                        new ByteArrayInputStream(ba), ENCODING));
//
//        List<ClimaticRecordBean> beans = new CsvToBeanBuilder(reader)
//                .withType(ClimaticRecordBean.class)
//                .withIgnoreQuotations(true)
//                .build().parse();
//        return beans;
//    }
}