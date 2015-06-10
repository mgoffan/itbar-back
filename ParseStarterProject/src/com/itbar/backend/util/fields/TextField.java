package com.itbar.backend.util.fields;

import com.itbar.backend.util.Field;

/**
 * Representa a un campo cuyo contenido va a ser puramente texto, incluye un Validator para evitar
 * redundancias a la hora de crear subclases.
 *
 * @see Validator
 * Created by martin on 5/26/15.
 */
public class TextField extends Field {

	private Validator customValidator = null;

	public TextField () {
		super();
	}

	public TextField(Boolean required) {
		super(required);
	}

	public TextField(Boolean required, String elem) {
		super(required, elem);
	}

	public TextField(Boolean required, Validator validator) {
		super(required);
		this.customValidator = validator;
	}

	/**
	 * Devuelve la validacion del Validator en caso de que tenga uno y sino true pues no tiene sentido
	 * validar texto que puede ser arbitrario
	 *
	 * @return Boolean la validez del campo
	 */
	@Override
	public Boolean isValid() {
		return this.customValidator != null ? this.customValidator.isValid(this.getValue()) : true;
	}

	@Override
	public String getErrorDescription() {
		if (this.customValidator != null && !this.isValid()) {
			return this.customValidator.errorDescription();
		}
		return "";
	}

	@Override
	public String retrieveResult() {
		return this.getValue();
	}
}
