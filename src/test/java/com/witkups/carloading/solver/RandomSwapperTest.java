package com.witkups.carloading.solver;

import com.witkups.carloading.PlacementTestProvider;
import com.witkups.carloading.solution.packageplacements.PackagePlacement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RandomSwapperTest extends OptimizerTestBase {
    @Test
    public void manyItemsInTheSameSequenceShouldSwap() {
        int size = 10;
        final List<PackagePlacement> initialPlacement = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            initialPlacement.add(PlacementTestProvider.prepareMediumPackagePlacement(5, 5, 5, 0, 5, 5, true, true));
        }
        List<PackagePlacement> copy = new ArrayList<>(initialPlacement);
        final boolean firstSwapResult = RandomSwapper.swap(copy);
        assertTrue(firstSwapResult);
        assertNotEquals(copy, initialPlacement);
        final ArrayList<PackagePlacement> anotherCopy = new ArrayList<>(copy);
        final boolean secondSwapResult = RandomSwapper.swap(anotherCopy);
        assertTrue(secondSwapResult);
        assertNotEquals(copy, anotherCopy);
    }

    @Test
    public void manyItemsButSingletonsInSequences() {
        int size = 10;
        final List<PackagePlacement> initialPlacement = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            initialPlacement.add(PlacementTestProvider.prepareMediumPackagePlacement(5, 5, 5, i, 5, 5, true, true));
        }
        List<PackagePlacement> copy = new ArrayList<>(initialPlacement);
        final boolean firstSwapResult = RandomSwapper.swap(copy);
        assertFalse(firstSwapResult);
        assertEquals(copy, initialPlacement);
        final ArrayList<PackagePlacement> anotherCopy = new ArrayList<>(copy);
        final boolean secondSwapResult = RandomSwapper.swap(anotherCopy);
        assertFalse(secondSwapResult);
        assertEquals(copy, anotherCopy);
    }

    @Test
    public void mixedTest() {
        int size = 10;
        final List<PackagePlacement> initialPlacement = new ArrayList<>(size);
        for (int i = 0; i < size / 2; i++) {
            initialPlacement.add(PlacementTestProvider.prepareMediumPackagePlacement(5, 5, 5, 0, 5, 5, true, true));
        }
        for (int i = size / 2; i < size; i++) {
            initialPlacement.add(PlacementTestProvider.prepareMediumPackagePlacement(5, 5, 5, i, 5, 5, true, true));
        }
        List<PackagePlacement> copy = new ArrayList<>(initialPlacement);
        final boolean firstSwapResult = RandomSwapper.swap(copy);
        assertTrue(firstSwapResult);
        assertNotEquals(copy, initialPlacement);
        compareWhichShouldBeEqual(size, initialPlacement, copy);
        final ArrayList<PackagePlacement> anotherCopy = new ArrayList<>(copy);
        final boolean secondSwapResult = RandomSwapper.swap(anotherCopy);
        assertTrue(secondSwapResult);
        assertNotEquals(copy, anotherCopy);
        compareWhichShouldBeEqual(size, anotherCopy, copy);
    }

    private void compareWhichShouldBeEqual(int size, List<PackagePlacement> initialPlacement,
                                           List<PackagePlacement> copy) {
        for (int i = size / 2; i < size; i++) {
            assertEquals(copy.get(i), initialPlacement.get(i));
        }
    }

}