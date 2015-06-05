package com.itbar.backend.util;

/**
 * Interfase usada para realizar validaciones internas dentro de un Form, como por ejemplo comparar
 * contraseñas.
 *
 * @see Form
 * Created by martin on 5/22/15.
 */
public interface InterFieldValidation {
	public Boolean validateForm();
}
