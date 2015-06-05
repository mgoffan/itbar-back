package com.itbar.backend.util.fields;

import com.itbar.backend.util.Field;
import com.itbar.backend.util.Formats;

/**
 *
 * Created by martin on 5/22/15.
 */
public class CuitField extends TextField {

	public CuitField() {
		super();
	}

	public CuitField(Boolean required) {
		super(required);
	}

	public CuitField(Boolean required, String elem) {
		super(required, elem);
	}

	@Override
	public Boolean isValid() {
		return Formats.CUIT.matcher(this.getValue()).matches();
	}

	@Override
	public String getErrorDescription() {
		return "CUIT no valido";
	}
}
