package com.witkups.carloading.instance.vehicle;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Vehicle {
    private final int width;
    private final int height;
}
