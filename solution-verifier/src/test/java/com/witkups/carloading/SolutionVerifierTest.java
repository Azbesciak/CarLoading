package com.witkups.carloading;

import org.junit.Test;


public class SolutionVerifierTest {
    @Test
    public void main() throws Exception {
        final String FILES_ROOT_PATH = "src/test/resources";
        final String[] args = { FILES_ROOT_PATH + "/input-file.txt", FILES_ROOT_PATH + "/output-file.txt" };
        SolutionVerifier.main(args);
    }

}