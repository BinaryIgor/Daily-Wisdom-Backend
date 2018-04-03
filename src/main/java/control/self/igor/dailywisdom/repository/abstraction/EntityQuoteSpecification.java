package control.self.igor.dailywisdom.repository.abstraction;

import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.QuoteOwner;

public class EntityQuoteSpecification {

    public static Specification<Quote> categoryQuotes(Category category) {
	return (root, query, criteriaBuilder) -> {
	    Join<Quote, Category> quoteCategories = root.join("categories");
	    return criteriaBuilder.equal(quoteCategories, category);
	};
    }

    public static Specification<Quote> authorQuotes(Author author) {
	return (root, query, criteriaBuilder) -> {
	    Join<Quote, Author> quoteAuthor = root.join("author");
	    return criteriaBuilder.equal(quoteAuthor, author);
	};
    }

    public static <EntityWithQuotes extends QuoteOwner> Specification<Quote> entityQuotes(EntityWithQuotes entity,
	    Class<EntityWithQuotes> clazz) {
	if (clazz.isAssignableFrom(Author.class)) {
	    return authorQuotes((Author) entity);
	}
	if (clazz.isAssignableFrom(Category.class)) {
	    return categoryQuotes((Category) entity);
	}
	return null;
    }
}
