package com.itbar.backend.middleware.translators;

import com.itbar.backend.services.views.Category;
import com.parse.ParseObject;

/**
 * Created by martin on 5/23/15.
 */
public class CategoryTranslator {

	public static Category toCategory(ParseObject category) {

		Category cat = new Category(category.getString("name"), category.getObjectId());

		return cat;
	}

	public static ParseObject fromCategory(Category category) {

		ParseObject obj = new ParseObject("MenuCategory");

		obj.put("name", category.getName());
		if (category.getObjectId() != null)
			obj.put("objectId", category.getObjectId());

		return obj;

	}

}
