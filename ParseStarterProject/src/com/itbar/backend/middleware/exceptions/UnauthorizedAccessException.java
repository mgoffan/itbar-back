package com.itbar.backend.middleware.exceptions;

/**
 * Created by martin on 5/23/15.
 */
public class UnauthorizedAccessException extends Exception {

	public UnauthorizedAccessException() {
		super();
	}

	public UnauthorizedAccessException(String detailMessage) {
		super(detailMessage);
	}
}
