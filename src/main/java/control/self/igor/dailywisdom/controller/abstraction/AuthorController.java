package control.self.igor.dailywisdom.controller.abstraction;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;

public interface AuthorController extends CrudController<Author>, SearchController<SearchByNameCriteria, Author> {

    AuthorDescription getAuthorDescription(long id);
}
