package com.witkups.carloading.processing;

import com.witkups.carloading.validation.ValidationError;

public class ParseValidationError extends ValidationError {
	public ParseValidationError(String message) {
		super(message);
	}
}
