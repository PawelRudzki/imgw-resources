package utils;


import dao.TestHelper;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HtmlUtilsTest {

    @Test
    public void testsPullDirectoryContents() {

        //given
        TestHelper th = new TestHelper();

        HtmlUtils htmlutils = new HtmlUtils();

        //when

        //it loses last element of the list probably due to toString method?
        List<String> result = htmlutils.pullDirectoryContents("https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/dobowe/klimat/");

        //then
        assertEquals(th.readLineByLineJava8("txt/directory-contents-test.txt"), result.get(result.size()-1) + "\n");
    }

    @Test
    public void testsPullFilesMap() {

        //given
        TestHelper th = new TestHelper();
        HtmlUtils htmlutils = new HtmlUtils();

        //when

        //it loses last element of the list probably due to toString method?
        Map result = htmlutils.pullFilesMap("https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/dobowe/klimat/");

        //then
        assertEquals(th.readLineByLineJava8("txt/files-map.txt"), result.get("2008/") + "\n");
    }
}
