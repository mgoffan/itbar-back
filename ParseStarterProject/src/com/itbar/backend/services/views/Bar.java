package com.itbar.backend.services.views;

import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;

/**
 * Esta es la vista de un Bar que es en esta app DUMMY o POJO como es a veces denominadao. Se usa
 * para que el front vea como es la representacion de este objeto. Sin embargo, los cambios no son
 * registrados pues si el usuario cambiara alguna informacion y ofreciecemos la posibilidad de
 * guardarla entonces estariamos abriendo el programa a que el front gaurde cualquier cosa que se le
 * ocurra. Por esta razon, implementamos los Form.
 *
 * @see Form
 * @see com.itbar.backend.util.FormBuilder
 *
 * Created by martin on 5/23/15.
 */
public class Bar {

	private String objectId;
	private String cuit;
	private String email;
	private String name;
	private String phone;

	public Bar() {

	}

	public Bar(Form form) {

		this.applyForm(form);

	}

	public void applyForm(Form form) {

		if (form.hasBeenValidated() || form.isValid()) {
			this.cuit= form.get(FieldKeys.KEY_CUIT);
			this.email = form.get(FieldKeys.KEY_EMAIL);
			this.name = form.get(FieldKeys.KEY_NAME);
			this.phone = form.get(FieldKeys.KEY_PHONE);
			this.objectId = form.get(FieldKeys.KEY_ID);
		}

	}

	public String getCuit() {
		return cuit;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
}
