package com.witkups.carloading.solver;

import com.witkups.carloading.entity.Solution;

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
//    private static int ink = 0;
    private Solution theBestSolution;

    private final List<Solution> solutions;
    SolutionsRepo() {
        solutions = new ArrayList<>();
    }

    void add(Solution solution) {
        updateSolutions(solution);
//        System.err.println("adding solution " + solution.getPurpose() + " is better " + solution.isBetterThan(theBestSolution) + " i " + ink++);
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
