package integration;

import beans.ClimaticRecordBean;
import dao.ClimaticRecordDao;
import dao.TestHelper;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import utils.Unzip;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UnzipAndReadDataTest {

    @Test
    public void testsDataReadedToClimaticRecordBeansFromZip() throws Exception {

        //given
        TestHelper th = new TestHelper();
        ClimaticRecordDao crd = new ClimaticRecordDao();
        Unzip unzipper = new Unzip();


        //when

        InputStream fis = new FileInputStream("zip/2019_01_k.zip");
        unzipper.unzip(fis, "output/");
        fis.close();


        fis = new FileInputStream("output/k_d_t_01_2019.csv");
        List<ClimaticRecordBean> resultList = crd.climaticRecordBuilder(fis);

        //if not closed won't be able to delete unzipped file at the end of the test
        fis.close();


        String resultString = resultList.get(resultList.size()-1).toString();

        //then
        assertEquals(th.readLineByLineJava8("txt/climatic-records-test.txt"), resultString+"\n");


        FileUtils.cleanDirectory(new File("output/"));

    }


    @Test
    public void testsDataReadedToClimaticRecordBeansFromOnlineZip() throws Exception {

        //given
        TestHelper th = new TestHelper();
        ClimaticRecordDao crd = new ClimaticRecordDao();
        Unzip unzipper = new Unzip();


        //when

        InputStream is = new URL(
                "https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/dobowe/klimat/2019/2019_01_k.zip").openStream();

        unzipper.unzip(is, "output/");


        InputStream fis = new FileInputStream("output/k_d_t_01_2019.csv");
        List<ClimaticRecordBean> resultList = crd.climaticRecordBuilder(fis);
        fis.close();

        String resultString = resultList.get(resultList.size()-1).toString();

        //then
        assertEquals(th.readLineByLineJava8("txt/climatic-records-test.txt"), resultString+"\n");

        FileUtils.cleanDirectory(new File("output/"));
    }
}
