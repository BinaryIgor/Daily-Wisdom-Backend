package control.self.igor.dailywisdom.controller.abstraction;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;

public interface CategoryController extends CrudController<Category>, SearchController<SearchByNameCriteria, Category> {

}
