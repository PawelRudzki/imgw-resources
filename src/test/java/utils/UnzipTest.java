package utils;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.*;


public class UnzipTest {

    @Test
    public void testsUnzipping() throws Exception {

        //when
        Unzip unzipper = new Unzip();

        //given

        InputStream is =  new FileInputStream("zip/2019_01_k.zip");
        unzipper.unzip(is, "output/");
        is.close();

        File unzippedFile1 = new File("output/k_d_t_01_2019.csv");
        File unzippedFile2 = new File("output/k_d_01_2019.csv");

        boolean result1 = unzippedFile1.exists();
        boolean result2 = unzippedFile1.exists();

        //then
        assertTrue(result1 && result2);
        FileUtils.cleanDirectory(new File("output/"));

    }

    @Test
    public void testsUnzippingFromImgwSite() throws Exception {

        //when
        Unzip unzipper = new Unzip();

        //given

        InputStream is = new URL("https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/dobowe/klimat/2001/2001_01_k.zip").openStream();
        unzipper.unzip(is, "output/");
        is.close();

        File unzippedFile1 = new File("output/k_d_t_01_2001.csv");
        File unzippedFile2 = new File("output/k_d_01_2001.csv");

        boolean result1 = unzippedFile1.exists();
        boolean result2 = unzippedFile1.exists();

        //then
        assertTrue(result1 && result2);
        FileUtils.cleanDirectory(new File("output/"));
    }

}
