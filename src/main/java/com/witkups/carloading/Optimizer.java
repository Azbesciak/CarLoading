package com.witkups.carloading;

import java.io.IOException;
import java.util.stream.Stream;

import static com.witkups.carloading.ConstructorUtils.getAllSolutions;
import static com.witkups.carloading.ConstructorUtils.solveForInstanceFromSystemIn;

public final class Optimizer {
	public static void main(String[] args) throws IOException {
		final Stream<String> solutions;
		solutions = solveForInstanceFromSystemIn();
		getAllSolutions(solutions);
	}
}