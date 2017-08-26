package com.witkups.carloading.instance.packages;

import com.witkups.carloading.instance.hosts.Host;
import lombok.*;

@Value
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id", "sequenceId"})
public class Package implements Comparable<Package> {
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