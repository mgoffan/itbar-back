package com.itbar.backend.services;

import com.parse.Parse;
import com.parse.ParseException;

/**
 *
 * Es una adaptacion de las excepciones que tira Parse porque ParseException es lo que fue arrojado
 * por Parse, en la app esto va a tener formato de error.
 *
 * Forma que tiene la app de representar una Exception arrojada por Parse cuando realiza sus
 * operaciones. Esta clase tiene forma de cortina para el front pues atras del Middleware no deben
 * conocer quien es el proveedor de contenidos. Escondo al ParseException atras de esta clase
 *
 * @since 1.0
 *
 * Created by martin on 5/22/15.
 */
public class RemoteError extends ParseException {

	public static final Integer ALREADY_LOGGED_IN = 301;
	public static final Integer INVALID_FORM = 302;
	public static final Integer NO_SUCH_USER = 303;
	public static final Integer NO_SUCH_ORDER = 303;
	public static final Integer NOT_LOGGED_IN = 304;

	private int code;

	public RemoteError(int theCode, String theMessage) {
		super(theCode, theMessage);
		this.code = theCode;
	}

	public RemoteError(int theCode, String message, Throwable cause) {
		super(theCode, message, cause);
		this.code = theCode;
	}

	public RemoteError(Throwable cause) {
		super(cause);
		this.code = -1;
	}

	public RemoteError(ParseException e) {
		super(e.getCode(), e.getMessage());
		this.code = e.getCode();
	}
}
