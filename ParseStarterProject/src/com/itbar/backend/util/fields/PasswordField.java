package com.itbar.backend.util.fields;

import com.itbar.backend.util.Field;
import com.itbar.backend.util.Formats;

import java.util.regex.Matcher;

/**
 * Created by martin on 5/22/15.
 */
public class PasswordField extends TextField {

	public PasswordField() {
		super();
	}

	public PasswordField(Boolean required) {
		super(required);
	}

	public PasswordField(Boolean required, String elem) {
		super(required, elem);
	}

	@Override
	public Boolean isValid() {
		return this.getValue().length() > 4;
	}

	@Override
	public String getErrorDescription() {
		return "Contraseña corta";
	}

}
