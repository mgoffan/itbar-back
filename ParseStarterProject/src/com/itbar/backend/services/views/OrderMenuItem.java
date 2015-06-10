package com.itbar.backend.services.views;

import java.io.Serializable;

/**
 * Clase read-only que corresponde a un producto en una orden, esto significa que tiene la info del
 * producto agregado a un comentario y una cantidad
 *
 * Created by martin on 5/23/15.
 */
public class OrderMenuItem {

	private String objectId;
	private MenuItem menuItem;
	private Integer quantity;
	private String comment;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		OrderMenuItem menuItem = (OrderMenuItem) o;

		return !(objectId != null ? !objectId.equals(menuItem.objectId) : menuItem.objectId != null);

	}

	@Override
	public int hashCode() {
		return objectId != null ? objectId.hashCode() : 0;
	}

	public String getObjectId() {
		return objectId;
	}
}
