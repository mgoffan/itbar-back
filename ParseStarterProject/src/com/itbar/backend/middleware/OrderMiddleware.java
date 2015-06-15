package com.itbar.backend.middleware;

import com.itbar.backend.middleware.translators.MenuItemTranslator;
import com.itbar.backend.middleware.translators.OrderProductTranslator;
import com.itbar.backend.middleware.translators.OrderTranslator;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.callbacks.FindMultipleCallback;
import com.itbar.backend.services.callbacks.RUDCallback;
import com.itbar.backend.services.callbacks.SaveOrderCallback;
import com.itbar.backend.services.views.Order;
import com.itbar.backend.services.views.OrderProduct;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 5/28/15.
 */
public class OrderMiddleware {

	public static void placeOrder(final Order order, final SaveOrderCallback cb) {

		final ParseObject parseOrder = OrderTranslator.fromOrder(order);

		parseOrder.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					order.setObjectId(parseOrder.getObjectId());

					List<ParseObject> items = new ArrayList<>();

					for (OrderProduct oP : order.getItems()) {
						items.add(OrderProductTranslator.fromOrderProduct(oP));
					}

					ParseObject.saveAllInBackground(items, new SaveCallback() {
						@Override
						public void done(com.parse.ParseException e) {
							if (e != null) {
								cb.error(new RemoteError(e));
							} else {
								cb.success();
							}
						}
					});
				}
			}
		});

	}

	public static void cancelOrder(Order order, RUDCallback cb) {

		updateOrderStatus(order, cb);

	}

	public static void updateOrderStatus(final Order order, final RUDCallback cb) {

		final ParseObject parseOrder = OrderTranslator.parseObjectByChangingStatusOnOrder(order);

		parseOrder.saveInBackground(new SaveCallback() {
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



	public static void fetchAll(FindMultipleCallback cb) {
		fetchWithStatus(null, cb);
	}

	public static void fetchWithStatus(String status, final FindMultipleCallback<Order> cb) {

		ParseQuery<ParseObject> query = new ParseQuery<>("Order");

		query.include("buyer").orderByDescending("createdAt").addDescendingOrder("horario");

		if (status != null)
			query.whereEqualTo("status", status);

		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> list, ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {

					List<Order> orders = new ArrayList<>();

					for (ParseObject obj : list) {

						orders.add(OrderTranslator.toOrder(obj));

					}

					cb.success(orders);


				}
			}
		});

	}

	public static void getProductsForOrder(String orderId, final FindMultipleCallback<OrderProduct> cb) {

		ParseQuery<ParseObject> query = new ParseQuery<>("OrderProduct");

		query.whereEqualTo("order", ParseObject.createWithoutData("Order", orderId));

		query.include("item");

		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> list, ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {

					List<OrderProduct> menuItemList = new ArrayList<>();

					for (ParseObject obj : list) {
						menuItemList.add(MenuItemTranslator.toOrderProduct(obj));
					}

					cb.success(menuItemList);

				}
			}
		});

	}
}
