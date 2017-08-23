package com.witkups.carloading.instance.hosts;

import com.witkups.carloading.processing.Section;
import com.witkups.carloading.processing.SectionParser;

import java.util.List;
import java.util.stream.Collectors;

public class HostsParser extends SectionParser<List<Host>> {

    private static final int ID_INDEX = 0;
    private static final int LENGTH_INDEX = 1;
    private static final int HEIGHT_INDEX = 2;

    public HostsParser(Section section) {
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
