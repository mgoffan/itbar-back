package com.itbar.backend.middleware.translators;

import com.itbar.backend.services.views.Category;
import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * Created by martin on 5/23/15.
 */
public class CategoryTranslator {

	public static Category toCategory(ParseObject category) {

		Category cat;

		try {
			category = category.fetchIfNeeded();

			cat = new Category(category.getString("name"), category.getObjectId());
		} catch (ParseException ex) {

			cat = new Category("", "");
		}

		return cat;
	}

	public static ParseObject fromCategory(Category category) {

		ParseObject obj;

		if (category.getObjectId() != null && !category.getObjectId().trim().equals(""))
			obj = ParseObject.createWithoutData("MenuCategories", category.getObjectId());
		else
			obj = new ParseObject("MenuCategories");

		obj.put("name", category.getName());

		return obj;

	}

}
