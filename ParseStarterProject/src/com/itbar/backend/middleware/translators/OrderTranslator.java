package com.itbar.backend.middleware.translators;

import android.util.Log;

import com.itbar.backend.services.views.MenuItem;
import com.itbar.backend.services.views.Order;
import com.itbar.backend.util.FieldKeys;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by ioninielavitzky on 5/28/15.
 */
public class OrderTranslator {


	public static ParseObject fromOrder(Order order) {

		ParseObject obj = new ParseObject("Order");

		obj.put(FieldKeys.KEY_BUYER, ParseObject.createWithoutData("_User", order.getBuyer().getObjectId()));
		obj.put(FieldKeys.KEY_COMMENT, order.getComment());
		obj.put(FieldKeys.KEY_HORARIO, order.getHorario());
		obj.put(FieldKeys.KEY_PAYMENT_TYPE, order.getPaymentType());
		if (order.getStatus() != null && !order.getStatus().trim().equals(""))
			obj.put(FieldKeys.KEY_STATUS, order.getStatus());
		else
			obj.put(FieldKeys.KEY_STATUS, "Enviada");
		obj.put(FieldKeys.KEY_TOTAL, order.getTotal());

		return obj;
	}

	public static ParseObject parseObjectByChangingStatusOnOrder(Order order) {

		ParseObject obj = ParseObject.createWithoutData("Order", order.getObjectId());

		obj.put(FieldKeys.KEY_STATUS, order.getStatus());

		return obj;

	}

	public static Order toOrder(ParseObject obj) {

		Order order = new Order();

		order.setStatus(obj.getString(FieldKeys.KEY_STATUS));
		order.setBuyer(UserTranslator.toUser(UserTranslator.fromParseObject(obj.getParseObject("buyer"))));
		order.setComment(obj.getString(FieldKeys.KEY_COMMENT));
		order.setHorario(obj.getString(FieldKeys.KEY_HORARIO));
//		order.setItems();
		order.setObjectId(obj.getObjectId());
		order.setPaymentType(obj.getString(FieldKeys.KEY_PAYMENT_TYPE));
		order.setTotal( obj.getNumber(FieldKeys.KEY_TOTAL).doubleValue());

		return order;

	}
}
