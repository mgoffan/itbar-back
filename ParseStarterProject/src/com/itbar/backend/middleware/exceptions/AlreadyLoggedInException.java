package com.itbar.backend.middleware.exceptions;

/**
 *
 * Created by martin on 5/23/15.
 * @deprecated
 */
public class AlreadyLoggedInException extends Exception {
	public AlreadyLoggedInException() {
		super();
	}

	public AlreadyLoggedInException(String detailMessage) {
		super(detailMessage);
	}
}
