package com.witkups.carloading.generator;

import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.instance.hosts.Host;
import com.witkups.carloading.instance.packages.Package;
import com.witkups.carloading.instance.vehicle.Vehicle;
import com.witkups.carloading.validation.constraints.Constraints;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.lang.Math.min;
import static java.util.stream.Collectors.toList;

public final class InstanceGenerator {
	private static final Random random = new Random();
	private final Constraints constraints;
	private List<Host> hosts;
	private Vehicle vehicle;

	public InstanceGenerator(Constraints constraints) {
		this.constraints = constraints;
	}

	public Instance prepare() {
		vehicle = prepareVehicle();
		hosts = prepareHosts();
		return new Instance(vehicle, hosts, preparePackages());
	}

	private List<Package> preparePackages() {
		final int maxPackages = constraints.getMaxPackages();
		return IntStream.range(0, getRandomIntValue(maxPackages))
		                .mapToObj(this::preparePackage)
		                .collect(toList());
	}

	private Package preparePackage(int id) {
		final int packHeight = min(random.nextInt(constraints.getMaxPackageHeight()), vehicle.getHeight());
		return Package.builder()
		              .canBePlacedOnPackage(random.nextBoolean())
		              .canOtherPackageBePlacedOn(random.nextBoolean())
		              .sequenceId(random.nextInt(constraints.getMaxSequences()))
		              .height(packHeight)
		              .host(hosts.get(random.nextInt(hosts.size())))
		              .id("" + id)
		              .build();
	}

	private Vehicle prepareVehicle() {
		final int height = getRandomIntValue(constraints.getMaxVehicleHeight());
		final int width = getRandomIntValue(constraints.getMaxVehicleHeight());
		return new Vehicle(width, height);
	}

	private int getRandomIntValue(int max) {
		final int base = max / 2;
		return random.nextInt(base) + base;
	}

	private List<Host> prepareHosts() {
		return IntStream.range(0, constraints.getMaxHosts())
		                .mapToObj(this::buildHost)
		                .collect(toList());
	}

	private Host buildHost(int i) {

		final int length = min(random.nextInt(constraints.getMaxHostLength()), vehicle.getWidth());
		final int width = min(random.nextInt(constraints.getMaxHostWidth()), vehicle.getWidth());
		return Host.builder()
		           .id("" + i)
		           .length(length)
		           .width(width)
		           .build();
	}
}