package utils;

import dao.ClimaticRecordDao;
import dao.TestHelper;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HtmlUtilsTest {

    @Test
    public void testsPullDirectoryContents() {

        //given
        TestHelper th = new TestHelper();

        HtmlUtils htmlutils = new HtmlUtils();

        //when

        //it looses last element of the list probably due to toString method
        String result = htmlutils.pullDirectoryContents("https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/dobowe/klimat/").toString();

        //then
        assertEquals(th.readLineByLineJava8("txt/directory-contents-test.txt"), result + "\n");


    }
}
