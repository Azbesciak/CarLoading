package com.witkups.carloading.solver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@AllArgsConstructor
@Builder
@Value
final class SolverProperties {
    private final int solutionPersistenceLimit;
    private final int solutionsRetainRatio;

    int getSolutionsToRetain() {
        return (int)(solutionPersistenceLimit * (solutionsRetainRatio / 100f));
    }
}