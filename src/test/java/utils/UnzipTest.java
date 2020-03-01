package utils;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;


public class UnzipTest {

    @Test
    public void testsUnzippingWithOneFileArchive() throws Exception {

        //when
        Unzip unzipper = new Unzip();

        //given

        File unzippedFile = unzipper.unzip(
                "zip/one-file-archive.zip",
                "output/");
        boolean result = unzippedFile.exists();

        //then
        assertTrue(result);
        unzippedFile.delete();




    }
}
