package com.itbar.backend.middleware;

import com.itbar.backend.middleware.translators.BarTranslator;
import com.itbar.backend.middleware.translators.CategoryTranslator;
import com.itbar.backend.middleware.translators.MenuItemTranslator;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.callbacks.BarLogInCallback;
import com.itbar.backend.services.callbacks.FindMultipleCallback;
import com.itbar.backend.services.callbacks.RUDCallback;
import com.itbar.backend.services.views.Category;
import com.itbar.backend.services.views.MenuItem;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by martin on 5/23/15.
 */
public class BarMiddleware {

	public static void loginBar(String cuit, String pass, final BarLogInCallback cb) {

		ParseUser.logInInBackground(cuit, pass, new LogInCallback() {
			@Override
			public void done(ParseUser bar, ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					cb.success(BarTranslator.toBar(bar));
				}
			}
		});
	}

	public static void getMenus(final FindMultipleCallback<Category> cb) {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("MenuItem");
		query.include("category");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> list, ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					HashMap<Category, List<MenuItem>> categories = new HashMap<>();

					List<Category> result = new ArrayList<>();

					for (ParseObject parseMenuItem : list) {

						Category key = CategoryTranslator.toCategory(parseMenuItem.getParseObject("category"));

						if (categories.containsKey(key)) {
							categories.get(key).add(MenuItemTranslator.toMenuItem(parseMenuItem));
						} else {
							List<MenuItem> l = new ArrayList<>();
							l.add(MenuItemTranslator.toMenuItem(parseMenuItem));
							categories.put(key, l);
						}
					}

					for (Map.Entry<Category, List<MenuItem>> entry : categories.entrySet()) {
						Category c = new Category(entry.getKey().getName(), entry.getKey().getObjectId());
						c.addItems(entry.getValue());
						result.add(c);
					}

					cb.success(result);
				}
			}
		});

	}

	public static void addCategory(Category category, final RUDCallback cb) {

		ParseObject object = CategoryTranslator.fromCategory(category);

		object.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					cb.success();
				}
			}
		});

	}

	public static void removeCategory(Category category, final RUDCallback cb) {

		ParseObject object = CategoryTranslator.fromCategory(category);

		if (object.getObjectId() == null) {
			cb.error(new RemoteError(104, "Falta OID"));
			return;
		}

		object.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					cb.success();
				}
			}
		});

	}

	public static void updateCategory(Category category, final RUDCallback cb) {
		addCategory(category, cb);
	}

	public static void addMenuItem(MenuItem item, final RUDCallback cb) {

		ParseObject object = MenuItemTranslator.fromMenuItem(item);

		object.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					cb.success();
				}
			}
		});
	}

	public static void removeMenuItem(MenuItem item, final RUDCallback cb) {

		ParseObject object = MenuItemTranslator.fromMenuItem(item);

		if (object.getObjectId() == null) {
			cb.error(new RemoteError(104, "Falta OID"));
			return;
		}

		object.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					cb.success();
				}
			}
		});

	}


}
