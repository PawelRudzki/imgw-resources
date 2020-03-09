package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HtmlUtils {

    public List<String> pullDirectoryContents(String url) {
        Document doc;
        Elements dirs = null;
        try {
            doc = Jsoup.connect(url).get();
            dirs = doc.getElementsByTag("a");
            List textContents = dirs.eachText();

            //cutting out title records
            textContents = textContents.subList(5, textContents.size() - 1);


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

        Map<String, List<String>> filesMap = new HashMap<>();

        pullDirectoryContents(url).stream()
                .filter(a -> "/".equals(a.substring(a.length() - 1)))
                .forEach(a -> filesMap.put(a, pullDirectoryContents(url + a)));

        return filesMap;
    }
}

