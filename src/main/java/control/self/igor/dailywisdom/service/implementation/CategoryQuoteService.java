package control.self.igor.dailywisdom.service.implementation;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.repository.abstraction.CategoryQuoteRepository;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteRepository;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteSpecification;
import control.self.igor.dailywisdom.service.abstraction.EntityQuoteService;

@Service
@Transactional
public class CategoryQuoteService implements EntityQuoteService {

    private static final Logger LOGGER = Logger.getLogger(CategoryQuoteService.class.getSimpleName());
    private static final int DEFAULT_PAGE_SIZE = 50;

    @Autowired
    private CategoryQuoteRepository repository;

    @Autowired
    private EntityQuoteRepository entityQuoteRepository;

    @Override
    public List<Quote> getRelatedEntities(long mainEntityId, Integer page, Integer size) {
	// Sort sort = new Sort(Direction.ASC, "id");
	// if (page == null || page < 1) {
	// return repository.getQuotes(relatedEntityId);
	// }
	//
	// if (size == null || size < 1) {
	// size = DEFAULT_PAGE_SIZE;
	// }
	// return repository.getQuotes(relatedEntityId, PageRequest.of(page - 1, size,
	// sort)).getContent();
	Category category = repository.getCategory(mainEntityId);
	return entityQuoteRepository.findAll(EntityQuoteSpecification.categoryQuotes(category));
    }

    @Override
    public Quote getRelatedEntity(long mainEntityId, long relatedEntityId) {
	try {
	    Quote quote = repository.getQuote(mainEntityId, relatedEntityId);
	    return quote;
	} catch (NoSuchElementException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    return null;
	}
    }

    @Override
    public List<Quote> searchRelatedEntities(long mainEntityId, Integer page, Integer size,
	    String relatedEntitySearchCriteria) {
	Sort sort = new Sort(Direction.ASC, "id");
	if (page == null || page < 1) {
	    return repository.searchQuotes(mainEntityId, relatedEntitySearchCriteria);
	}

	if (size == null || size < 1) {
	    size = DEFAULT_PAGE_SIZE;
	}
	return repository.searchQuotes(mainEntityId, relatedEntitySearchCriteria, PageRequest.of(page - 1, size, sort))
		.getContent();
    }

    @Override
    public long countFoundRelatedEntities(long mainEntityId, String relatedEntitySearchCriteria) {
	return repository.countFoundQuotes(mainEntityId, relatedEntitySearchCriteria);
    }

    @Override
    public long createRelatedEntity(long mainEntityId, Quote relatedEntity) {
	Category category = repository.getCategory(mainEntityId);
	if (category == null) {
	    return 0;
	}
	if (!relatedEntity.hasCategory(mainEntityId)) {
	    relatedEntity.addCategory(repository.getCategory(mainEntityId));
	}
	relatedEntity = repository.save(relatedEntity);
	return relatedEntity.getId();
    }

    @Override
    public boolean updateRelatedEntity(long mainEntityId, Quote relatedEntity) {
	if (!relatedEntityExists(mainEntityId, relatedEntity.getId())) {
	    return false;
	}
	repository.save(relatedEntity);
	return true;
    }

    @Override
    public boolean deleteRelatedEntity(long mainEntityId, long relatedEntityId) {
	Quote quote = repository.getQuote(mainEntityId, relatedEntityId);
	if (quote == null) {
	    LOGGER.info("Have not found related quote!");
	    return false;
	}
	repository.delete(quote);
	return true;
    }

    @Override
    public boolean relatedEntityExists(long mainEntityId, long relatedEntityId) {
	return repository.hasQuote(mainEntityId, relatedEntityId);
    }

    @Override
    public long countRelatedEntities(long id) {
	return repository.countQuotes(id);
    }

    @Override
    public List<Quote> getRelatedEntities(long id) {
	return repository.getQuotes(id);
    }

}
