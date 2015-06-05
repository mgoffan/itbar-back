package com.itbar.backend.util.fields;

import com.itbar.backend.util.Field;
import com.itbar.backend.util.Formats;

/**
 * Created by martin on 5/22/15.
 */
public class LegajoField extends NumberField {

	public LegajoField() {
		super();
	}

	public LegajoField(Boolean required) {
		super(required);
	}

	public LegajoField(Boolean required, String elem) {
		super(required, elem);
	}

	@Override
	public Boolean isValid() {
		return Formats.LEGAJO.matcher(this.getValue()).matches();
	}

	@Override
	public String getErrorDescription() {
		return "Legajo no valido";
	}

}
