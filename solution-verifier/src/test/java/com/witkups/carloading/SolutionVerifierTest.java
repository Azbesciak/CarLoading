package com.witkups.carloading;

import org.junit.Test;


public class SolutionVerifierTest {
    @Test
    public void main() throws Exception {
        final String FILES_ROOT_PATH = "src/test/resources";
        final String[] args = { FILES_ROOT_PATH + "/instance-file.txt", FILES_ROOT_PATH + "/solution-file.txt" };
        SolutionVerifier.main(args);
    }

}