package com.witkups.carloading.solution.purpose;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public final class Purpose implements Comparable<Purpose>{
    private final int maxDistance;
    private final int occupiedPlace;

    @Override
    public int compareTo(Purpose o) {
        if (this.maxDistance == o.maxDistance) {
            return Integer.compare(this.occupiedPlace, o.occupiedPlace);
        } else {
            return Integer.compare(this.maxDistance, o.maxDistance);
        }
    }
}
