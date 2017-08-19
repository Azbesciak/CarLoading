package com.witkups.carloading.parser;

import java.util.List;

public interface Reader<T> {
    List<T> read() throws Exception;
}
