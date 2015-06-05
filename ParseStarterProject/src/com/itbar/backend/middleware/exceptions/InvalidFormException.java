package com.itbar.backend.middleware.exceptions;

/**
 * Created by martin on 5/23/15.
 */
public class InvalidFormException extends Exception {
	public InvalidFormException() {
		super();
	}

	public InvalidFormException(String detailMessage) {
		super(detailMessage);
	}
}
