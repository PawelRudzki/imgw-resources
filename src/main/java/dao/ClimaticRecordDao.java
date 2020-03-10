package dao;

import beans.ClimaticRecordBean;
import com.opencsv.bean.CsvToBeanBuilder;
import utils.HtmlUtils;
import utils.Unzip;

import java.io.*;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TreeMap;
import java.util.stream.Stream;


public class ClimaticRecordDao {

    private final String ENCODING = "windows-1250";

    public List<ClimaticRecordBean> toBeans(InputStream is) throws Exception {

        Reader reader = new BufferedReader(
                new InputStreamReader(
                        is, ENCODING));
        List<ClimaticRecordBean> beans = new CsvToBeanBuilder(reader)
                .withType(ClimaticRecordBean.class)
                .withIgnoreQuotations(true)
                .build().parse();
        return beans;
    }

    public void pullData() throws Exception {

        HtmlUtils htmlUtils = new HtmlUtils();
        Unzip unzip = new Unzip();
        List<ClimaticRecordBean> pathsList = new LinkedList();
        TreeMap<String, LinkedList<String>> filesMap = (TreeMap) htmlUtils.pullFilesMap("https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/dobowe/klimat/");

        filesMap.forEach((key, value) -> {
            value.forEach(file -> {
                InputStream is = null;

                //rule out files starting with "k_d_t" - this data doesn't fit to ClimaticRecordBeans
                try {
                    is = new URL("https://dane.imgw.pl/data/dane_pomiarowo_obserwacyjne/dane_meteorologiczne/dobowe/klimat/" + key + file).openStream();
                    unzip.unzip(is, "output/");
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }

    public List<ClimaticRecordBean> readData() {

        List<ClimaticRecordBean> crbList = new LinkedList<>();
        try (Stream<Path> walk = Files.walk(Paths.get("output/"))) {
            walk
                    .filter(b -> b.toString().length()>11 && b.toString().charAt(11)!='t')
                    .forEach(a -> {
                        InputStream is = null;
                        try {
                            is = new FileInputStream(a.toFile());
                            crbList.addAll(toBeans(is));
                            is.close();
                            Files.delete(a);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
            return crbList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

