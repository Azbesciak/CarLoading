package com.witkups.carloading.instance;

import com.witkups.carloading.instance.hosts.Host;
import com.witkups.carloading.instance.hosts.HostsParser;
import com.witkups.carloading.instance.packages.Package;
import com.witkups.carloading.instance.packages.PackagesParser;
import com.witkups.carloading.instance.vehicle.Vehicle;
import com.witkups.carloading.instance.vehicle.VehicleParser;
import com.witkups.carloading.processing.ParseValidationError;
import com.witkups.carloading.processing.Parser;
import com.witkups.carloading.processing.Section;
import com.witkups.carloading.processing.reader.InputReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.witkups.carloading.instance.InstanceSectionsStructure.*;

public final class InstanceParser implements Parser<Instance> {
	private List<Section> sections;

	public InstanceParser(InputStream inputStream) throws IOException {
		this.sections = new InputReader(INSTANCE_SECTIONS, inputStream).read();
	}

	public Instance parse() {
		final Vehicle vehicle = getVehicle();
		final List<Host> hosts = getHosts();
		final List<Package> packages = getPackages(hosts);

		return new Instance(vehicle, hosts, packages);
	}

	private Vehicle getVehicle() {
		final Section vehicleSection = getSection(VEHICLE_SECTION_INDEX);
		return new VehicleParser(vehicleSection).parse();
	}

	private List<Host> getHosts() {
		final Section hostsSection = getSection(HOSTS_SECTION_INDEX);
		return new HostsParser(hostsSection).parse();
	}

	private List<Package> getPackages(List<Host> hosts) {
		final Section packagesSection = getSection(PACKAGES_SECTION_INDEX);
		return new PackagesParser(packagesSection, hosts).parse();
	}

	private Section getSection(int sectionIndex) {
		return sections.get(sectionIndex);
	}
}