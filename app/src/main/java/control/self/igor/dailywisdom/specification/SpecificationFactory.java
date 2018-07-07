package control.self.igor.dailywisdom.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.entity.Identifiable;
import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.QuoteOwner;
import control.self.igor.dailywisdom.model.QuoteSearchCriteria;

public class SpecificationFactory {

    public static <Entity extends Identifiable> Specification<Entity> searchByTextColumn(String columnName,
	    String columnValue) {
	return (root, query, criteriaBuilder) -> {
	    return criteriaBuilder.like(criteriaBuilder.lower(root.get(columnName)), "%" + columnValue + "%");
	};
    }

    public static <Entity extends Identifiable> Specification<Entity> searchById(long id) {
	return (root, query, criteriaBuilder) -> {
	    return criteriaBuilder.equal(root.get("id"), id);
	};
    }

    public static <Entity extends Identifiable> Specification<Entity> searchInIds(List<Long> ids) {
	return (root, query, criteriaBuilder) -> {
	    return root.get("id").in(ids);
	};
    }

    public static <Entity extends QuoteOwner> Specification<Quote> entitiesQuotes(List<Long> ids,
	    Class<Entity> entityClazz) {
	return (root, query, criteriaBuilder) -> {
	    if (entityClazz.isAssignableFrom(Author.class)) {
		return root.join("author").get("id").in(ids);
	    }
	    if (entityClazz.isAssignableFrom(Category.class)) {
		return root.join("categories").get("id").in(ids);
	    }
	    return null;
	};
    }

    public static <Entity extends QuoteOwner> Specification<Quote> entityQuotes(long id, Class<Entity> entityClazz) {
	return (root, query, criteriaBuilder) -> {
	    if (entityClazz.isAssignableFrom(Author.class)) {
		return criteriaBuilder.equal(root.join("author").get("id"), id);
	    }
	    if (entityClazz.isAssignableFrom(Category.class)) {
		return criteriaBuilder.equal(root.join("categories").get("id"), id);
	    }
	    return null;
	};
    }

    public static <Entity extends QuoteOwner> Specification<Quote> joinEntities(Class<Entity> entityClazz,
	    List<Long> ids) {
	return (root, query, criteriaBuilder) -> {
	    if (entityClazz.isAssignableFrom(Author.class)) {
		return root.join("author").get("id").in(ids);
	    }
	    if (entityClazz.isAssignableFrom(Category.class)) {
		return root.join("categories").get("id").in(ids);
	    }
	    return null;
	};
    }

    public static Specification<Quote> searchQuotes(QuoteSearchCriteria searchCriteria) {
	Specification<Quote> searchSpecification = null;
	List<Long> ids = searchCriteria.getAuthorsIds();
	if (ids != null && !ids.isEmpty()) {
	    searchSpecification = entitiesQuotes(ids, Author.class);
	}
	ids = searchCriteria.getCategoriesIds();
	if (ids != null && !ids.isEmpty()) {
	    searchSpecification = (searchSpecification == null) ? entitiesQuotes(ids, Category.class)
		    : searchSpecification.and(entitiesQuotes(ids, Category.class));
	}
	String content = searchCriteria.getContent();
	if (content != null && !content.isEmpty()) {
	    searchSpecification = (searchSpecification == null) ? searchByTextColumn("content", content)
		    : searchSpecification.and(searchByTextColumn("content", content));
	}
	return searchSpecification;
    }

    public static <Entity extends QuoteOwner> Specification<Quote> searchEntityQuotes(long id, Class<Entity> clazz,
	    String searchCriteria) {
	Specification<Quote> specification = entityQuotes(id, clazz);
	if (specification == null) {
	    return null;
	}
	return specification.and(searchByTextColumn("content", searchCriteria));
    }

    public static <Entity extends QuoteOwner> Specification<Quote> entityQuote(long id, Class<Entity> clazz,
	    long quoteId) {
	Specification<Quote> specification = entityQuotes(id, clazz);
	if (specification == null) {
	    return null;
	}
	return specification.and(searchById(quoteId));
    }
}
