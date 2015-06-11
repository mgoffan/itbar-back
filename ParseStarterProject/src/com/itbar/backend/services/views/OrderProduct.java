package com.itbar.backend.services.views;

import java.io.Serializable;

/**
 * Clase read-only que corresponde a un producto en una orden, esto significa que tiene la info del
 * producto agregado a un comentario y una cantidad
 *
 * Created by martin on 5/23/15.
 */
public class OrderProduct{

	private String objectId;
	private MenuItem menuItem;
	private Integer quantity;
	private String comment;

	private Order order;

	public OrderProduct() {

	}

	public OrderProduct(MenuItem menuItem, String comment, Integer quantity) {
		this.menuItem = menuItem;
		this.comment = comment;
		this.quantity = quantity;
	}

	public OrderProduct(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		OrderProduct orderProduct = (OrderProduct) o;

		return !(objectId != null ? !objectId.equals(orderProduct.objectId) : orderProduct.objectId != null);

	}

	@Override
	public int hashCode() {
		return objectId != null ? objectId.hashCode() : 0;
	}

	public String getObjectId() {
		return objectId;
	}
}
