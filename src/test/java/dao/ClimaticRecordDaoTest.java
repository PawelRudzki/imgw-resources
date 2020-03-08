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
        List<ClimaticRecordBean> resultList = crd.climaticRecordBuilder(is);
        is.close();

        String resultString = resultList.get(resultList.size()-1).toString();

        //then
        assertEquals(th.readLineByLineJava8("txt/climatic-records-test.txt"), resultString+"\n");

    }
}
