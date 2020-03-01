package utils;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip {

    public File unzip(InputStream is, String outputDirectory) throws Exception {

        byte[] buffer = new byte[2048];

        Path outDir = Paths.get(outputDirectory);

        try (BufferedInputStream bis = new BufferedInputStream(is);
             ZipInputStream stream = new ZipInputStream(bis)) {

            ZipEntry entry;
            while ((entry = stream.getNextEntry()) != null) {

                Path filePath = outDir.resolve(entry.getName());

                try (FileOutputStream fos = new FileOutputStream(filePath.toFile());
                     BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length)) {

                    int len;
                    while ((len = stream.read(buffer)) > 0) {
                        bos.write(buffer, 0, len);
                    }
                    return new File(filePath.toString());
                }
            }
        }

        return null;
    }
}