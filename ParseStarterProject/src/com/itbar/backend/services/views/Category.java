package com.itbar.backend.services.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 5/23/15.
 */
public class Category implements Serializable{

	private String objectId;

	private String name;

	private List<MenuItem> items = new ArrayList<>();

	public Category() {

	}

	public Category(String name) {
		this.name = name;
	}

	public Category(String name, String objectId) {
		this.name = name;
		this.objectId = objectId;
	}

	public Category(String name, List<MenuItem> items) {
		this.name = name;
		this.items = items;
	}

	public List<MenuItem> getItems() {
		return items;
	}

	public void setItems(List<MenuItem> items) {
		this.items = items;
	}

	public void addItem(MenuItem item) {
		this.items.add(item);
	}

	public void addItems(List<MenuItem> items) {
		this.items.addAll(items);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Category category = (Category) o;

		return !(objectId != null ? !objectId.equals(category.objectId) : category.objectId != null);

	}

	@Override
	public int hashCode() {
		return objectId != null ? objectId.hashCode() : 0;
	}
}
