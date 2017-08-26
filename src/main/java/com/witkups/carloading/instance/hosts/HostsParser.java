package com.witkups.carloading.instance.hosts;

import com.witkups.carloading.processing.Section;
import com.witkups.carloading.processing.SectionParser;

import java.util.List;
import java.util.stream.Collectors;

import static com.witkups.carloading.instance.hosts.HostSectionStructure.*;

public final class HostsParser extends SectionParser<List<Host>> {
	public HostsParser(Section section) {
		super(section);
	}

	@Override
	public List<Host> parse() {
		return section.getSectionStream()
		              .peek(line -> validateLength(line, HOST_FIELDS))
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