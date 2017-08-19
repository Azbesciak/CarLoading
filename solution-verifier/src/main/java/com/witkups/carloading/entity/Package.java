package com.witkups.carloading.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class Package implements Comparable<Package>{
    private String id;
    private int sequenceId;
    private Host host;
    private int height;
    private boolean canBePlacedOnPackage;
    private boolean canOtherPackageBePlacedOn;

    @Override
    public int compareTo(Package o) {
        return Integer.compare(sequenceId, o.sequenceId);
    }
}
