package com.witkups.carloading.validation.constraints;

import java.io.InputStream;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public final class ConstraintsLoader {
	private static final String constraintsFile = "constraints.properties";
	private Properties prop;

	public ConstraintsLoader() {
		this.prop = new Properties();
	}

	public Constraints loadProperties() {

		try (InputStream input = Constraints.class.getClassLoader()
		                                          .getResourceAsStream(constraintsFile)) {
			prop.load(input);
		} catch (Exception e) {
			System.err.println("No property file was found, used default");
		}

		return buildConstraints();
	}

	private Constraints buildConstraints() {
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

	private int getIntProp(String property, String defaultValue) {
		return parseInt(prop.getProperty(property, defaultValue));
	}
}
