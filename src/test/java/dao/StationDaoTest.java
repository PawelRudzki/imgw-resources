package dao;

import beans.StationBean;
import org.junit.Test;

import java.io.FileInputStream;

import java.io.InputStream;
import java.net.URL;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class StationDaoTest {

    @Test
    public void testsDataReadedToStationBeans() throws Exception {

        //given
        TestHelper th = new TestHelper();
        StationDao sd = new StationDao();


        //when
        InputStream is = new FileInputStream("csv/wykaz_stacji.csv");

        Set<StationBean> resultSet = sd.readFromCSV(is);
        is.close();

        String result = resultSet.toString();


        //then
        assertEquals( th.txtToString("txt/stations-test-from-disc.txt"), result+"\n");

    }



    @Test
    public void testsPullingAndReadingStationsFromImgwSitetoBeans() throws Exception {

        //given
        TestHelper th = new TestHelper();
        StationDao sd = new StationDao();


        //when
        InputStream is = new URL(
                "https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/wykaz_stacji.csv")
                .openStream();

        Set<StationBean> resultSet = sd.readFromCSV(is);
        is.close();

        //Some differences in reading .csv files from disk and directly from url require explicit adding "" to station names.
        //It also affects beans order in resultSet so there are used different reference txt files for tests
        for(StationBean bean : resultSet){
            bean.setName("\""+bean.getName()+"\"");
        }

        String result = resultSet.toString();

        //then
        assertEquals( th.txtToString("txt/stations-test-from-url.txt"), result+"\n");
    }
}