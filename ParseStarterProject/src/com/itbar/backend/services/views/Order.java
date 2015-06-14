package com.itbar.backend.services.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable{

	private List<OrderProduct> items = null;

	private User buyer;

	private String comment;

	private String horario;

	private String paymentType;

	private String status;

	private double total;

	private String objectId;

	public Order(){
		items = new ArrayList<>();
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public User getBuyer() {
		return buyer;
	}

	public List<OrderProduct> getItems() {
		return items;
	}

	public void setItems(List<OrderProduct> items) {
		this.items = items;
	}

	public void addItem(OrderProduct item) {
		item.setOrder(this);
		this.items.add(item);
	}

	public void removeItem(OrderProduct item) {
		this.items.remove(item);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
