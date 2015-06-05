package com.itbar.backend.util.fields;

import com.itbar.backend.util.Field;
import com.itbar.backend.util.Formats;

/**
 * Created by martin on 5/22/15.
 */
public class PhoneField extends TextField {

	public PhoneField() {
		super();
	}

	public PhoneField(Boolean required) {
		super(required);
	}

	public PhoneField(Boolean required, String elem) {
		super(required, elem);
	}

	@Override
	public Boolean isValid() {
		return Formats.PHONE.matcher((String)this.getValue()).matches();
	}

	@Override
	public String getErrorDescription() {
		return "Telefono no valido";
	}

}
