package com.itbar.backend.util.fields;

import com.itbar.backend.util.Field;
import com.itbar.backend.util.Formats;

/**
 * Created by martin on 5/22/15.
 */
public class NameField extends TextField {

	public NameField() {
		super();
	}

	public NameField(Boolean required) {
		super(required);
	}

	public NameField(Boolean required, String elem) {
		super(required, elem);
	}


	/* puedo pedir algo para la DB o que empieze con mayuscula */
	@Override
	public Boolean isValid() {
		return Formats.NAME.matcher(this.getValue()).matches();
	}

	@Override
	public String getErrorDescription() {
		return "Nombre no valido";
	}

}
