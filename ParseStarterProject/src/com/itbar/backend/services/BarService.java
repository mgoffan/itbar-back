package com.itbar.backend.services;

import com.itbar.backend.middleware.BarMiddleware;
import com.itbar.backend.services.callbacks.BarLogInCallback;
import com.itbar.backend.services.callbacks.FindMultipleCallback;
import com.itbar.backend.services.views.Bar;
import com.itbar.backend.services.views.Category;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;


/**
 * Created by martin on 5/23/15.
 */
public class BarService {

	public void loginBar(Form form, final BarLogInCallback cb) {

		if (Session.use().getCurrentBar() == null
				&& (form.hasBeenValidated() || form.isValid()) ) {

			BarMiddleware.loginBar(form.get(FieldKeys.KEY_LEGAJO), form.get(FieldKeys.KEY_PASSWORD), new BarLogInCallback() {
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

}
