package dao;

import org.apache.commons.io.FileUtils;
import utils.Unzip;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TestHelper {

    //this constructor makes sure that output/ folder is empty before test
    public TestHelper() throws IOException {
        FileUtils.cleanDirectory(new File("output/"));

    }

    public String txtToString(String path) throws FileNotFoundException {
        StringBuilder contentBuilder = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader(path));
        {

            String sCurrentLine = null;
            while (true) {
                try {
                    if (!((sCurrentLine = br.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                contentBuilder.append(sCurrentLine).append("\n");
            }

        }
        return contentBuilder.toString();
    }


    //Read file content into string with - Files.lines(Path path, Charset cs)

    public String readLineByLineJava8(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), Charset.defaultCharset())) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

    public void unzipExampleRecords() throws Exception {
        Unzip unzipper = new Unzip();
        InputStream is = new URL("https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/dobowe/klimat/2001/2001_01_k.zip").openStream();
        unzipper.unzip(is, "output/");
        is.close();
    }
}


