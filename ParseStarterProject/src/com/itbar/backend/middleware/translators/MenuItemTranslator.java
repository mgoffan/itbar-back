package com.itbar.backend.middleware.translators;

import com.itbar.backend.services.views.MenuItem;
import com.parse.ParseObject;

/**
 * Created by martin on 5/23/15.
 */
public class MenuItemTranslator {

	public static MenuItem toMenuItem(ParseObject menuItem) {

		MenuItem item = new MenuItem();

		item.setCategory(CategoryTranslator.toCategory(menuItem.getParseObject("category")));
		item.setName(menuItem.getString("name"));
		item.setDescription(menuItem.getString("description"));
		item.setPrice(menuItem.getNumber("price").doubleValue());
		item.setObjectId(menuItem.getObjectId());

		return item;

	}

	public static ParseObject fromMenuItem(MenuItem menuItem) {

		ParseObject obj = new ParseObject("MenuItem");

		obj.put("name", menuItem.getName());
		obj.put("description", menuItem.getDescription());
		obj.put("price", menuItem.getPrice());
		obj.put("category", ParseObject.createWithoutData("Category", menuItem.getCategory().getObjectId()));

		return obj;
	}
}
