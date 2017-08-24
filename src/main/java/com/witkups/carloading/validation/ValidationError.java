package com.witkups.carloading.validation;

public class ValidationError extends RuntimeException {
	public ValidationError(String message) {
		super(message);
	}
}
