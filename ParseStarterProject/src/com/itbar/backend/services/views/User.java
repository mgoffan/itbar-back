package com.itbar.backend.services.views;

import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;

/**
 * Exhibe el comportamiento de un usuario, sin mostrar todo el comportamiento de un ParseUser
 * <p/>
 * Es una clase read-only solo muestra la informacion contenida en el usuario
 * <p/>
 * Para modificar un User se lo debe pedir al Back End
 * <p/>
 * <code>
 * <p/>
 * Backend.getInstance().loginUser(Form);
 * <p/>
 * </code>
 * <p/>
 * Created by martin on 5/21/15.
 */
public class User {

	private String legajo;
	private String email;
	private String name;
	private String surname;
	private String phone;
	private String objectId;

	public User(Form form) {

		this.applyForm(form);

	}

	public void applyForm(Form form) {

		if (form.hasBeenValidated() || form.isValid()) {
			this.legajo = form.get(FieldKeys.KEY_LEGAJO);
			this.email = form.get(FieldKeys.KEY_EMAIL);
			this.name = form.get(FieldKeys.KEY_NAME);
			this.surname = form.get(FieldKeys.KEY_SURNAME);
			this.phone = form.get(FieldKeys.KEY_PHONE);
			this.objectId = form.get(FieldKeys.KEY_ID);
		} else {
			System.out.println(form.collectErrors());
		}

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
	 * Gets email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets surname.
	 *
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Gets phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	public String getObjectId() {
		return objectId;
	}
}
