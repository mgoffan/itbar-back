package com.itbar.backend.services;

import com.itbar.backend.middleware.BarMiddleware;
import com.itbar.backend.services.callbacks.BarLogInCallback;
import com.itbar.backend.services.callbacks.FindMultipleCallback;
import com.itbar.backend.services.callbacks.RUDCallback;
import com.itbar.backend.services.callbacks.SaveCategoryCallback;
import com.itbar.backend.services.callbacks.SaveMenuItemCallback;
import com.itbar.backend.services.views.Bar;
import com.itbar.backend.services.views.Category;
import com.itbar.backend.services.views.MenuItem;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;

import java.security.Key;


/**
 * Created by martin on 5/23/15.
 */
public class BarService {

	public void loginBar(Form form, final BarLogInCallback cb) {

		if (Session.use().getCurrentBar() == null
				&& (form.hasBeenValidated() || form.isValid()) ) {

			BarMiddleware.loginBar(form.get(FieldKeys.KEY_CUIT), form.get(FieldKeys.KEY_PASSWORD), new BarLogInCallback() {
				@Override
				public void success(Bar bar) {
					Session.use().setCurrentBar(bar);
					cb.success(bar);
				}

				@Override
				public void error(RemoteError e) {
					cb.error(e);
				}
			});
		} else {

			if ( Session.use().getCurrentBar() == null) {
				cb.error(new RemoteError(RemoteError.ALREADY_LOGGED_IN, "Ya inicio sesion"));
			} else {
				cb.error(new RemoteError(RemoteError.INVALID_FORM, "Formulario invalido"));
			}
		}
	}

	public void getMenus(FindMultipleCallback<Category> cb) {
		if ( Session.use().getCurrentBar() != null) {

			BarMiddleware.getMenus(cb);
		} else {
			cb.error(new RemoteError(RemoteError.NOT_LOGGED_IN, "No ha iniciado sesion"));
		}
	}

	public void addCategory(Form form, SaveCategoryCallback cb) {

		if ( Session.use().getCurrentBar() != null ) {

			if (form.hasBeenValidated() || form.isValid()) {

				Category category = new Category(form.get(FieldKeys.KEY_NAME), form.get(FieldKeys.KEY_ID));

				BarMiddleware.addCategory(category, cb);

			} else {

				cb.error(new RemoteError(RemoteError.INVALID_FORM, "Formulario invalido"));

			}

		} else {

			cb.error(new RemoteError(RemoteError.NOT_LOGGED_IN, "No ha iniciado sesion"));

		}

	}

	public void removeCategory(Form form, RUDCallback cb) {

		if (Session.use().getCurrentBar() != null) {

			if (form.hasBeenValidated() || form.isValid()) {

				Category category = new Category();

				category.setObjectId(form.get(FieldKeys.KEY_ID));

				BarMiddleware.removeCategory(category, cb);

			} else {
				cb.error(new RemoteError(RemoteError.INVALID_FORM, "Formulario Invalido"));
			}

		} else {
			cb.error(new RemoteError(RemoteError.NOT_LOGGED_IN, "No ha iniciado sesion"));
		}

	}

	public void updateCategory(Form form, SaveCategoryCallback cb) {

		if (Session.use().getCurrentBar() != null) {

			if (form.hasBeenValidated() || form.isValid()) {

				Category category = new Category(form.get(FieldKeys.KEY_NAME));

				BarMiddleware.updateCategory(category, cb);

			} else {
				cb.error(new RemoteError(RemoteError.INVALID_FORM, "Formulario Invalido"));
			}

		} else {
			cb.error(new RemoteError(RemoteError.NOT_LOGGED_IN, "No ha iniciado sesion"));
		}
	}

	public void addMenuItem(Form form, SaveMenuItemCallback cb) {

		if (Session.use().getCurrentBar() != null) {

			if (form.hasBeenValidated() || form.isValid()) {

				MenuItem item = new MenuItem();

				item.setName(form.get(FieldKeys.KEY_NAME));
				item.setDescription(form.get(FieldKeys.KEY_DESCRIPTION));
				item.setPrice((Double) form.getField(FieldKeys.KEY_PRICE).retrieveResult());

				Category category = new Category();
				category.setObjectId(form.get(FieldKeys.KEY_CATEGORY));
				item.setCategory(category);

				BarMiddleware.addMenuItem(item, cb);

			} else {
				cb.error(new RemoteError(RemoteError.INVALID_FORM, "Formulario Invalido"));
			}

		} else {
			cb.error(new RemoteError(RemoteError.NOT_LOGGED_IN, "No ha iniciado sesion"));
		}
	}

	public void removeMenuItem(Form form, RUDCallback cb) {

		if (Session.use().getCurrentBar() != null) {

			if (form.hasBeenValidated() || form.isValid()) {

				MenuItem item = new MenuItem();

				item.setObjectId(form.get(FieldKeys.KEY_ID));

				BarMiddleware.removeMenuItem(item, cb);

			} else {
				cb.error(new RemoteError(RemoteError.INVALID_FORM, "Formulario Invalido"));
			}

		} else {
			cb.error(new RemoteError(RemoteError.NOT_LOGGED_IN, "No ha iniciado sesion"));
		}
	}

	public void updateMenuItem(Form form, SaveMenuItemCallback cb) {

		if (Session.use().getCurrentBar() != null) {

			if (form.hasBeenValidated() || form.isValid()) {

				MenuItem item = new MenuItem();

				item.setName(form.get(FieldKeys.KEY_NAME));
				item.setDescription(form.get(FieldKeys.KEY_DESCRIPTION));
				item.setPrice((Double) form.getField(FieldKeys.KEY_PRICE).retrieveResult());
				item.setObjectId(form.get(FieldKeys.KEY_ID));
				Category category = new Category();
				category.setObjectId(form.get(FieldKeys.KEY_CATEGORY));
				item.setCategory(category);

				BarMiddleware.updateMenuItem(item, cb);

			} else {
				cb.error(new RemoteError(RemoteError.INVALID_FORM, "Formulario Invalido"));
			}

		} else {
			cb.error(new RemoteError(RemoteError.NOT_LOGGED_IN, "No ha iniciado sesion"));
		}
	}

}
