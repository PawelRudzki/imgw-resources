package utils;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import static org.junit.Assert.*;


public class UnzipTest {

    @Test
    public void testsUnzippingWithOneFileArchive() throws Exception {

        //when
        Unzip unzipper = new Unzip();

        //given
        File unzippedFile = unzipper.unzip(
                new FileInputStream("zip/one-file-archive.zip"),
                "output/");

        boolean result = unzippedFile.exists();

        //then
        assertTrue(result);
        unzippedFile.delete();
    }

    @Test
    public void testsUnzippingWithOneFileArchiveOnline() throws Exception {

        //when
        Unzip unzipper = new Unzip();

        //given
        File unzippedFile = unzipper.unzip(
                new URL("https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/dobowe/klimat/2001/2001_01_k.zip").openStream(),
                "output/");

        boolean result = unzippedFile.exists();

        //then
        assertTrue(result);
        unzippedFile.delete();
    }

}
