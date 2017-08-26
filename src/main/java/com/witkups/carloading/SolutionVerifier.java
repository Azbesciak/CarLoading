package com.witkups.carloading;

import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.instance.InstanceParser;
import com.witkups.carloading.processing.reader.FileReader;
import com.witkups.carloading.solution.Solution;
import com.witkups.carloading.solution.SolutionParser;
import com.witkups.carloading.validation.ValidationError;

import java.io.IOException;
import java.io.InputStream;

import static com.witkups.carloading.ConstructorUtils.validateInstance;
import static com.witkups.carloading.ConstructorUtils.validateSolution;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public final class SolutionVerifier {
	public static void main(String[] args) {
		exitIfFilesNotProvided(args);

		String instanceFilePath = args[0];
		String solutionFilePath = args[1];
		try {
			Instance instance = getInstance(instanceFilePath);
			validateInstance(instance);
			Solution solution = getSolution(solutionFilePath, instance);
			validateSolution(solution, instance);
			System.out.println("No errors found");
		} catch (ValidationError | IOException e) {
			System.err.println(e.getMessage());
		}
	}

	private static Solution getSolution(String solutionFilePath, Instance instance) throws IOException {
		Solution solution;
		try (InputStream solutionInputStream = new FileReader(solutionFilePath).read()) {
			solution = new SolutionParser(solutionInputStream, instance).parse();
		}
		return solution;
	}

	private static Instance getInstance(String instanceFilePath) throws IOException {
		Instance instance;
		try (InputStream instanceInputStream = new FileReader(instanceFilePath).read()) {
			instance = new InstanceParser(instanceInputStream).parse();
		}
		return instance;
	}

	private static void exitIfFilesNotProvided(String[] args) {
		if (isInputDataNotValid(args)) {
			System.err.println("Please pass instance and solution parameter "
					                   + "as '-Pinstance=\"<inputPath>\" -Psolution=\"<outputPath>\"");
			System.exit(-1);
		}
	}

	private static boolean isInputDataNotValid(String[] args) {
		return !(args.length == 2 && isNotBlank(args[0]) && isNotBlank(args[1]));
	}
}