package control.self.igor.dailywisdom.service.abstraction;

import java.util.List;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.QuoteOwner;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteRepository;
import control.self.igor.dailywisdom.repository.abstraction.QuoteSpecification;

public abstract class AbstractEntityQuoteSearchService<Entity extends QuoteOwner> extends AbstractQuoteSearchService {

    private Class<Entity> entityClazz;

    public AbstractEntityQuoteSearchService(Class<Entity> entityClazz, EntityQuoteRepository entityQuoteRepository) {
	super(entityQuoteRepository);
	this.entityClazz = entityClazz;
    }

    public List<Quote> searchQuotes(long id, Integer page, Integer size, String searchCriteria) {
	return searchQuotes(page, size, QuoteSpecification.searchEntityQuotes(id, entityClazz, searchCriteria));
    }

    public long countFoundQuotes(long id, String searchCriteria) {
	return countFoundQuotes(QuoteSpecification.searchEntityQuotes(id, entityClazz, searchCriteria));
    }

}
