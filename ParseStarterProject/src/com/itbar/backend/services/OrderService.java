package com.itbar.backend.services;

import com.itbar.backend.middleware.OrderMiddleware;
import com.itbar.backend.services.callbacks.FindMultipleCallback;
import com.itbar.backend.services.callbacks.RUDCallback;
import com.itbar.backend.services.callbacks.SaveOrderCallback;
import com.itbar.backend.services.views.MenuItem;
import com.itbar.backend.services.views.Order;
import com.itbar.backend.services.views.OrderProduct;
import com.itbar.backend.services.views.User;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;

/**
 *
 * Discucion:
 *
 * Ver UserService
 *
 * Created by martin on 5/23/15.
 */
public class OrderService {


	/* con el form se pueden aplicar filtros */


	public static void getOrders(Form form, FindMultipleCallback cb) {

		if (form.hasBeenValidated() || form.isValid()) {

			if (Session.use().getCurrentBar() != null) {
				OrderMiddleware.fetchWithStatus(form.get(FieldKeys.KEY_STATUS), cb);
			} else {
				cb.error(new RemoteError(RemoteError.NOT_LOGGED_IN, "No ha iniciado sesion"));
			}
		} else {
			cb.error(new RemoteError(RemoteError.INVALID_FORM, "Formulario invalido"));
		}

	}

	public static void updateItem(Form form) {
		/** TODO: puede ser que tenga sentido hacer un removeItem y un addItem */


	}
}
