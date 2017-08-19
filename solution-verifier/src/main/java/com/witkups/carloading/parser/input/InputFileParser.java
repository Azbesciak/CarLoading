package com.witkups.carloading.parser.input;

import com.witkups.carloading.InputSection;
import com.witkups.carloading.entity.Host;
import com.witkups.carloading.entity.Package;
import com.witkups.carloading.entity.Vehicle;
import com.witkups.carloading.parser.FileParser;

import java.io.IOException;
import java.util.List;

public class InputFileParser extends FileParser<InputParseResult> {

    private static final int VEHICLE_SECTION_INDEX = 0;
    private static final int HOSTS_SECTION_INDEX = 1;
    private static final int PACKAGES_SECTION_INDEX = 2;

    public InputFileParser(String inputFilePath) throws IOException {
        super(3, inputFilePath);
    }

    public InputParseResult parse() {

        final Vehicle vehicle = getVehicle();
        final List<Host> hosts = getHosts();
        final List<Package> packages = getPackages(hosts);

        return new InputParseResult(vehicle, hosts, packages);
    }

    private Vehicle getVehicle() {
        final InputSection vehicleSection = getSection(VEHICLE_SECTION_INDEX);
        return new VehicleParser(vehicleSection).parse();
    }

    private List<Host> getHosts() {
        final InputSection hostsSection = getSection(HOSTS_SECTION_INDEX);
        return new HostsParser(hostsSection).parse();
    }

    private List<Package> getPackages(List<Host> hosts) {
        final InputSection packagesSection = getSection(PACKAGES_SECTION_INDEX);
        return new PackagesParser(packagesSection, hosts).parse();
    }
    private InputSection getSection(int sectionIndex) {
        return data.get(sectionIndex);
    }

}
