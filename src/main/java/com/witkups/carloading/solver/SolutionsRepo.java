package com.witkups.carloading.solver;

import com.witkups.carloading.solution.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

final class SolutionsRepo {
    private final int MAX_SOLUTIONS;
    private final int SOLUTIONS_TO_RETAIN;
    private final static ReentrantReadWriteLock solutionsLock = new ReentrantReadWriteLock();
    private final static ReentrantReadWriteLock theBestLock = new ReentrantReadWriteLock();
    private final static Random randomGen = new Random();
    private static SolutionsRepo instance;
    private final List<Solution> solutions;
    private Solution theBestSolution;

    static SolutionsRepo getRepoInstance() {
        if (instance == null) {
            synchronized (SolutionsRepo.class) {
                if (instance == null) {
                    instance = new SolutionsRepo();
                }
            }
        }
        return instance;
    }

    private SolutionsRepo() {
        solutions = new ArrayList<>();
        final SolverProperties properties = new SolverPropertiesLoader().load();
        MAX_SOLUTIONS = properties.getSolutionPersistenceLimit();
        SOLUTIONS_TO_RETAIN = properties.getSolutionsToRetain();
    }

    void add(Solution solution) {
        updateSolutions(solution);
        updateBestSolutionIfBetter(solution);
    }

    private void updateSolutions(Solution solution) {
        solutionsLock.writeLock().lock();

        if (solutions.size() >= MAX_SOLUTIONS) {
            leaveOnlyTheBestSolutions();
        }
        if (!solutions.contains(solution)) {
            solutions.add(solution);
        }
        solutionsLock.writeLock().unlock();
    }

    private void leaveOnlyTheBestSolutions() {
        Collections.sort(solutions);
        solutions.removeAll(solutions.subList(SOLUTIONS_TO_RETAIN, solutions.size()));
    }

    private void updateBestSolutionIfBetter(Solution solution) {
        theBestLock.writeLock().lock();
        if (solution.isBetterThan(theBestSolution)) {
            theBestSolution = solution;
        }
        theBestLock.writeLock().unlock();
    }

    Solution getRandom() {
        final boolean isLocked = solutionsLock.readLock().tryLock();
        if (isLocked) {
            final int randomIndex = randomGen.nextInt(solutions.size());
            final Solution solution = solutions.get(randomIndex);
            solutionsLock.readLock().unlock();
            return solution;
        }
        return getTheBest();
    }

    Solution getTheBest() {
        return theBestSolution;
    }
}