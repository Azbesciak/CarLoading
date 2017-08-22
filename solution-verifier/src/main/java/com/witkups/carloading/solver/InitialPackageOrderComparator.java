package com.witkups.carloading.solver;

import com.witkups.carloading.entity.Package;

import java.util.Comparator;

class InitialPackageOrderComparator implements Comparator<Package> {

    @Override
    public int compare(Package p1, Package p2) {
        if (p1.getSequenceId() == p2.getSequenceId()) {
            final int p1Area = getPackArea(p1);
            final int p2Area = getPackArea(p2);
            if (p1Area == p2Area) {
                return compareByPlacementPossibility(p1, p2);
            } else {
                return Integer.compare(p1Area, p2Area);
            }
        } else {
            return Integer.compare(p1.getSequenceId(), p2.getSequenceId());
        }
    }

    private int compareByPlacementPossibility(Package p1, Package p2) {
        if (!p1.canBePlacedOnPackage()) {
            return -1;
        } else if (!p2.canBePlacedOnPackage()) {
            return 1;
        } else if (p1.canOtherPackageBePlacedOn()){
            return -1;
        } else if (p2.canOtherPackageBePlacedOn()) {
            return 1;
        } else {
            return 0;
        }
    }

    private int getPackArea(Package pack) {
        return pack.getHost().getLength() * pack.getHost().getWidth();
    }
}
