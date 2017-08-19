package com.witkups.carloading;

import com.witkups.carloading.entity.Constraints;
import com.witkups.carloading.parser.input.InputFileParser;
import com.witkups.carloading.parser.input.InputParseResult;
import com.witkups.carloading.parser.output.OutputFileParser;
import com.witkups.carloading.parser.output.OutputParseResult;

import java.io.IOException;

public class SolutionVerifier {
    public static void main(String[] args) throws IOException {
        if (args.length < 2)
            exitWithInvalidArgument();

        String inputFile = args[0];
        String outputFile = args[1];
        final Constraints constraints = new ConstraintsLoader().loadProperties();
        System.out.println(constraints);
        final InputParseResult inputParseResult = new InputFileParser(inputFile).parse();
        System.out.println(inputParseResult);
        final OutputParseResult outputParseResult = new OutputFileParser(outputFile, inputParseResult).parse();
        System.out.println(outputParseResult);
    }

    private static void exitWithInvalidArgument() {
        throw new IllegalArgumentException("Please pass input and output parameter "
                + "as '-Pinput=\"<inputPath>\" -Poutput=\"<outputPath>\"");
    }


}
