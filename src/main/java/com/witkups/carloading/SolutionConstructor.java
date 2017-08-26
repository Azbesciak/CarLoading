package com.witkups.carloading;

import java.io.IOException;
import java.util.stream.Stream;

import static com.witkups.carloading.ConstructorUtils.finalizeWhenAnySolutionFound;
import static com.witkups.carloading.ConstructorUtils.solveForInstanceFromSystemIn;

public final class SolutionConstructor {
	public static void main(String[] args) {
		final Stream<String> solutions;
		try {
			solutions = solveForInstanceFromSystemIn();
			finalizeWhenAnySolutionFound(solutions);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}