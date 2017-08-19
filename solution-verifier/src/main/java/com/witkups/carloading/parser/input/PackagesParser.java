package com.witkups.carloading.parser.input;

import com.witkups.carloading.InputSection;
import com.witkups.carloading.entity.Host;
import com.witkups.carloading.entity.Package;
import com.witkups.carloading.parser.ObjectParser;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.List;
import java.util.stream.Collectors;

public class PackagesParser extends ObjectParser<List<Package>> {
    private final List<Host> hosts;

    public PackagesParser(InputSection section, List<Host> hosts) {
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
                .id(line[0])
                .sequentialNumber(Integer.valueOf(line[1]))
                .host(findHost(line[2]))
                .height(Integer.valueOf(line[3]))
                .canBePlacedOnPackage("1".equals(line[4]))
                .canOtherPackageBePlaceOn("1".equals(line[5]))
                .build();
    }

    private Host findHost(String anObject) {
        return hosts.stream()
                .filter(host -> host.getId().equals(anObject))
                .findFirst()
                .orElseThrow(() -> new InvalidStateException("Given host not found"));
    }
}
