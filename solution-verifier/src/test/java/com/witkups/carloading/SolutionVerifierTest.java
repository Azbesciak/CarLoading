package com.witkups.carloading;

import org.junit.Test;

import static com.witkups.carloading.TestFilesData.SAMPLE_MAIN_ARGS;

public class SolutionVerifierTest {

	@Test
	public void mainTest() throws Exception {
		SolutionVerifier.main(SAMPLE_MAIN_ARGS);
	}

}