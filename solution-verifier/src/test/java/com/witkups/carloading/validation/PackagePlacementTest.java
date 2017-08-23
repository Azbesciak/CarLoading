package com.witkups.carloading.validation;

import com.witkups.carloading.instance.hosts.Host;
import com.witkups.carloading.instance.packages.Package;
import com.witkups.carloading.solution.packageplacements.PackagePlacement;
import com.witkups.carloading.solution.packageplacements.Placement;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class PackagePlacementTest {

    @Test
    public void getAsViewTest() {
        final PackagePlacement placement = PackagePlacement.builder()
                .pack(Package.builder().host(Host.builder().width(10).length(15).build()).build())
                .isPackageReversed(false).placement(new Placement(5,5)).build();
        final Rectangle notReversed = placement.asViewFromAbove();
        assertEquals(5, notReversed.x);
        assertEquals(5, notReversed.y);
        assertEquals(15, notReversed.getMaxX(), 0.01);
        assertEquals(20, notReversed.getMaxY(), 0.01);

        final Rectangle reversed = placement.withPackageReversed(true).asViewFromAbove();
        assertEquals(5, reversed.x);
        assertEquals(5, reversed.y);
        assertEquals(15, reversed.getMaxY(), 0.01);
        assertEquals(20, reversed.getMaxX(), 0.01);
    }

}
