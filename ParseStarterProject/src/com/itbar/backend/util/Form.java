package com.itbar.backend.util;

import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Form es la clase designada para intercambiar informacion entre el Front(UI) y los Services. Esta
 * clase asegura que la informacion que va a ser enviada al servidor (Parse) este validada.
 *
 * @see com.itbar.backend.services.UserService
 * @see com.itbar.backend.services.BarService
 * @see com.itbar.backend.services.OrderService
 *
 * Created by martin on 21/05/15.
 */
public class Form {

	/**
	 * Contiene un mappeo entre un id y el campo correspondiente
	 */
	private HashMap<String, Field> fields = new HashMap<String, Field>();

	/**
	 * Guarda si el formulario ya fue validado para evitar, en caso que tenga, validaciones costosas
	 */
	private Boolean validated = false;

	/**
	 * Guarda en Strings legibles los errores que puede tener el formulario
	 */
	private Set<String> errors = new HashSet<String>();

	/**
	 * Guarda la validacion entre campos del formulario, ej: repetir contraseña
	 * @see FormBuilder
	 */
	private InterFieldValidation interFieldValidation = null;

	public Form() {
	}

	public Form(InterFieldValidation validation) {
		this.interFieldValidation = validation;
	}

	public Form(String key, Field field) {
		this.fields.put(key, field);
	}

	/**
	 * Resetea el estado del formulario
	 */
	private void resetState() {
		validated = false;
		if (!errors.isEmpty())
			errors.clear();
	}

	/**
	 * Agrega un campo al formulario y lo resetea por si ya habia sido validado lo existente
	 *
	 * Es protected porque no cualquiera deberia poder agregar un campo
	 *
	 * @param key la clave del campo a agregar
	 * @param field el campo a agregar
	 */
	protected void addField(String key, Field field) {
		resetState();
		this.fields.put(key, field);
	}

	/**
	 * Saca un campo del formulario
	 *
	 * Es protected porque no cualquiera deberia poder sacar un campo del formulario
	 *
	 * @param key la clave del campo
	 */
	protected void removeField(String key) {
		resetState();
		this.fields.remove(key);
	}

	/**
	 * Setter del interFieldValidation
	 * @param interFieldValidation la validacion entre campos del formulario
	 */
	public void setInterFieldValidation(InterFieldValidation interFieldValidation) {
		this.interFieldValidation = interFieldValidation;
	}

	/**
	 * Fija un valor val en campo con clave key
	 *
	 * Se reincia el estado porque se debe validar un vez que se cambio el valor de un campo
	 *
	 * @param key la clave asociada al campo
	 * @param val el valor que se le desea asignar
	 */
	public void set(String key, String val) {
		this.fields.get(key).setValue(val);
		resetState();
	}

	/**
	 * Obtiene el valor asociado a la clave key
	 *
	 * @param key la clave del campo
	 * @return String el valor que contiene el campo asociado a la clave key, devuelve null si no
	 * existe el campo en este formulario
	 */
	public String get(String key) {
		if (this.fields.get(key) != null)
			return this.fields.get(key).getValue();
		return null;
	}


	/**
	 *
	 * @param key el id del campo
	 * @return Field el campo
	 */
	public Field getField(String key) {
		return this.fields.get(key);
	}


	/**
	 * Verifica la validez del formulario
	 *
	 * El procedimiento es simple, fijarse si los campos requeridos estan presentes luego validar
	 * todos los campos y si el formulario sigue siendo valido fijarse si entre los campos se valida
	 * la condicion; e ir acumulando errores en el proceso.
	 *
	 * @return Boolean verdadero si los campos requeridos no son null ni vacios, todos los campos
	 * validan y la validacion entre campos pasa el truth test
	 */
	public Boolean isValid() {

		Boolean valid = true;

		for (Map.Entry<String, Field> entry : this.fields.entrySet()) {

			Field field = entry.getValue();

			if (field.isRequired() && (field.getValue() == null || field.getValue().trim().equalsIgnoreCase(""))) {
				valid = false;
				errors.add("Falta campo: " + entry.getKey());
			} else if (!field.isValid()) {
				valid = false;
				errors.add(entry.getValue().getErrorDescription());
			}

		}
		if (valid) {

			if (interFieldValidation != null) {

				validated = valid = interFieldValidation.validateForm();

			} else {
				validated = true;
			}

		}
		return valid;

	}

	/**
	 * Para testear un campo en el momento
	 * @param key la clave del campo
	 * @param val el valor a ser probado
	 * @return null si el val valida la condicion que corresponde a su campo, el String
	 * correspondiente al error asociado sino
	 */
	public String testValidityForKey(String key, String val) {
		Field field = this.fields.get(key);
		if ( field != null ) {
			return field.isValid() ? null : field.getErrorDescription();
		}
		return "Field does not Exist";
	}

	/**
	 * Obtiene los errores obtenidos en el proceso de validacion en isValid
	 * @return Set<String> el conjunto de errores obtenidos en la validacion
	 */
	public Set<String> collectErrors() {
		return errors;
	}

	/**
	 * Obtiene si el formulario ya fue validado, para que, en caso de que la validacion sea extensa o
	 * costosa, no tener que correr isValid nuevamente
	 * @return Boolean true si ya fue validado, false sino
	 */
	public Boolean hasBeenValidated() {
		return validated;
	}
}
