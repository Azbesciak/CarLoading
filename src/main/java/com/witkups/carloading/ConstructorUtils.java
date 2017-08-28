package com.witkups.carloading;

import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.instance.InstanceParser;
import com.witkups.carloading.processing.Section;
import com.witkups.carloading.solution.Solution;
import com.witkups.carloading.solution.SolutionSerializer;
import com.witkups.carloading.solver.Solver;
import com.witkups.carloading.validation.InstanceValidator;
import com.witkups.carloading.validation.SolutionValidator;
import com.witkups.carloading.validation.constraints.Constraints;
import com.witkups.carloading.validation.constraints.ConstraintsLoader;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
final class ConstructorUtils {

	static Stream<String> solveForInstanceFromSystemIn() throws IOException {
		final Instance instance = readInstanceFromSystemIn();
		final Stream<Solution> solutionStream = generateSolutions(instance);
		return printSolutions(solutionStream);
	}

	static void validateInstance(Instance instance) {
		final Constraints constraints = new ConstraintsLoader().load();
		new InstanceValidator(instance, constraints).validate();
	}

	static void validateSolution(Solution solution, Instance instance) {
		new SolutionValidator(instance, solution).validate();
	}

	private static Stream<Solution> generateSolutions(Instance instance) {
		return new Solver(instance).generateBetterSolutions();
	}

	private static Instance readInstanceFromSystemIn() throws IOException {
		System.out.println("Pass an instance data:");
		return new InstanceParser(System.in).parse();
	}

	static void finalizeWhenAnySolutionFound(Stream<String> solutions) {
		solutions.sequential().findAny().ifPresent(solution -> {
			System.out.println("Solution found:");
			System.out.println(solution);
		});
	}

	static void getAllSolutions(Stream<String> solutions) {
		System.out.println("Looking for a solution...");
		solutions.forEach(solution -> {}); // stream is lazy, need to be finalized
	}

	private static Stream<String> printSolutions(Stream<Solution> solutions) {
		return solutions.map(ConstructorUtils::stringifySolution)
		                .peek(System.out::println);
	}

	private static String stringifySolution(Solution solution) {
		final List<Section> serialize = new SolutionSerializer(solution).serialize();
		return Section.stringifySections(serialize);
	}
}