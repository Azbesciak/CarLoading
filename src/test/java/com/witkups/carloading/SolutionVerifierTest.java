package com.witkups.carloading;

import com.witkups.carloading.solver.OptimizerTestBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.Permission;

import static com.witkups.carloading.TestFilesData.SAMPLE_MAIN_ARGS;

public class SolutionVerifierTest extends OptimizerTestBase{

	private SecurityManager defaultSecurityManager;

	@Before
	public void setUp() throws Exception {
		defaultSecurityManager = System.getSecurityManager();
		System.setSecurityManager(new SecurityManager() {
			@Override
			public void checkPermission(Permission perm) {}

			@Override
			public void checkPermission(Permission perm, Object context) {}

			@Override
			public void checkExit(int status) {
				super.checkExit(status);
				throw new SecurityException("Overriding shutdown...");
			}
		});
	}

	@After
	public void tearDown() throws Exception {
		System.setSecurityManager(defaultSecurityManager);
	}

	@Test
	public void mainTest() throws Exception {
		SolutionVerifier.main(SAMPLE_MAIN_ARGS);
	}

	@Test(expected = SecurityException.class)
	public void noArgsToMain() throws Exception {
		SolutionVerifier.main(new String[] {});
	}

}