package control.self.igor.dailywisdom.model.api;

import java.util.List;

import control.self.igor.dailywisdom.entity.Category;

public class Categories {

	private List<Category> categories;

	public Categories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Category> getCategories() {
		return categories;
	}

}
