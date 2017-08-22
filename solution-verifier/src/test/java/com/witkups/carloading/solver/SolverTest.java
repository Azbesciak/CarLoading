package com.witkups.carloading.solver;

import com.witkups.carloading.InstanceGenerator;
import com.witkups.carloading.entity.*;
import com.witkups.carloading.entity.Package;
import com.witkups.carloading.validation.Constraints;
import com.witkups.carloading.validation.ConstraintsLoader;
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
                .mapToObj(i -> preparePackage(i, 2,2,2, true, true))
                .collect(Collectors.toList());

        final List<Host> hosts = packages.stream().map(Package::getHost).collect(Collectors.toList());
        final Instance instance = new Instance(vehicle, hosts, packages);
//        final Solver.Result result = new Solver.Result();
        final Solution solution = new Solver(instance).prepareSolution().limit(100000).collect(Collectors.toList()).get(0);
        assertEquals(2, solution.getPurpose().getMaxDistance());
        assertEquals(8, solution.getPurpose().getOccupiedPlace());
    }

    @Test
    public void sameEdgesAndCannotBePlacedOnEachOther() {
        final Vehicle vehicle = new Vehicle(10, 10);
        final List<Package> packages = IntStream.range(0, 10)
                .mapToObj(i -> preparePackage(i, 2,2,2, false, false))
                .collect(Collectors.toList());

        final List<Host> hosts = packages.stream().map(Package::getHost).collect(Collectors.toList());
        final Instance instance = new Instance(vehicle, hosts, packages);
        final Solution solution = new Solver(instance).prepareSolution().findFirst().get();
        assertEquals(4, solution.getPurpose().getMaxDistance());
        assertEquals(40, solution.getPurpose().getOccupiedPlace());
    }

    private Package preparePackage(int id, int width, int height, int length, boolean canOtherPackageBePlacedOn, boolean canBePlacedOnPackage) {
        final Host host = Host.builder().width(width).length(length).id("" + id).build();
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
        final Stream<Solution> solutionStream = new Solver(instance).prepareSolution();
        final List<Purpose> results = solutionStream.limit(1000).map(Solution::getPurpose).collect(Collectors.toList());
        results.forEach(System.out::println);
        for (int i = 0; i < results.size() - 1; i++) {
            assertTrue("next solution should be not worse",results.get(i).compareTo(results.get(i + 1)) >= 0);
        }
    }

}