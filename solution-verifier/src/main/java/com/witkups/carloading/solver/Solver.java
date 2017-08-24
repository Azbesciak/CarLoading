package com.witkups.carloading.solver;

import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.solution.Solution;

import java.util.stream.Stream;

public class Solver {
	private final InitialSolutionMaker initialSolutionMaker;
	private final ContinuousSolutionMaker continuousSolutionMaker;

	public Solver(Instance instance) {
		initialSolutionMaker = new InitialSolutionMaker(instance);
		continuousSolutionMaker = new ContinuousSolutionMaker(instance);
	}

	public Stream<Solution> generateBetterSolutions() {
		final ResultFilter resultFilter = new ResultFilter();
		return generateSolutions().filter(resultFilter::validate);
	}

	public Stream<Solution> generateSolutions() {
		final Solution initialSolution = initialSolutionMaker.findSolution();
		return Stream.concat(Stream.of(initialSolution), Stream.generate(continuousSolutionMaker::findSolution))
		             .parallel();
	}

	private class ResultFilter {
		private Solution solution;

		synchronized boolean validate(Solution solution) {
			if (solution.isBetterThan(this.solution)) {
				this.solution = solution;
				return true;
			}
			return false;
		}
	}
}
