package com.itbar.backend.services;

import com.parse.Parse;
import com.parse.ParseException;

/**
 * Forma que tiene la app de representar una Exception arrojada por Parse cuando realiza sus
 * operaciones. Esta clase tiene forma de cortina para el front pues atras del Middleware no deben
 * conocer quien es el proveedor de contenidos. Escondo al ParseException atras de esta clase
 *
 * @deprecated Adopto la forma de error ver Remote Error
 * @see RemoteError
 * Created by martin on 5/22/15.
 */
public class RemoteException extends ParseException {

	public static final Integer ALREADY_LOGGED_IN = 1;
	public static final Integer INVALID_FORM = 2;

	private int code;

	public RemoteException(int theCode, String theMessage) {
		super(theCode, theMessage);
		this.code = theCode;
	}

	public RemoteException(int theCode, String message, Throwable cause) {
		super(theCode, message, cause);
		this.code = theCode;
	}

	public RemoteException(Throwable cause) {
		super(cause);
		this.code = -1;
	}

	public RemoteException(ParseException e) {
		super(e.getCode(), e.getMessage());
		this.code = e.getCode();
	}
}
