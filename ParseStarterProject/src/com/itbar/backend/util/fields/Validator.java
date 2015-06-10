package com.itbar.backend.util.fields;

/**
 * Created by martin on 02/06/15.
 */
public interface Validator {
	Boolean isValid(String val);
	String errorDescription();
}
