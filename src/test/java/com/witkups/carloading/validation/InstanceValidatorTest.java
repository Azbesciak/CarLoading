package com.witkups.carloading.validation;

import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.instance.hosts.Host;
import com.witkups.carloading.instance.packages.Package;
import com.witkups.carloading.instance.vehicle.Vehicle;
import com.witkups.carloading.validation.constraints.Constraints;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.String.valueOf;
import static org.junit.Assert.*;

public class InstanceValidatorTest {

	private Constraints constraints;
	@Before
	public void setUp() throws Exception {
		constraints = Constraints.builder()
		                         .maxHostLength(2)
		                         .maxHosts(2)
		                         .maxHostWidth(2)
		                         .maxPackageHeight(2)
		                         .maxPackages(2)
		                         .maxSequences(1)
		                         .maxVehicleHeight(2)
		                         .maxVehicleWidth(2)
		                         .build();
	}

	@Test
	public void validateHostsTooWide() throws Exception {
		final Vehicle vehicle = new Vehicle(2, 2);
		final Host host = Host.builder().width(3).length(2).id("qwe").build();
		final List<Host> hosts = Collections.singletonList(host);
		final Package pack = Package.builder().id("xyz").host(host).build();
		final List<Package> packages = Collections.singletonList(pack);

		final Instance instance = new Instance(vehicle, hosts, packages);
		try {
			new InstanceValidator(instance, constraints).validate();
			fail();
		} catch (ConstraintsValidationError e) {
			assertTrue(e.getMessage().contains("Host"));
			assertTrue(e.getMessage().contains("width"));
			assertTrue(e.getMessage().contains(valueOf(constraints.getMaxHostWidth())));
		}
	}

	@Test
	public void validateHostTooLong() {
		final Vehicle vehicle = new Vehicle(2, 2);
		final Host host = Host.builder().width(2).length(3).id("qwe").build();
		final List<Host> hosts = Collections.singletonList(host);
		final Package pack = Package.builder().id("xyz").host(host).build();
		final List<Package> packages = Collections.singletonList(pack);

		final Instance instance = new Instance(vehicle, hosts, packages);
		try {
			new InstanceValidator(instance, constraints).validate();
			fail();
		} catch (ConstraintsValidationError e) {
			assertTrue(e.getMessage().contains("Host"));
			assertTrue(e.getMessage().contains("length"));
			assertTrue(e.getMessage().contains(valueOf(constraints.getMaxHostLength())));
		}
	}

	@Test
	public void hostAmountExceeded() {
		final Vehicle vehicle = new Vehicle(2, 2);
		final Host host1 = Host.builder().width(2).length(3).id("qw1").build();
		final Host host2 = Host.builder().width(2).length(3).id("qw2").build();
		final Host host3 = Host.builder().width(2).length(3).id("qw3").build();
		final List<Host> hosts = Arrays.asList(host1, host2, host3);
		final Package pack = Package.builder().id("xyz").host(host1).build();
		final List<Package> packages = Collections.singletonList(pack);

		final Instance instance = new Instance(vehicle, hosts, packages);
		try {
			new InstanceValidator(instance, constraints).validate();
			fail();
		} catch (ConstraintsValidationError e) {
			assertTrue(e.getMessage().contains("More hosts than allowed"));
			assertTrue(e.getMessage().contains(valueOf(constraints.getMaxHosts())));
		}
	}

	@Test
	public void vehicleTooHigh() {
		final Vehicle vehicle = new Vehicle(2, 3);
		final Host host1 = Host.builder().width(2).length(3).id("qw1").build();
		final List<Host> hosts = Collections.singletonList(host1);
		final Package pack = Package.builder().id("xyz").host(host1).build();
		final List<Package> packages = Collections.singletonList(pack);

		final Instance instance = new Instance(vehicle, hosts, packages);
		try {
			new InstanceValidator(instance, constraints).validate();
			fail();
		} catch (ConstraintsValidationError e) {
			assertTrue(e.getMessage().contains("Vehicle too high"));
			assertTrue(e.getMessage().contains(valueOf(constraints.getMaxVehicleHeight())));
		}
	}

	@Test
	public void vehicleTooWide() {
		final Vehicle vehicle = new Vehicle(3, 2);
		final Host host1 = Host.builder().width(2).length(3).id("qw1").build();
		final List<Host> hosts = Collections.singletonList(host1);
		final Package pack = Package.builder().id("xyz").host(host1).build();
		final List<Package> packages = Collections.singletonList(pack);

		final Instance instance = new Instance(vehicle, hosts, packages);
		try {
			new InstanceValidator(instance, constraints).validate();
			fail();
		} catch (ConstraintsValidationError e) {
			assertTrue(e.getMessage().contains("Vehicle too wide"));
			assertTrue(e.getMessage().contains(valueOf(constraints.getMaxVehicleWidth())));
		}
	}

	@Test
	public void tooManyPackages() {
		final Vehicle vehicle = new Vehicle(2, 2);
		final Host host1 = Host.builder().width(2).length(2).id("qw1").build();
		final List<Host> hosts = Collections.singletonList(host1);
		final Package pack1 = Package.builder().id("xyz").host(host1).build();
		final Package pack2 = Package.builder().id("xy").host(host1).build();
		final Package pack3 = Package.builder().id("x").host(host1).build();
		final List<Package> packages = Arrays.asList(pack1, pack2, pack3);

		final Instance instance = new Instance(vehicle, hosts, packages);
		try {
			new InstanceValidator(instance, constraints).validate();
			fail();
		} catch (ConstraintsValidationError e) {
			assertTrue(e.getMessage().contains("More packages than allowed"));
			assertTrue(e.getMessage().contains(valueOf(constraints.getMaxPackages())));
		}
	}

	@Test
	public void tooManySequences() {
		final Vehicle vehicle = new Vehicle(2, 2);
		final Host host1 = Host.builder().width(2).length(2).id("qw1").build();
		final List<Host> hosts = Collections.singletonList(host1);
		final Package pack1 = Package.builder().id("xyz").host(host1).sequenceId(1).build();
		final Package pack2 = Package.builder().id("xy").host(host1).sequenceId(2).build();
		final List<Package> packages = Arrays.asList(pack1, pack2);

		final Instance instance = new Instance(vehicle, hosts, packages);
		try {
			new InstanceValidator(instance, constraints).validate();
			fail();
		} catch (ConstraintsValidationError e) {
			assertTrue(e.getMessage().contains("More sequences than allowed"));
			assertTrue(e.getMessage().contains(valueOf(constraints.getMaxSequences())));
		}
	}

	@Test
	public void packageTooHigh() {
		final Vehicle vehicle = new Vehicle(2, 2);
		final Host host1 = Host.builder().width(2).length(2).id("qw1").build();
		final List<Host> hosts = Collections.singletonList(host1);
		final Package pack1 = Package.builder().id("xyz").host(host1).sequenceId(1).height(3).build();
		final List<Package> packages = Collections.singletonList(pack1);

		final Instance instance = new Instance(vehicle, hosts, packages);
		try {
			new InstanceValidator(instance, constraints).validate();
			fail();
		} catch (ConstraintsValidationError e) {
			assertTrue(e.getMessage().contains("Package is higher than allowed"));
			assertTrue(e.getMessage().contains(valueOf(constraints.getMaxPackageHeight())));
		}
	}

	@Test
	public void packagesIdsMustBeUnique() {
		final Vehicle vehicle = new Vehicle(2, 2);
		final Host host1 = Host.builder().width(2).length(2).id("qw1").build();
		final List<Host> hosts = Collections.singletonList(host1);
		final Package pack1 = Package.builder().id("xyz").host(host1).sequenceId(1).build();
		final Package pack2 = Package.builder().id("xyz").host(host1).sequenceId(2).build();
		final List<Package> packages = Arrays.asList(pack1, pack2);

		final Instance instance = new Instance(vehicle, hosts, packages);
		try {
			new InstanceValidator(instance, constraints).validate();
			fail();
		} catch (ConstraintsValidationError e) {
			assertTrue(e.getMessage().contains("Package ids must be unique"));
		}
	}


	@Test
	public void hostsIdsMustBeUnique() {
		final Vehicle vehicle = new Vehicle(2, 2);
		final Host host1 = Host.builder().width(2).length(2).id("qw1").build();
		final Host host2 = Host.builder().width(1).length(2).id("qw1").build();
		final List<Host> hosts = Arrays.asList(host1, host2);
		final Package pack1 = Package.builder().id("xyz").host(host1).sequenceId(1).build();
		final List<Package> packages = Collections.singletonList(pack1);

		final Instance instance = new Instance(vehicle, hosts, packages);
		try {
			new InstanceValidator(instance, constraints).validate();
			fail();
		} catch (ConstraintsValidationError e) {
			assertTrue(e.getMessage().contains("Host ids must be unique"));
		}
	}
}