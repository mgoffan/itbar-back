package com.itbar.backend.util.types;

import com.itbar.backend.util.Field;
import com.itbar.backend.util.Formats;

/**
 * Created by martin on 20/05/15.
 */
public class Legajo {

	private String legajo;

	/**
	 * Instantiates a new Legajo.
	 *
	 * @param legajo the legajo
	 */
	public Legajo(String legajo) {
		this.legajo = legajo;
	}

	/**
	 * Validate format.
	 *
	 * @param legajo the legajo
	 * @return the boolean
	 */
	public static Boolean validateFormat(String legajo) {
		return Formats.LEGAJO.matcher(legajo).matches();
	}

	public static Boolean isValid(String legajo) {
		return Formats.LEGAJO.matcher(legajo).matches();
	}

	/**
	 * Gets legajo.
	 *
	 * @return the legajo
	 */
	public String getLegajo() {
		return legajo;
	}

	/**
	 * Sets legajo.
	 *
	 * @param legajo the legajo
	 */
	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}

}
