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
        String result = sd.readFromCSV(is).toString();
        is.close();


        //then
        assertEquals( th.txtToString("txt/stations.txt"), result+"\n");

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

        Set<StationBean> resultList = sd.readFromCSV(is);
        is.close();

        //Some differences in reading .csv files from disk and directly from url require adding "" to station names.

        for(StationBean bean : resultList){
            bean.setName("\""+bean.getName()+"\"");
        }

        String result = resultList.toString();

        //then
        assertEquals( th.txtToString("txt/stations.txt"), result+"\n");

    }

}