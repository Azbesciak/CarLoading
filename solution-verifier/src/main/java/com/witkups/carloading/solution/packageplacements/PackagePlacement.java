package com.witkups.carloading.solution.packageplacements;

import com.witkups.carloading.instance.hosts.Host;
import com.witkups.carloading.instance.packages.Package;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

import java.awt.*;


@Value
@AllArgsConstructor
@Builder
public final class PackagePlacement {
    private final Package pack;
    @Wither
    private final boolean isPackageReversed;
    @Wither
    private final Placement placement;


    public Rectangle asViewFromAbove() {
        int width = getEdgeLength(isPackageReversed, pack.getHost());
        int length = getEdgeLength(!isPackageReversed, pack.getHost());
        int x = placement.getDistanceToLeftEdge();
        int y = placement.getDistanceToVehicleFront();
        return new Rectangle(x, y, width, length);
    }

    private int getEdgeLength(boolean shouldGetLength, Host packHost) {
        return shouldGetLength ? packHost.getLength() : packHost.getWidth();
    }
}
