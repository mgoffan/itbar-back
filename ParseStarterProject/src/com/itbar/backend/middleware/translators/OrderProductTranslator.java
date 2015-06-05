package com.itbar.backend.middleware.translators;

import com.itbar.backend.services.views.Order;
import com.itbar.backend.services.views.OrderProduct;
import com.parse.ParseObject;

/**
 * Created by ioninielavitzky on 5/28/15.
 */
public class OrderProductTranslator {

    public static ParseObject fromOrderProduct(OrderProduct orderProduct) {

        ParseObject obj = new ParseObject("OrderProduct");

        obj.put("comment", orderProduct.getComment());
        obj.put("item", ParseObject.createWithoutData("MenuItem", orderProduct.getMenuItem().getObjectId()));
        obj.put("order", ParseObject.createWithoutData("Order", orderProduct.getOrder().getObjectId()));
        obj.put("quantity", orderProduct.getQuantity());

        return obj;
    }
}
