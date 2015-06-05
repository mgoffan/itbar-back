package com.itbar.backend.services.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by martin on 5/23/15.
 */
public class Menu {

	private String name;

	List<Category> categories = new ArrayList<>();

	public Menu() {

	}

	public Menu(String name, List<Category> categories) {
		this.categories = categories;
		this.name = name;
	}

	public Menu(Category category) {
		categories.add(category);
	}

	public Menu(String name) {
		this.name = name;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public List<String> getCategoryNames() {
		List<String> names = new ArrayList<>();
		for (Category i : this.categories) {
			names.add(i.getName());
		}
		return names;
	}
}
