package control.self.igor.dailywisdom.service.abstraction;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.CrudRepository;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.QuoteOwner;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteRepository;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteSpecification;

public class AbstractEntityQuoteCrudService<Entity extends QuoteOwner> implements EntityQuoteCrudService<Entity> {

    private static final int DEFAULT_PAGE_SIZE = 50;
    private Class<Entity> entityClazz;
    private CrudRepository<Entity, Long> crudRepository;
    private EntityQuoteRepository entityQuoteRepository;

    public AbstractEntityQuoteCrudService(Class<Entity> entityClazz, CrudRepository<Entity, Long> crudRepository,
	    EntityQuoteRepository entityQuoteRepository) {
	this.entityClazz = entityClazz;
	this.crudRepository = crudRepository;
	this.entityQuoteRepository = entityQuoteRepository;
    }

    @Override
    public List<Quote> getQuotes(long id) {
	return entityQuoteRepository.findAll(EntityQuoteSpecification.entityQuotes(getEntity(id), entityClazz));
    }

    @Override
    public List<Quote> getQuotes(long id, Integer page, Integer size) {
	Sort sort = new Sort(Direction.ASC, "id");
	if ((page == null || page < 1) && (size == null || size < 1)) {
	    return entityQuoteRepository.findAll(EntityQuoteSpecification.entityQuotes(getEntity(id), entityClazz));
	}
	if (page == null || page < 1) {
	    page = 1;
	}
	if (size == null || size < 1) {
	    size = DEFAULT_PAGE_SIZE;
	}
	return entityQuoteRepository.findAll(EntityQuoteSpecification.entityQuotes(getEntity(id), entityClazz),
		PageRequest.of(page - 1, size, sort)).getContent();
    }

    @Override
    public Quote getQuote(long id, long quoteId) {
	return entityQuoteRepository.findOne(EntityQuoteSpecification.entityQuotes(getEntity(id), entityClazz)).get();
    }

    @Override
    public long createQuote(long id, Quote quote) {
	Entity entity = getEntity(id);
	if (entityClazz.isAssignableFrom(Category.class) && !quote.hasCategory(entity.getId())) {
	    quote.addCategory((Category) entity);
	} else if (entityClazz.isAssignableFrom(Author.class)) {
	    Author author = (Author) entity;
	    if (!author.equals(quote.getAuthor())) {
		quote.setAuthor(author);
	    }
	} else {
	    return 0;
	}
	return entityQuoteRepository.save(quote).getId();
    }

    @Override
    public boolean updateQuote(long id, Quote quote) {
	if (!quoteExists(id, quote.getId())) {
	    return false;
	}
	entityQuoteRepository.save(quote);
	return true;
    }

    @Override
    public boolean deleteQuote(long id, long quoteId) {
	Quote quote = entityQuoteRepository.findById(quoteId).get();
	if (quote == null || !quoteExists(id, quote.getId())) {
	    return false;
	}
	entityQuoteRepository.delete(quote);
	return true;
    }

    @Override
    public boolean quoteExists(long id, long quoteId) {
	return entityQuoteRepository
		.count(EntityQuoteSpecification.entityQuoteExists(getEntity(id), entityClazz, quoteId)) > 0;
    }

    @Override
    public long countQuotes(long id) {
	return entityQuoteRepository.count(EntityQuoteSpecification.entityQuotes(getEntity(id), entityClazz));
    }

    @Override
    public Entity getEntity(long id) {
	return crudRepository.findById(id).get();
    }

}
