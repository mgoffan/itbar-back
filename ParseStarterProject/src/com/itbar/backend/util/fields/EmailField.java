package com.itbar.backend.util.fields;

import com.itbar.backend.util.Field;
import com.itbar.backend.util.Formats;
import com.itbar.backend.util.types.Email;

/**
 * Created by martin on 5/22/15.
 */
public class EmailField extends TextField {

	public EmailField() {
		super();
	}

	public EmailField(Boolean required) {
		super(required);
	}

	public EmailField(Boolean required, String elem) {
		super(required, elem);
	}

	@Override
	public Boolean isValid() {
		return Formats.EMAIL.matcher(this.getValue()).matches();
	}

	@Override
	public String getErrorDescription() {
		return "Email no valido";
	}
}
