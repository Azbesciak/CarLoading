package com.witkups.carloading.solver;

import com.witkups.carloading.generator.InstanceGenerator;
import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.instance.hosts.Host;
import com.witkups.carloading.instance.packages.Package;
import com.witkups.carloading.instance.vehicle.Vehicle;
import com.witkups.carloading.solution.Solution;
import com.witkups.carloading.solution.purpose.Purpose;
import com.witkups.carloading.validation.constraints.Constraints;
import com.witkups.carloading.validation.constraints.ConstraintsLoader;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SolverTest {
	@Test
	public void sameEdgesAndCanBePlacedOnEachOther() throws Exception {
		final Vehicle vehicle = new Vehicle(10, 10);
		final List<Package> packages = IntStream.range(0, 10)
		                                        .mapToObj(i -> preparePackage(i, 2, 2, 2, true, true))
		                                        .collect(Collectors.toList());

		final List<Host> hosts = packages.stream()
		                                 .map(Package::getHost)
		                                 .collect(Collectors.toList());
		final Instance instance = new Instance(vehicle, hosts, packages);
		final Solution solution = new Solver(instance).generateSolutions()
		                                              .findFirst()
		                                              .get();
		assertEquals(2, solution.getPurpose()
		                        .getMaxDistance());
		assertEquals(8, solution.getPurpose()
		                        .getOccupiedPlace());
	}

	@Test
	public void sameEdgesAndCannotBePlacedOnEachOther() {
		final Vehicle vehicle = new Vehicle(10, 10);
		final List<Package> packages = IntStream.range(0, 10)
		                                        .mapToObj(i -> preparePackage(i, 2, 2, 2, false, false))
		                                        .collect(Collectors.toList());

		final List<Host> hosts = packages.stream()
		                                 .map(Package::getHost)
		                                 .collect(Collectors.toList());
		final Instance instance = new Instance(vehicle, hosts, packages);
		final Solution solution = new Solver(instance).generateSolutions()
		                                              .findFirst()
		                                              .get();
		assertEquals(4, solution.getPurpose()
		                        .getMaxDistance());
		assertEquals(40, solution.getPurpose()
		                         .getOccupiedPlace());
	}

	private Package preparePackage(int id, int width, int height, int length, boolean canOtherPackageBePlacedOn,
			boolean canBePlacedOnPackage) {
		final Host host = Host.builder()
		                      .width(width)
		                      .length(length)
		                      .id("" + id)
		                      .build();
		return Package.builder()
		              .host(host)
		              .canOtherPackageBePlacedOn(canOtherPackageBePlacedOn)
		              .canBePlacedOnPackage(canBePlacedOnPackage)
		              .id("" + id)
		              .sequenceId(id)
		              .height(height)
		              .build();
	}

	@Test
	public void validateImprovingSolution() {
		final Constraints constraints = new ConstraintsLoader().loadProperties();
		final Instance instance = new InstanceGenerator(constraints).prepare();
		final Stream<Solution> solutionStream = new Solver(instance).generateBetterSolutions();
		final List<Purpose> results = solutionStream.sequential()
		                                            .map(Solution::getPurpose)
		                                            .peek(System.out::println)
		                                            .limit(2)
		                                            .collect(Collectors.toList());
		for (int i = 0; i < results.size() - 1; i++) {
			assertTrue("next solution should be not worse", results.get(i)
			                                                       .compareTo(results.get(i + 1)) >= 0);
		}
	}

}