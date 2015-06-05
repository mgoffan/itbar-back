package com.itbar.backend.util;

import com.itbar.backend.util.fields.Validator;

/**
 * Representa un campo en un formulario.
 *
 * La idea es que tenga un valor primitivo String y que mediante retrieveResult sus subclases
 * devuelvan el valor que le corresponda a la interpretacion de ese valor String.
 *
 * Ademas cada campo puede ser requerido o obligatorio y tambien sabe determinar su validez y tener
 * un mensaje de error en caso de que su contenido no sea valido
 *
 * Created by martin on 20/05/15.
 *
 */
public abstract class Field<T> {

	private String value;
	private Boolean required = false;

	public Field() {
	}

	public Field(Boolean required) {
		this.required = required;
	}

	public Field(Boolean required, String elem) {
		this.required = required;
		this.value = elem;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean isRequired() {
		return required;
	}

	public abstract Boolean isValid();

	public abstract String getErrorDescription();

	public abstract T retrieveResult();

}
