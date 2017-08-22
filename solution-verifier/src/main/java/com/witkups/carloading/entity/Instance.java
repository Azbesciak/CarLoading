package com.witkups.carloading.entity;

import com.witkups.carloading.entity.Host;
import com.witkups.carloading.entity.Package;
import com.witkups.carloading.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public final class Instance {
    private final Vehicle vehicle;
    private final List<Host> hosts;
    private final List<Package> packages;
}
