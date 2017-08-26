package com.witkups.carloading.solver;

import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.instance.packages.Package;
import com.witkups.carloading.solution.Solution;
import com.witkups.carloading.solution.packageplacements.PackagePlacement;

import java.util.List;
import java.util.stream.Collectors;

final class InitialSolutionMaker extends SolutionMaker {
	InitialSolutionMaker(Instance instance) {
		super(instance);
	}

	Solution findSolution() {
		final List<Package> packages = instance.getPackages();
		packages.sort(new InitialPackageOrderComparator());
		final List<PackagePlacement> placements = initializePackagePlacements(packages);
		placePackages(placements);
		final Solution solution = new Solution(placements);
		solutionsRepo.add(solution);
		return solution;
	}

	private List<PackagePlacement> initializePackagePlacements(List<Package> packages) {
		return packages.stream()
		               .map(pack -> new PackagePlacement(pack, false, null))
		               .collect(Collectors.toList());
	}
}
