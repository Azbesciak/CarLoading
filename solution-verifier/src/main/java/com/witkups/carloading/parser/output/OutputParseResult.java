package com.witkups.carloading.parser.output;

import com.witkups.carloading.entity.PackagePlacement;
import com.witkups.carloading.entity.Purpose;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public final class OutputParseResult {
    private final Purpose purpose;
    private final List<PackagePlacement> packagePlacements;
}
