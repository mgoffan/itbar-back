package com.itbar.backend.util.types;

import com.itbar.backend.util.Formats;

/**
 * Created by martin on 20/05/15.
 */
public class Email {
	/**
	 * The Email.
	 */
	String email;

	/**
	 * Instantiates a new Email.
	 *
	 * @param email the email
	 */
	public Email(String email) {
		this.email = email;
	}

	/**
	 * Validate format.
	 *
	 * @param email the email
	 * @return the boolean
	 * @deprecated
	 */
	public static Boolean validateFormat(String email) {
		return Formats.EMAIL.matcher(email).matches();
	}

	public static Boolean isValid(String email) {
		return Formats.EMAIL.matcher(email).matches();
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
	 * Sets email.
	 *
	 * @param email the email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
