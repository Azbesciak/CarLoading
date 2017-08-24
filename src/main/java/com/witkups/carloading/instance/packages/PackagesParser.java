package com.witkups.carloading.instance.packages;

import com.witkups.carloading.instance.hosts.Host;
import com.witkups.carloading.processing.Section;
import com.witkups.carloading.processing.SectionParser;

import java.util.List;
import java.util.stream.Collectors;

import static com.witkups.carloading.instance.packages.PackageSectionStructure.*;

public final class PackagesParser extends SectionParser<List<Package>> {
	private final List<Host> hosts;

	public PackagesParser(Section section, List<Host> hosts) {
		super(section);
		this.hosts = hosts;
	}

	@Override
	public List<Package> parse() {
		return section.getSectionStream()
		              .peek(line -> validateLength(line, PACKAGE_FIELDS))
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
		            .filter(host -> host.getId()
		                                .equals(anObject))
		            .findFirst()
		            .orElseThrow(() -> new RuntimeException("Given host not found"));
	}
}
