package com.witkups.carloading.processing.reader;

import lombok.AllArgsConstructor;

import java.io.*;

@AllArgsConstructor
public final class FileReader implements Reader<InputStream>{
	private final String filePath;

	public InputStream read() throws IOException {
		final File file = new File(filePath);
		final FileInputStream fileInputStream = new FileInputStream(file);
		return new BufferedInputStream(fileInputStream);
	}
}