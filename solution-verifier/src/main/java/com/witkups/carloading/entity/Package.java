package com.witkups.carloading.entity;

import lombok.*;

@Value
@AllArgsConstructor
@Builder
public class Package implements Comparable<Package>{
    private String id;
    private int sequenceId;
    private Host host;
    private int height;
    @Getter(AccessLevel.NONE)
    private boolean canBePlacedOnPackage;
    @Getter(AccessLevel.NONE)
    private boolean canOtherPackageBePlacedOn;

    public boolean canBePlacedOnPackage() {
        return canBePlacedOnPackage;
    }

    public boolean canOtherPackageBePlacedOn() {
        return canOtherPackageBePlacedOn;
    }


    @Override
    public int compareTo(Package o) {
        return Integer.compare(sequenceId, o.sequenceId);
    }
}
