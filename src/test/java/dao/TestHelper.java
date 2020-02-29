package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestHelper {
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
}
