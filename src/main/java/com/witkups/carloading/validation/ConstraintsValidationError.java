package com.witkups.carloading.validation;

final class ConstraintsValidationError extends ValidationError {

	ConstraintsValidationError(String message) {
		super(message);
	}

	ConstraintsValidationError(String error, int constraintValue) {
		super(formatMessage(error, constraintValue));
	}

	private static String formatMessage(String error, int constraint) {
		return String.format("%s; constraint - %d", error, constraint);
	}
}
