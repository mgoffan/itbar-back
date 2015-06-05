package com.itbar.backend.util.types;


/**
 * Created by martin on 20/05/15.
 */
public class Phone {
	/**
	 * The Phone.
	 */
	String phone;

	/**
	 * Instantiates a new Phone.
	 *
	 * @param phone the phone
	 */
	public Phone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets phone.
	 *
	 * @param phone the phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return this.getPhone();
	}
}
