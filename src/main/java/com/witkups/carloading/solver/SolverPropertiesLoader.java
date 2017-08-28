package com.witkups.carloading.solver;

import com.witkups.carloading.processing.PropertiesLoader;

final class SolverPropertiesLoader extends PropertiesLoader<SolverProperties> {
    SolverPropertiesLoader() {
        super("solver.properties");
    }

    @Override
    protected SolverProperties build() {
        return SolverProperties.builder()
                .solutionsRetainRatio(getIntProp("solutions.retain.ratio", "10"))
                .solutionPersistenceLimit(getIntProp("solution.persistence.limit", "100"))
                .build();
    }
}