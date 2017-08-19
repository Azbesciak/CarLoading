package com.witkups.carloading.parser.input;

import com.witkups.carloading.entity.Host;
import com.witkups.carloading.entity.Package;
import com.witkups.carloading.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public final class InputParseResult {
    private final Vehicle vehicle;
    private final List<Host> hosts;
    private final List<Package> packages;
}
