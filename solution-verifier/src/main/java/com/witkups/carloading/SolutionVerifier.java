package com.witkups.carloading;

import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.instance.InstanceParser;
import com.witkups.carloading.processing.reader.FileReader;
import com.witkups.carloading.solution.Solution;
import com.witkups.carloading.solution.SolutionParser;
import com.witkups.carloading.validation.constraints.Constraints;
import com.witkups.carloading.validation.constraints.ConstraintsLoader;

import java.io.IOException;
import java.io.InputStream;

import static com.witkups.carloading.validation.InputDataValidator.validate;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class SolutionVerifier {
	public static void main(String[] args) throws IOException {
		exitIfFilesNotProvided(args);

		String instanceFilePath = args[0];
		String solutionFilePath = args[1];
		final Constraints constraints = new ConstraintsLoader().loadProperties();
		System.out.println(constraints);
		Instance instance = getInstance(instanceFilePath);
		Solution solution = getSolution(solutionFilePath, instance);
		validate(instance, solution, constraints);
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
		if (args.length == 2) {
			if (isNotBlank(args[0]) && isNotBlank(args[1]))
				return;
		}
		throw new IllegalArgumentException("Please pass instance and solution parameter "
				                                   + "as '-Pinput=\"<inputPath>\" -Poutput=\"<outputPath>\"");
	}
}
