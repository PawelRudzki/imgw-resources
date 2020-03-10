package dao;

import beans.ClimaticRecordBean;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import utils.Unzip;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
        InputStream is = new FileInputStream("csv/k_d_t_01_2019.csv");
        List<ClimaticRecordBean> resultList = crd.toBeans(is);
        is.close();

        String resultString = resultList.get(resultList.size()-1).toString();

        //then
        assertEquals(th.readLineByLineJava8("txt/climatic-records-test.txt"), resultString+"\n");
    }

    @Test
    public void testsPullData() throws Exception {
        //given
        TestHelper th = new TestHelper();
        ClimaticRecordDao crd = new ClimaticRecordDao();


        //when
        crd.pullData();


        //then
        assertEquals(556, new File("output/").listFiles().length);
        FileUtils.cleanDirectory(new File("output/"));

    }

    @Test
    public void testsReadingFiles() throws Exception {
        //given
        TestHelper th = new TestHelper();
        ClimaticRecordDao crd = new ClimaticRecordDao();
        Unzip unzipper = new Unzip();



        //when

        InputStream is = new URL("https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/dobowe/klimat/2001/2001_01_k.zip").openStream();
        unzipper.unzip(is, "output/");
        is.close();

        List<ClimaticRecordBean> crbList = crd.readData();
        crbList.forEach(a->a.toString());

        //then
        assertEquals(th.readLineByLineJava8("txt/read-climatic-records-test.txt"), crbList.get(crbList.size()-1)+"\n");
        FileUtils.cleanDirectory(new File("output/"));

    }



}
