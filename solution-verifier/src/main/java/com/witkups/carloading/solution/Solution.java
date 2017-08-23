package com.witkups.carloading.solution;

import com.witkups.carloading.solution.packageplacements.PackagePlacement;
import com.witkups.carloading.solution.purpose.Purpose;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.Wither;

import java.awt.*;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
@AllArgsConstructor
public final class Solution implements Comparable<Solution>{
    @Wither
    private final Purpose purpose;
    private final List<PackagePlacement> packagePlacements;

    public Solution(List<PackagePlacement> packagePlacements) {
        this.packagePlacements = packagePlacements;
        this.purpose = preparePurpose();
    }

    private Purpose preparePurpose() {
        int occupiedPlace = getOccupiedPlace();
        int maxDistance = countMaxDistance();
        return new Purpose(maxDistance, occupiedPlace);
    }

    private int countMaxDistance() {
        return (int) getPlacementsAsViewStream()
                .reduce(Rectangle::union)
                .orElseGet(Rectangle::new)
                .getMaxY();
    }

    private Stream<Rectangle> getPlacementsAsViewStream() {
        return packagePlacements.stream()
                .map(PackagePlacement::asViewFromAbove);
    }

    private int getOccupiedPlace() {
        final List<Rectangle> views = getDistinctPlacements();
        removePlacementsNotOnTheGround(views);
        return countOccupiedPlace(views);
    }

    private int countOccupiedPlace(List<Rectangle> views) {
        return views.parallelStream()
                .map(view -> view.width * view.height)
                .reduce((a, b) -> a + b)
                .orElse(0);
    }

    private List<Rectangle> getDistinctPlacements() {
        return getPlacementsAsViewStream()
                .collect(Collectors.toList());
    }

    private void removePlacementsNotOnTheGround(List<Rectangle> views) {
        int currentIndex = 0;
        while (currentIndex < views.size()) {
            removeAllNextContained(views, currentIndex);
            currentIndex++;
        }
    }

    private void removeAllNextContained(List<Rectangle> views, int currentIndex) {
        final Rectangle currentPlacement = views.get(currentIndex);
        final ListIterator<Rectangle> viewsIterator = views.listIterator(currentIndex + 1);
        while (viewsIterator.hasNext()) {
            final Rectangle nextView = viewsIterator.next();
            if (currentPlacement.contains(nextView))
                viewsIterator.remove();
        }
    }

    @Override
    public int compareTo(Solution o) {
        return this.purpose.compareTo(o.purpose);
    }

    public boolean isBetterThan(Solution other) {
        return other == null || compareTo(other) < 0;
    }
}
