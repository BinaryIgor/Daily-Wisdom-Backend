package control.self.igor.dailywisdom.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.entity.Identifiable;
import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.QuoteOwner;
import control.self.igor.dailywisdom.model.search.QuoteSearchCriteria;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;

public class DataTestUtil {

    public static <SearchCriteria, Entity extends Identifiable> SearchCriteria createSearchCriteria(
	    Class<SearchCriteria> searchCriteriaClazz, Class<Entity> entityClazz, List<Entity> availableEntities,
	    boolean emptyResult) {
	if (searchCriteriaClazz.isAssignableFrom(SearchByNameCriteria.class)) {
	    return (SearchCriteria) MockUtil.createSearchCriteria(entityClazz, availableEntities, emptyResult);
	}
	if (searchCriteriaClazz.isAssignableFrom(QuoteSearchCriteria.class)
		&& entityClazz.isAssignableFrom(Quote.class)) {
	    return (SearchCriteria) MockUtil.createSearchCritieria((List<Quote>) availableEntities, emptyResult);
	}
	return null;
    }

    public static <SearchCriteria, Entity extends Identifiable> SearchCriteria createEmptySearchCriteria(
	    Class<SearchCriteria> searchCriteriaClazz, Class<Entity> entityClazz) {
	if (searchCriteriaClazz.isAssignableFrom(SearchByNameCriteria.class)) {
	    return (SearchCriteria) new SearchByNameCriteria("");
	}
	if (searchCriteriaClazz.isAssignableFrom(QuoteSearchCriteria.class)) {
	    return (SearchCriteria) new QuoteSearchCriteria(null, null, null);
	}
	return null;
    }

    public static <Entity extends Identifiable> List<Entity> insertEntities(TestEntityManager entityManager,
	    Class<Entity> clazz) {
	if (clazz.isAssignableFrom(Quote.class)) {
	    Author author = MockUtil.createAuthor();
	    List<Category> categories = MockUtil.createCategories();
	    author = entityManager.persist(author);
	    insertList(entityManager, categories);
	    List<Quote> quotes = MockUtil.createQuotes(author, categories);
	    insertList(entityManager, quotes);
	    return (List<Entity>) quotes;
	}
	List<Entity> entities = createEntities(clazz);
	if (entities == null || entities.isEmpty()) {
	    return null;
	}
	insertList(entityManager, entities);
	return entities;
    }

    public static <Entity extends Identifiable> List<Entity> createEntities(Class<Entity> clazz) {
	if (clazz.isAssignableFrom(Author.class)) {
	    return (List<Entity>) MockUtil.createAuthors();
	}
	if (clazz.isAssignableFrom(Category.class)) {
	    return (List<Entity>) MockUtil.createCategories();
	}
	if (clazz.isAssignableFrom(Quote.class)) {
	    return (List<Entity>) MockUtil.createQuotes(MockUtil.createAuthor(), MockUtil.createCategories());
	}
	return null;
    }

    public static <Entity extends Identifiable> Entity insertEntityWithDependency(TestEntityManager entityManager,
	    Class<Entity> clazz) {
	Author author = entityManager.persist(MockUtil.createAuthor());
	if (clazz.isAssignableFrom(Author.class)) {
	    List<Category> categories = MockUtil.createCategories();
	    insertList(entityManager, categories);
	    insertList(entityManager, MockUtil.createQuotes(author, categories));
	    return (Entity) entityManager.persistAndFlush(author);
	}
	if (clazz.isAssignableFrom(Category.class)) {
	    Category category = entityManager.persist(MockUtil.createCategory());
	    List<Quote> quotes = MockUtil.createCategoryQuotes(author, category);
	    category.setQuotes(quotes);
	    return (Entity) entityManager.persistAndFlush(category);
	}
	return null;
    }

    public static <Entity extends Identifiable> Entity insertEntity(TestEntityManager entityManager,
	    Class<Entity> clazz) {
	if (clazz.isAssignableFrom(Category.class)) {
	    return (Entity) entityManager.persistAndFlush(MockUtil.createCategory());
	}
	if (clazz.isAssignableFrom(Author.class)) {
	    return (Entity) entityManager.persistAndFlush(MockUtil.createAuthor());
	}
	if (clazz.isAssignableFrom(Quote.class)) {
	    Quote quote = MockUtil.createQuote();
	    entityManager.persist(quote.getAuthor());
	    return (Entity) entityManager.persistAndFlush(quote);
	}
	return null;
    }

    public static <Entity extends QuoteOwner> Quote insertQuote(TestEntityManager entityManager, Class<Entity> clazz,
	    Entity entity) {
	String content = "Java is the best programming language";
	if (clazz.isAssignableFrom(Category.class)) {
	    List<Category> categories = new ArrayList<>();
	    categories.add((Category) entity);
	    return entityManager.persistAndFlush(new Quote(content, categories, MockUtil.createAuthor()));
	}
	if (clazz.isAssignableFrom(Author.class)) {
	    return entityManager.persistAndFlush(new Quote(content, (Author) entity));
	}
	return null;
    }

    private static <Entity extends Identifiable> void insertList(TestEntityManager entityManager,
	    List<Entity> entities) {
	for (Entity item : entities) {
	    entityManager.persist(item);
	}
	entityManager.flush();
    }

    public static <Entity extends Identifiable> Entity createEntity(Class<Entity> clazz, boolean proper) {
	if (clazz.isAssignableFrom(Author.class)) {
	    return (Entity) (proper ? MockUtil.createAuthor() : new Author());
	}
	if (clazz.isAssignableFrom(Category.class)) {
	    return (Entity) (proper ? MockUtil.createCategory() : new Category());
	}
	if (clazz.isAssignableFrom(Quote.class)) {
	    return (Entity) (proper ? MockUtil.createQuote() : new Quote());
	}
	return null;
    }

    public static <Entity extends Identifiable> void likenEntities(Class<Entity> clazz, Entity entity,
	    Entity toLikenEntity) {
	if (clazz.isAssignableFrom(Author.class)) {
	    Author.class.cast(toLikenEntity).setName(Author.class.cast(entity).getName());
	} else if (clazz.isAssignableFrom(Category.class)) {
	    Category.class.cast(toLikenEntity).setName(Category.class.cast(entity).getName());
	} else if (clazz.isAssignableFrom(Quote.class)) {
	    Quote.class.cast(toLikenEntity).setContent(Quote.class.cast(entity).getContent());
	}
    }

    public static <Entity extends Identifiable> void changeEntity(Entity entity, Class<Entity> clazz, boolean proper) {
	if (clazz.isAssignableFrom(Author.class)) {
	    Author author = (Author) entity;
	    String name = proper ? (author.getName() + "abc") : null;
	    author.setName(name);
	    author.setImagePath(null);
	}
	if (clazz.isAssignableFrom(Category.class)) {
	    Category category = (Category) entity;
	    String name = proper ? (category.getName() + "def") : null;
	    category.setName(name);
	}
	if (clazz.isAssignableFrom(Quote.class)) {
	    Quote quote = (Quote) entity;
	    String content = proper ? (quote.getContent() + "ghijkl") : null;
	    quote.setContent(content);
	}
    }

}
