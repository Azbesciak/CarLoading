package com.witkups.carloading.instance.packages;

import com.witkups.carloading.processing.Section;
import com.witkups.carloading.instance.hosts.Host;
import com.witkups.carloading.processing.SectionParser;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.List;
import java.util.stream.Collectors;

public class PackagesParser extends SectionParser<List<Package>> {
    private static final int ID_INDEX = 0;
    private static final int SEQUENCE_ID_INDEX = 1;
    private static final int HOST_ID_INDEX = 2;
    private static final int HEIGHT_INDEX = 3;
    private static final int CAN_BE_PLACED_ON_PACKAGE_INDEX = 4;
    private static final int CAN_OTHER_PACKAGE_BE_PLACED_ON_INDEX = 5;
    private final List<Host> hosts;

    public PackagesParser(Section section, List<Host> hosts) {
        super(section);
        this.hosts = hosts;
    }

    @Override
    public List<Package> parse() {
        return section.getSectionStream()
                .map(this::parse)
                .collect(Collectors.toList());
    }

    private Package parse(String[] line) {
        return Package.builder()
                .id(line[ID_INDEX])
                .sequenceId(Integer.valueOf(line[SEQUENCE_ID_INDEX]))
                .host(findHost(line[HOST_ID_INDEX]))
                .height(Integer.valueOf(line[HEIGHT_INDEX]))
                .canBePlacedOnPackage("1".equals(line[CAN_BE_PLACED_ON_PACKAGE_INDEX]))
                .canOtherPackageBePlacedOn("1".equals(line[CAN_OTHER_PACKAGE_BE_PLACED_ON_INDEX]))
                .build();
    }

    private Host findHost(String anObject) {
        return hosts.stream()
                .filter(host -> host.getId().equals(anObject))
                .findFirst()
                .orElseThrow(() -> new InvalidStateException("Given host not found"));
    }
}
