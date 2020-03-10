package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class HtmlUtils {

    public List<String> pullDirectoryContents(String url) {
        Document doc;
        Elements dirs = null;
        try {
            doc = Jsoup.connect(url).get();
            dirs = doc.getElementsByTag("a");
            List textContents = dirs.eachText();

            //cutting out title records
            textContents = textContents.subList(5, textContents.size());


            List<String> stringList = new LinkedList<>();
            for (int i = 0; i < textContents.size(); i++) {
                stringList.add((String) textContents.get(i));
            }
            return stringList;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Map pullFilesMap(String url) {

        Map<String, List<String>> filesMap = new TreeMap<>();

        pullDirectoryContents(url).stream()
                .filter(a -> "/".equals(a.substring(a.length() - 1)))
                .forEach(a -> filesMap.put(a, pullDirectoryContents(url + a)));

        return filesMap;
    }
}

