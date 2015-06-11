package com.itbar.backend.middleware.translators;

import android.util.Log;

import com.itbar.backend.services.views.Bar;
import com.itbar.backend.services.views.User;
import com.itbar.backend.util.Field;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;
import com.itbar.backend.util.FormBuilder;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by martin on 5/23/15.
 */
public class BarTranslator {

	public static Bar toBar(ParseUser parseBar) {

		Form form = FormBuilder.buildBarForm();

		form.set(FieldKeys.KEY_CUIT, parseBar.getUsername());
		form.set(FieldKeys.KEY_EMAIL, parseBar.getEmail());
		form.set(FieldKeys.KEY_NAME, parseBar.getString("name"));
		form.set(FieldKeys.KEY_PHONE, parseBar.getString("phone"));
		form.set(FieldKeys.KEY_ID, parseBar.getObjectId());

		Bar bar = new Bar(form);

		return bar;

	}

	public static ParseUser fromBar(Bar bar) {

		return fromBar(bar, null);

	}

	public static ParseUser fromBar(Bar bar, String pass) {

		ParseUser parseUser = new ParseUser();

		parseUser.setUsername(bar.getCuit());

		if (pass != null && pass.trim().equalsIgnoreCase(""))
			parseUser.setPassword(pass);

		parseUser.setEmail(bar.getEmail());

		parseUser.put(FieldKeys.KEY_PHONE, bar.getPhone());
		parseUser.put(FieldKeys.KEY_NAME, bar.getName());
		parseUser.put(FieldKeys.KEY_TYPE, "bar");

		return parseUser;

	}
}
