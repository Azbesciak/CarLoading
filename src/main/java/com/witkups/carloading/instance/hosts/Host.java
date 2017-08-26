package com.witkups.carloading.instance.hosts;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(of = "id")
public final class Host {
	private String id;
	private int length;
	private int width;
}