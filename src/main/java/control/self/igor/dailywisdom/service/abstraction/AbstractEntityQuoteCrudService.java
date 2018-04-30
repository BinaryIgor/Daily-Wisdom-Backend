package control.self.igor.dailywisdom.service.abstraction;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.CrudRepository;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.QuoteOwner;
import control.self.igor.dailywisdom.repository.abstraction.CategoryRepository;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteRepository;
import control.self.igor.dailywisdom.repository.abstraction.SpecificationFactory;

public class AbstractEntityQuoteCrudService<Entity extends QuoteOwner> {

    private static final int DEFAULT_PAGE_SIZE = 50;
    private static final Logger LOGGER = Logger.getLogger(AbstractEntityQuoteCrudService.class.getSimpleName());
    private Class<Entity> entityClazz;
    private CrudRepository<Author, Long> authorRepository;
    private CategoryRepository categoryRepository;
    private EntityQuoteRepository entityQuoteRepository;
    private ComparatorService comparatorService;

    public AbstractEntityQuoteCrudService(Class<Entity> entityClazz, CrudRepository<Author, Long> authorRepository,
	    CategoryRepository categoryRepository, EntityQuoteRepository entityQuoteRepository,
	    ComparatorService comparatorService) {
	this.entityClazz = entityClazz;
	this.authorRepository = authorRepository;
	this.categoryRepository = categoryRepository;
	this.entityQuoteRepository = entityQuoteRepository;
	this.comparatorService = comparatorService;
    }

    public List<Quote> getQuotes(long id) {
	return entityQuoteRepository.findAll(SpecificationFactory.entityQuotes(id, entityClazz));
    }

    public List<Quote> getQuotes(long id, Integer page, Integer size) {
	System.out.println("Why we are searching?");
	Sort sort = new Sort(Direction.ASC, "id");
	if ((page == null || page < 1) && (size == null || size < 1)) {
	    return entityQuoteRepository.findAll(SpecificationFactory.entityQuotes(id, entityClazz));
	}
	if (page == null || page < 1) {
	    page = 1;
	}
	if (size == null || size < 1) {
	    size = DEFAULT_PAGE_SIZE;
	}
	return entityQuoteRepository
		.findAll(SpecificationFactory.entityQuotes(id, entityClazz), PageRequest.of(page - 1, size, sort))
		.getContent();
    }

    public Quote getQuote(long id, long quoteId) {
	return entityQuoteRepository.findOne(SpecificationFactory.entityQuote(id, entityClazz, quoteId)).get();
    }

    public long createQuote(long id, Quote quote) {
	if (!validateEntityQuote(id, quote)) {
	    return 0;
	}
	System.out.println("Saving quote with author = " + quote.getAuthor());
	return entityQuoteRepository.save(quote).getId();
    }

    public boolean updateQuote(long id, Quote quote) {
	if (!quoteExists(id, quote.getId())) {
	    LOGGER.info("Quote does not exist!(" + id + ", " + quote.getId());
	    return false;
	}
	if (!validateEntityQuote(id, quote)) {
	    LOGGER.info("Quote can not be validated!");
	    return false;
	}
	entityQuoteRepository.save(quote);
	return true;
    }

    public boolean deleteQuote(long id, long quoteId) {
	if (quoteExists(id, quoteId)) {
	    try {
		Quote quote = entityQuoteRepository.findById(quoteId).get();
		entityQuoteRepository.delete(quote);
	    } catch (NoSuchElementException exception) {
		LOGGER.log(Level.WARNING, exception.toString(), exception);
	    }
	} else {
	    boolean entityExists = false;
	    if (entityClazz.isAssignableFrom(Author.class)) {
		entityExists = authorRepository.existsById(id);
	    } else {
		entityExists = categoryRepository.existsById(id);
	    }
	    if (!entityExists) {
		throw new NoSuchElementException();
	    }
	}
	return true;
    }

    public boolean quoteExists(long id, long quoteId) {
	return entityQuoteRepository.count(SpecificationFactory.entityQuote(id, entityClazz, quoteId)) > 0;
    }

    public long countQuotes(long id) {
	return entityQuoteRepository.count(SpecificationFactory.entityQuotes(id, entityClazz));
    }

    private boolean validateEntityQuote(long id, Quote quote) {
	if (entityClazz.isAssignableFrom(Author.class)) {
	    return validateAuthorQuote(id, quote);
	} else {
	    return validateCategoryQuote(id, quote);
	}
    }

    private boolean validateAuthorQuote(long id, Quote quote) {
	try {
	    Author author = authorRepository.findById(id).get();
	    quote.setAuthor(author);
	    List<Category> categories = quote.getCategories();
	    if (categories == null || categories.isEmpty()) {
		return true;
	    }
	    List<Category> existingCategories = categoryRepository.findByIdIn(Category.getCategoriesIds(categories));
	    if (!comparatorService.compareLists(categories, existingCategories)) {
		return false;
	    }
	} catch (NoSuchElementException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    return false;
	}
	return true;
    }

    private boolean validateCategoryQuote(long id, Quote quote) {
	try {
	    Category category = categoryRepository.findById(id).get();
	    quote.addCategory(category);
	    Author author = quote.getAuthor();
	    if (author == null) {
		return false;
	    }
	    author = authorRepository.findById(author.getId()).get();
	    quote.setAuthor(author);
	} catch (NoSuchElementException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    return false;
	}
	return true;
    }

}
