package com.itbar.backend.util.fields;

import com.itbar.backend.util.Field;
import com.itbar.backend.util.Formats;

/**
 * Representa a un campo cuyo contenido va a ser puramente numerico.
 *
 * Abarca las dos posibilidades que sea un numero entero o racional
 *
 * Retrieve result devuelve el tipo correspondiente en cada caso
 *
 * Created by martin on 5/29/15.
 */
public class NumberField<T extends Number> extends Field<T> {

	private Boolean hasFloatingPoint;

	public NumberField () {
		super();
	}

	public NumberField(Boolean required) {
		super(required);
	}

	public NumberField(Boolean required, String elem) {
		super(required, elem);
	}

	public NumberField setHasFloatingPoint(Boolean hasFloatingPoint) {
		this.hasFloatingPoint = hasFloatingPoint;
		return this;
	}

	public Boolean hasFloatingPoint() {
		return hasFloatingPoint;
	}

	@Override
	public String getErrorDescription() {
		return "No es un numero";
	}

	/**
	 * Valida contra RegExp distintos dependiendo del contenido del campo
	 * @return Boolean true si valida alguno del los dos casos (es un numero)
	 */
	@Override
	public Boolean isValid() {

		if (hasFloatingPoint)
			return Formats.NUMBER_DOUBLE.matcher(this.getValue()).matches();

		return Formats.NUMBER_INT.matcher(this.getValue()).matches() || ( hasFloatingPoint = Formats.NUMBER_DOUBLE.matcher(this.getValue()).matches());
	}

	/**
	 * Extrae el resultado numerico
	 * @return T el numero que contiene el campo solo si es valido, null sino
	 */
	@Override
	public T retrieveResult() {
		if (isValid()) {
			if (hasFloatingPoint) {
				return (T) Double.valueOf(this.getValue());
			} else {
				return (T) Integer.valueOf(this.getValue());
			}
		}
		return null;
	}
}
