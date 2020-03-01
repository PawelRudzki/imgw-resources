package dao;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ClimaticRecordDaoTest {

    @Test
    public void testsDataReadedToClimaticRecordBeans() throws Exception {

        //given
        TestHelper th = new TestHelper();
        ClimaticRecordDao crd = new ClimaticRecordDao();


        //when
        String result = crd.climaticRecordBuilder("csv/k_d_01_2001.csv").toString();


        //then
        assertEquals(th.txtToString("txt/climatic-records.txt"), result + "\n");
    }

    @Test
    public void testsDataReadedToClimaticRecordBeansFromZip() throws Exception {

        //given
        TestHelper th = new TestHelper();
        ClimaticRecordDao crd = new ClimaticRecordDao();


        //when
        String result = crd.climaticRecordBuilder("csv/k_d_01_2001.csv").toString();


        //then
        assertEquals(th.txtToString("txt/climatic-records.txt"), result + "\n");
    }

}
