package com.witkups.carloading.parser.input;

import com.witkups.carloading.InputSection;
import com.witkups.carloading.entity.Host;
import com.witkups.carloading.parser.ObjectParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class HostsParser extends ObjectParser<List<Host>> {


    private static final int ID_INDEX = 0;
    private static final int LENGTH_INDEX = 1;
    private static final int HEIGHT_INDEX = 2;

    public HostsParser(InputSection section) {
        super(section);
    }

    @Override
    public List<Host> parse() {
        return section.getSectionStream()
                .map(this::buildHost)
                .collect(Collectors.toList());
    }

    private Host buildHost(String[] line) {
        return Host.builder()
                .id(getHostId(line))
                .length(getHostLength(line))
                .width(getHostHeight(line))
                .build();
    }

    private String getHostId(String[] line) {
        return line[ID_INDEX];
    }

    private int getHostLength(String[] line) {
        return Integer.valueOf(line[LENGTH_INDEX]);
    }

    private int getHostHeight(String[] line) {
        return Integer.valueOf(line[HEIGHT_INDEX]);
    }
}
