package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class HtmlUtils {

    public List<String> pullDirectoryContents(String url) {
        Document doc;
        String title = "";
        Elements dirs = null;
        try {
            doc = Jsoup.connect(url).get();
            title = doc.title();
            dirs = doc.getElementsByTag("a");
            List textNodes = dirs.textNodes();
            textNodes = textNodes.subList(5, textNodes.size() - 1);
            return textNodes;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
