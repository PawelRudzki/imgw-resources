package dao;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

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



        //Read file content into string with - Files.lines(Path path, Charset cs)

        public String readLineByLineJava8(String filePath)
        {
            StringBuilder contentBuilder = new StringBuilder();

            try (Stream<String> stream = Files.lines( Paths.get(filePath), Charset.defaultCharset()))
            {
                stream.forEach(s -> contentBuilder.append(s).append("\n"));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return contentBuilder.toString();
        }
    }


