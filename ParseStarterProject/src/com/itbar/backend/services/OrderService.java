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


	public void getOrders(Form form, FindMultipleCallback cb) {

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

	public void getProductsForOrder(Form form, FindMultipleCallback cb) {
		if (form.hasBeenValidated() || form.isValid()) {

			if (Session.use().getCurrentBar() != null) {
				OrderMiddleware.getProductsForOrder(form.get(FieldKeys.KEY_ID), cb);
			} else {
				cb.error(new RemoteError(RemoteError.NOT_LOGGED_IN, "No ha iniciado sesion"));
			}
		} else {
			cb.error(new RemoteError(RemoteError.INVALID_FORM, "Formulario invalido"));
		}
	}

	public void cancelOrder(Form form, RUDCallback cb) {

		if (form.hasBeenValidated() || form.isValid()) {

			if (Session.use().getCurrentBar() != null) {

				Order order = new Order();

				order.setStatus(form.get(FieldKeys.KEY_STATUS));

				OrderMiddleware.cancelOrder(order, cb);
			} else {
				cb.error(new RemoteError(RemoteError.NOT_LOGGED_IN, "No ha iniciado sesion"));
			}
		} else {
			cb.error(new RemoteError(RemoteError.INVALID_FORM, "Formulario invalido"));
		}

	}

	public void orderHasBeenPrepared(Form form, RUDCallback cb) {

		if (form.hasBeenValidated() || form.isValid()) {

			if (Session.use().getCurrentBar() != null) {

				Order order = new Order();

				order.setObjectId(form.get(FieldKeys.KEY_ID));
				order.setStatus(form.get(FieldKeys.KEY_STATUS));

				OrderMiddleware.updateOrderStatus(order, cb);
			} else {
				cb.error(new RemoteError(RemoteError.NOT_LOGGED_IN, "No ha iniciado sesion"));
			}
		} else {
			cb.error(new RemoteError(RemoteError.INVALID_FORM, "Formulario invalido"));
		}

	}
}
