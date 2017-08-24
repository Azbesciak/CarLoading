package com.witkups.carloading;

import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.instance.InstanceParser;
import com.witkups.carloading.processing.Section;
import com.witkups.carloading.solution.Solution;
import com.witkups.carloading.solution.SolutionSerializer;
import com.witkups.carloading.solver.Solver;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class SolutionConstructor {
	public static void main(String[] args) throws IOException {
		final Instance instance = readInstanceFromSystemIn();
		final Stream<Solution> solutionStream = generateSolutions(instance);
		final Stream<String> stringStream = printSolutions(solutionStream);
		finalizeWhenAnyFound(stringStream);
	}

	public static Stream<Solution> generateSolutions(Instance instance) {
		return new Solver(instance).generateBetterSolutions();
	}

	public static Instance readInstanceFromSystemIn() throws IOException {
		System.out.println("Pass an instance data:");
		return new InstanceParser(System.in).parse();
	}

	public static void finalizeWhenAnyFound(Stream<String> solutions) {
		solutions.sequential().findAny();
	}

	public static Stream<String> printSolutions(Stream<Solution> solutions) {
		return solutions.map(SolutionConstructor::stringifySolution)
		                .peek(System.out::println);
	}

	public static String stringifySolution(Solution solution) {
		final List<Section> serialize = new SolutionSerializer(solution).serialize();
		return Section.stringifySections(serialize);
	}
}
