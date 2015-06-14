package com.itbar.backend.middleware.translators;

import com.itbar.backend.services.views.User;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;
import com.itbar.backend.util.FormBuilder;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Traductor de informacion campo a campo del modelo User
 *
 * Discucion:
 *
 * Tal cual lo hablamos con Pablo, esta no es la solucion mas elegante ni la mas prolija pero es
 * la unica alternativa que tenemos para convertir un usuario de Parse en un usuario de la app
 * porque la app no habla ParseUser, habla User
 *
 * Created by martin on 5/22/15.
 */
public class UserTranslator {

	public static User toUser(ParseUser parseUser) {

		Form form = FormBuilder.buildUserForm();

		form.set(FieldKeys.KEY_LEGAJO, parseUser.getUsername());
		form.set(FieldKeys.KEY_EMAIL, parseUser.getEmail());
		form.set(FieldKeys.KEY_NAME, parseUser.getString("name"));
		form.set(FieldKeys.KEY_SURNAME, parseUser.getString("surname"));
		form.set(FieldKeys.KEY_PHONE, parseUser.getString("phone"));
		form.set(FieldKeys.KEY_ID, parseUser.getObjectId());



		User user = new User(form);

		return user;

	}

	public static ParseUser fromUser(User user) {

		return fromUser(user, null);

	}

	public static ParseUser fromUser(User user, String pass) {

		ParseUser parseUser = new ParseUser();

		parseUser.setUsername(user.getLegajo());

		if (pass != null && !pass.trim().equalsIgnoreCase("")) {
			parseUser.setPassword(pass);
		}

		parseUser.setEmail(user.getEmail());

		parseUser.put(FieldKeys.KEY_PHONE, user.getPhone());
		parseUser.put(FieldKeys.KEY_NAME, user.getName());
		parseUser.put(FieldKeys.KEY_SURNAME, user.getSurname());
		parseUser.put(FieldKeys.KEY_TYPE, "user");
		if (user.getObjectId() != null) {
			parseUser.put(FieldKeys.KEY_ID, user.getObjectId());
		}

		return parseUser;

	}

	public static ParseUser fromParseObject(ParseObject obj) {

		ParseUser parseUser = new ParseUser();

		parseUser.setUsername(obj.getString("username"));
		parseUser.setEmail(obj.getString("email"));
		parseUser.put(FieldKeys.KEY_PHONE, obj.getString(FieldKeys.KEY_PHONE));
		parseUser.put(FieldKeys.KEY_NAME, obj.getString(FieldKeys.KEY_NAME));
		parseUser.put(FieldKeys.KEY_SURNAME, obj.getString(FieldKeys.KEY_SURNAME));
		parseUser.put(FieldKeys.KEY_TYPE, "user");

		if (!obj.isDataAvailable()) {
			parseUser.setObjectId(obj.getObjectId());
		}





		return parseUser;

	}

}
