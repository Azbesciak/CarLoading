package com.witkups.carloading.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class Package {
    private String id;
    private int sequentialNumber;
    private Host host;
    private int height;
    private boolean canBePlacedOnPackage;
    private boolean canOtherPackageBePlaceOn;
}
