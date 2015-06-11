package com.itbar.backend.services.views;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by martin on 5/23/15.
 */
public class MenuItem implements Serializable {

	private String objectId;
	private String name;
	private Double price;
	private String description;
	/**
	 * Es una referencia que aca solo por Parse, no tiene sentido pensado en POO
	 */
	private Category category;

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MenuItem menuItem = (MenuItem) o;

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
