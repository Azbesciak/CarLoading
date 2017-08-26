package com.witkups.carloading.processing;

import com.witkups.carloading.validation.ValidationError;

public final class ParseValidationError extends ValidationError {
	public ParseValidationError(String message) {
		super(message);
	}
}