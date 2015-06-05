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
 * @see UserService
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

	public static void placeOrder(Form form, final SaveOrderCallback cb) {
		/**(Parse) Con el usuario agarra a Order y primero crea en Parse un objeto de tipo order, con el id de Order mete todos
		 * los OrderProducts y los linkea al MenuItem y al Order. Esto se hace usando el metodo saveAllInBackground de Parse*/


		if (form.hasBeenValidated() || form.isValid()) {

			Session.use().getCurrentOrder().setBuyer(Session.use().getCurrentUser());

			Session.use().getCurrentOrder().setComment(form.get(FieldKeys.KEY_COMMENT));
			Session.use().getCurrentOrder().setHorario(form.get(FieldKeys.KEY_HORARIO));
			Session.use().getCurrentOrder().setPaymentType(form.get(FieldKeys.KEY_PAYMENT_TYPE));
			Session.use().getCurrentOrder().setTotal((Double)form.getField(FieldKeys.KEY_TOTAL).retrieveResult());

			OrderMiddleware.placeOrder(Session.use().getCurrentOrder(), new SaveOrderCallback() {
				@Override
				public void success() {
					// Reinicia el pedido
					Session.use().setCurrentOrder(null);
					cb.success();
				}

				@Override
				public void error(RemoteError e) {
					cb.error(e);
				}
			});
		} else {
			cb.error(new RemoteError(RemoteError.INVALID_FORM, "La informacion del pedido no es valida"));
		}
	}

	public static void cancelOrder(Form form, RUDCallback cb) { //(Parse)

		if (Session.use().getCurrentOrder() == null) {
			cb.error(new RemoteError(RemoteError.NO_SUCH_ORDER, "No hay nada en el carrito"));
			return;
		}

		OrderMiddleware.cancelOrder(Session.use().getCurrentOrder(), cb);

	}

	public static void addItem(MenuItem menuItem, String comment, int qty) { //(LOCAL) Aca mete OrderProduct en el mapa de Order
		OrderProduct orderProduct = new OrderProduct(menuItem, comment, qty);
		if (Session.use().getCurrentOrder() == null)
			Session.use().setCurrentOrder(new Order());
		Session.use().getCurrentOrder().addItem(orderProduct);
	}

	public static void removeItem(MenuItem mI) { //(LOCAL)
		if (Session.use().getCurrentOrder() != null)
			Session.use().getCurrentOrder().removeItem(new OrderProduct(mI));
	}

	public static void updateItem(Form form) {
		/** TODO: puede ser que tenga sentido hacer un removeItem y un addItem */


	}
}
