package com.witkups.carloading.solver;

import com.witkups.carloading.solution.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class SolutionsRepo {
    private final static int MAX_SOLUTIONS = 100;
    private final static int SOLUTIONS_TO_RETAIN = 10;
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
    }

    void add(Solution solution) {
//        System.out.println("found solution " + solution.getPurpose() + " " + solution.isBetterThan(theBestSolution));
        updateSolutions(solution);
        updateBestSolutionIfBetter(solution);
    }

    private void updateSolutions(Solution solution) {
        solutionsLock.writeLock().lock();
        if (solutions.size() >= MAX_SOLUTIONS) {
            leaveOnlyTheBestSolutions();
        }
        solutions.add(solution);
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
