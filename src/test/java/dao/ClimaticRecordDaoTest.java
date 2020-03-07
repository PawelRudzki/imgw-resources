package dao;

import beans.ClimaticRecordBean;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ClimaticRecordDaoTest {

    @Test
    public void testsDataReadedToClimaticRecordBeans() throws Exception {

        //given
        TestHelper th = new TestHelper();
        ClimaticRecordDao crd = new ClimaticRecordDao();


        //when
        List<ClimaticRecordBean> resultList = crd.climaticRecordBuilder(new FileInputStream("csv/k_d_t_01_2019.csv"));

        String resultString = resultList.get(resultList.size()-1).toString();

        //then
        assertEquals(th.readLineByLineJava8("txt/climatic-records-test.txt"), resultString+"\n");
    }

    @Test
    public void testsDataReadedToClimaticRecordBeansFromZip() throws Exception {

        //given
        TestHelper th = new TestHelper();
        ClimaticRecordDao crd = new ClimaticRecordDao();


        //when
        List<ClimaticRecordBean> resultList = crd.pullClimaticRecordsFromZip(new FileInputStream("zip/2019_01_k.zip"));

        String resultString = resultList.get(resultList.size()-1).toString();

        //then
        assertEquals(th.readLineByLineJava8("txt/climatic-records-test.txt"), resultString+"\n");
    }

    @Test
    public void testsDataReadedToClimaticRecordBeansFromImgwOnlineZip() throws Exception {

        //given
        TestHelper th = new TestHelper();
        ClimaticRecordDao crd = new ClimaticRecordDao();


        //when
        List<ClimaticRecordBean> resultList = crd.pullClimaticRecordsFromZip(new URL("https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/dobowe/klimat/2019/2019_01_k.zip").openStream());

        String resultString = resultList.get(resultList.size()-1).toString();


        //then
        assertEquals(th.txtToString("txt/climatic-records-test.txt"), resultString+"\n");
    }

}
