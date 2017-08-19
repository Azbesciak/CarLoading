package com.witkups.carloading.parser;

import java.io.*;

public class FileReader {

    public static InputStream read(String filePath) throws IOException {
        final File file = new File(filePath);
        final FileInputStream fileInputStream = new FileInputStream(file);
        return new BufferedInputStream(fileInputStream);
    }

}
