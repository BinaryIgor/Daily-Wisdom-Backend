package control.self.igor.dailywisdom.service.abstraction;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.CrudRepository;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.QuoteOwner;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteRepository;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteSpecification;

public abstract class AbstractEntityQuoteSearchService<Entity extends QuoteOwner> implements EntityQuoteSearchService {

    private static final int DEFAULT_PAGE_SIZE = 50;
    private Class<Entity> entityClazz;
    private CrudRepository<Entity, Long> crudRepository;
    private EntityQuoteRepository entityQuoteRepository;

    public AbstractEntityQuoteSearchService(Class<Entity> entityClazz, CrudRepository<Entity, Long> crudRepository,
	    EntityQuoteRepository entityQuoteRepository) {
	this.entityClazz = entityClazz;
	this.crudRepository = crudRepository;
	this.entityQuoteRepository = entityQuoteRepository;
    }

    @Override
    public List<Quote> searchQuotes(long id, Integer page, Integer size, String searchCriteria) {
	Sort sort = new Sort(Direction.ASC, "id");
	if (page == null || page < 1) {
	    return entityQuoteRepository
		    .findAll(EntityQuoteSpecification.searchEntityQuotes(getEntity(id), entityClazz, searchCriteria));
	}

	if (size == null || size < 1) {
	    size = DEFAULT_PAGE_SIZE;
	}
	return entityQuoteRepository
		.findAll(EntityQuoteSpecification.searchEntityQuotes(getEntity(id), entityClazz, searchCriteria),
			PageRequest.of(page - 1, size, sort))
		.getContent();
    }

    @Override
    public long countFoundQuotes(long id, String searchCriteria) {
	return entityQuoteRepository
		.count(EntityQuoteSpecification.searchEntityQuotes(getEntity(id), entityClazz, searchCriteria));
    }

    public Entity getEntity(long id) {
	return crudRepository.findById(id).get();
    }
}
