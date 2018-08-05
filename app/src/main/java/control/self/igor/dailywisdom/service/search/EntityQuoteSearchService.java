package control.self.igor.dailywisdom.service.search;

import java.util.ArrayList;
import java.util.List;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.QuoteOwner;
import control.self.igor.dailywisdom.repository.EntityQuoteRepository;
import control.self.igor.dailywisdom.specification.SpecificationFactory;

public abstract class EntityQuoteSearchService<Entity extends QuoteOwner> extends AbstractSearchService<Quote> {

    private Class<Entity> entityClazz;

    public EntityQuoteSearchService(Class<Entity> entityClazz, EntityQuoteRepository entityQuoteRepository) {
	super(entityQuoteRepository, "id");
	this.entityClazz = entityClazz;
    }

    public List<Quote> searchQuotes(long id, Integer page, Integer size, String searchCriteria) {
	if (searchCriteria == null || searchCriteria.isEmpty()) {
	    return new ArrayList<>();
	}
	return searchEntities(page, size,
		SpecificationFactory.searchEntityQuotes(id, entityClazz, searchCriteria.toLowerCase()));
    }

    public long countFoundQuotes(long id, String searchCriteria) {
	if (searchCriteria == null || searchCriteria.isEmpty()) {
	    return 0;
	}
	return countFoundEntities(
		SpecificationFactory.searchEntityQuotes(id, entityClazz, searchCriteria.toLowerCase()));
    }

}
