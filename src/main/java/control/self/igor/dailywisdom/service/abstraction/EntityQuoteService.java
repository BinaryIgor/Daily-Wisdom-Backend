package control.self.igor.dailywisdom.service.abstraction;

import control.self.igor.dailywisdom.entity.Quote;

public interface EntityQuoteService extends RelationCrudService<Quote>, RelationSearchService<String, Quote> {

}
