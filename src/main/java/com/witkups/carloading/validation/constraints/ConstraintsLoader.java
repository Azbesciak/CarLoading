package com.witkups.carloading.validation.constraints;

import com.witkups.carloading.processing.PropertiesLoader;

import java.io.InputStream;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public final class ConstraintsLoader extends PropertiesLoader<Constraints> {

	public ConstraintsLoader() {
		super("constraints.properties");
	}

	@Override
	protected Constraints build() {
		return Constraints.builder()
		                  .maxVehicleWidth(getIntProp("vehicle.width", "40"))
		                  .maxVehicleHeight(getIntProp("vehicle.height", "40"))
		                  .maxSequences(getIntProp("sequences", "20"))
		                  .maxPackages(getIntProp("packages", "50"))
		                  .maxHosts(getIntProp("hosts", "10"))
		                  .maxHostLength(getIntProp("host.length", "20"))
		                  .maxHostWidth(getIntProp("host.width", "20"))
		                  .maxPackageHeight(getIntProp("package.height", "40"))
		                  .build();
	}
}