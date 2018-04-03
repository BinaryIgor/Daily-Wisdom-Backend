
package control.self.igor.dailywisdom.controller.abstraction;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.model.search.QuoteSearchCriteria;

public interface QuoteController extends SearchController<QuoteSearchCriteria, Quote>, CrudController<Quote> {

}
