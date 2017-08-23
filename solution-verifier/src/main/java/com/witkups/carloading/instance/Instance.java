package com.witkups.carloading.instance;

import com.witkups.carloading.instance.hosts.Host;
import com.witkups.carloading.instance.packages.Package;
import com.witkups.carloading.instance.vehicle.Vehicle;
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
