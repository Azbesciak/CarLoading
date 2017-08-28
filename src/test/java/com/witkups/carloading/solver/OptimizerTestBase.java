package com.witkups.carloading.solver;

import org.junit.Before;

import java.lang.reflect.Field;
// Needed to reset singleton used in solver
public abstract class OptimizerTestBase {

    @Before
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = RandomSwapper.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }
}
