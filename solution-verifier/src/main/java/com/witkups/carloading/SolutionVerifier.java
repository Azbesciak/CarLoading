package com.witkups.carloading;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.witkups.carloading.parser.input.Instance;
import com.witkups.carloading.parser.input.InstanceParser;
import com.witkups.carloading.parser.output.Solution;
import com.witkups.carloading.parser.output.SolutionParser;
import com.witkups.carloading.parser.reader.FileReader;
import com.witkups.carloading.validation.Constraints;
import com.witkups.carloading.validation.ConstraintsLoader;

import java.io.IOException;
import java.io.InputStream;

import static com.witkups.carloading.InputDataValidator.validate;

public class SolutionVerifier {
    public static void main(String[] args) throws IOException {
        if (args.length < 2)
            exitWithInvalidArgument();

        String instanceFilePath = args[0];
        String solutionFilePath = args[1];
        final Constraints constraints = new ConstraintsLoader().loadProperties();
        System.out.println(constraints);
        Instance instance = getInstance(instanceFilePath);
//        printAsJson(instance);
        Solution solution = getSolution(solutionFilePath, instance);
        printAsJson(solution);
        validate(instance, solution, constraints);

    }

    private static void printAsJson(Object object) {
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(object));
    }

    private static Solution getSolution(String solutionFilePath, Instance instance) throws IOException {
        Solution solution;
        try (InputStream solutionInputStream = FileReader.read(solutionFilePath)) {
            solution = new SolutionParser(solutionInputStream, instance).parse();
        }
        return solution;
    }

    private static Instance getInstance(String instanceFilePath) throws IOException {
        Instance instance;
        try (InputStream instanceInputStream = FileReader.read(instanceFilePath)) {
            instance = new InstanceParser(instanceInputStream).parse();
        }
        return instance;
    }

    private static void exitWithInvalidArgument() {
        throw new IllegalArgumentException("Please pass input and output parameter "
                + "as '-Pinput=\"<inputPath>\" -Poutput=\"<outputPath>\"");
    }


}
