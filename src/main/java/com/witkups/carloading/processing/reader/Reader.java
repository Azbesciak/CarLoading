package com.witkups.carloading.processing.reader;

public interface Reader<T> {
	T read() throws Exception;
}
