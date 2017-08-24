package com.witkups.carloading.processing.reader;

import java.io.*;

public class FileReader {
	private final String filePath;

	public FileReader(String filePath) {
		this.filePath = filePath;
	}

	public InputStream read() throws IOException {
		final File file = new File(filePath);
		final FileInputStream fileInputStream = new FileInputStream(file);
		return new BufferedInputStream(fileInputStream);
	}
}