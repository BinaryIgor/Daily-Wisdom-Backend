package control.self.igor.dailywisdom.controller.abstraction;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;

public interface CategoryQuoteController
	extends RelationCrudController<Quote>, RelationSearchController<SearchByNameCriteria, Quote> {

}
