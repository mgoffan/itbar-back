package com.itbar.backend.util.fields;

import com.itbar.backend.util.Formats;

/**
 * Created by martin on 5/29/15.
 */
public class TimeField extends TextField {

	private Boolean format24hs = true;

	public TimeField () {
		super();
	}

	public TimeField(Boolean required) {
		super(required);
	}

	public TimeField(Boolean required, String elem) {
		super(required, elem);
	}

	public Boolean isFormat24hs() {
		return format24hs;
	}

	public void setFormat24hs(Boolean format24hs) {
		this.format24hs = format24hs;
	}

	@Override
	public String getErrorDescription() {
		return "Horario Incorrecto";
	}

	@Override
	public Boolean isValid() {
		if (format24hs) {
			return Formats.TIME24.matcher(this.getValue()).matches();
		} else {
			return Formats.TIME12.matcher(this.getValue()).matches();
		}
	}
}
