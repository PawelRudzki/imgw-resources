package dao;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StationDaoTest {

    @Test
    public void testsDataReadedToStationBeans() throws Exception {

        //given
        TestHelper th = new TestHelper();
        StationDao sd = new StationDao();


        //when
        String result = sd.stationBuilder("csv/wykaz_stacji.csv").toString();


        //then
        assertEquals( th.txtToString("stations.txt"), result+"\n");

    }

}