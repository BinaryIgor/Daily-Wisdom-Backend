package control.self.igor.dailywisdom.util;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.entity.Identifiable;
import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;

public class DataTestUtil {

    private static final byte[] IMAGE_PLACE_HOLDER = new byte[100];
    private static final Logger LOGGER = Logger.getLogger(DataTestUtil.class.getSimpleName());

    public static <SearchCriteria, Entity extends Identifiable> SearchCriteria createSearchCriteria(
	    Class<SearchCriteria> searchCriteriaClazz, Class<Entity> entityClazz, List<Entity> availableEntities) {
	if (searchCriteriaClazz.isAssignableFrom(SearchByNameCriteria.class)) {
	    return (SearchCriteria) createNameSearchCriteria(entityClazz, availableEntities);
	}
	return null;
    }

    public static <SearchCriteria, Entity extends Identifiable> SearchByNameCriteria createNameSearchCriteria(
	    Class<Entity> entityClazz, List<Entity> availableEntities) {
	String name = null;
	if (entityClazz.isAssignableFrom(Author.class)) {
	    name = Author.class.cast(availableEntities.get(0)).getName();
	}
	if (entityClazz.isAssignableFrom(Category.class)) {
	    name = Category.class.cast(availableEntities.get(0)).getName();
	}
	if (name == null || name.isEmpty()) {
	    return null;
	}
	return new SearchByNameCriteria(name.substring(0, name.length() / 2));
    }

    public static <Entity extends Identifiable> List<Entity> insertEntities(TestEntityManager entityManager,
	    Class<Entity> clazz) {
	List<Entity> entities = createEntities(clazz);
	if (entities == null || entities.isEmpty()) {
	    return null;
	}
	insertList(entityManager, entities);
	return entities;
    }

    public static <Entity extends Identifiable> List<Entity> createEntities(Class<Entity> clazz) {
	if (clazz.isAssignableFrom(Author.class)) {
	    return (List<Entity>) createAuthors();
	}
	if (clazz.isAssignableFrom(Category.class)) {
	    return (List<Entity>) createCategories();
	}
	return null;
    }

    public static <Entity extends Identifiable> Entity insertEntityWithDependency(TestEntityManager entityManager,
	    Class<Entity> clazz) {
	if (clazz.isAssignableFrom(Category.class)) {
	    return (Entity) entityManager.persistAndFlush(createCategoryWithQuotes());
	}
	if (clazz.isAssignableFrom(Author.class)) {
	    return (Entity) entityManager.persistAndFlush(entityManager.persist(createAynRand(createCategories())));
	}
	return null;
    }

    public static <Entity extends Identifiable> Entity insertEntity(TestEntityManager entityManager,
	    Class<Entity> clazz) {
	if (clazz.isAssignableFrom(Category.class)) {
	    return (Entity) entityManager.persistAndFlush(createCategory());
	}
	if (clazz.isAssignableFrom(Author.class)) {
	    return (Entity) entityManager.persistAndFlush(createAuthor());
	}
	return null;
    }

    private static <Entity extends Identifiable> void insertList(TestEntityManager entityManager,
	    List<Entity> entities) {
	for (Entity item : entities) {
	    entityManager.persist(item);
	}
    }

    private static Author createAynRand(List<Category> existingCategories) {
	Author author = new Author("Ayn Rand", IMAGE_PLACE_HOLDER);
	List<Quote> quotes = new ArrayList<>();
	quotes.add(new Quote("Achievement of your happiness is the only moral purpose of your life,"
		+ " and that happiness, not pain or mindless self-indulgence, is the proof of your moral integrity, "
		+ "since it is the proof and the result of your loyalty to the achievement of your values.",
		createRandomCategories(existingCategories), author));
	quotes.add(new Quote("The question isn't who is going to let me; it's who is going to stop me.",
		createRandomCategories(existingCategories), author));
	quotes.add(new Quote(
		"Do not let your fire go out, spark by irreplaceable spark in the hopeless swamps of the not-quite, the not-yet, and the not-at-all."
			+ " Do not let the hero in your soul perish in lonely frustration for the life you deserved and have never been able to reach. "
			+ "The world you desire can be won. It exists.. it is real.. it is possible.. it's yours",
		createRandomCategories(existingCategories), author));
	author.setQuotes(quotes);
	return author;
    }

    private static Author createNietzsche(List<Category> existingCategories) {
	Author author = new Author("Nietzschce", IMAGE_PLACE_HOLDER);
	List<Quote> quotes = new ArrayList<>();
	quotes.add(new Quote("That which does not kill us makes us stronger.",
		createRandomCategories(existingCategories), author));
	quotes.add(new Quote(
		"And those who were seen dancing were thought to be insane by those who could not hear the music.",
		createRandomCategories(existingCategories), author));
	quotes.add(new Quote(
		"Whoever fights monsters should see to it that in the process he does not become a monster. "
			+ "And if you gaze long enough into an abyss, the abyss will gaze back into you.",
		createRandomCategories(existingCategories), author));
	author.setQuotes(quotes);
	return author;
    }

    private static Author createAristotle(List<Category> existingCategories) {
	Author author = new Author("Aristotle", IMAGE_PLACE_HOLDER);
	List<Quote> quotes = new ArrayList<>();
	quotes.add(new Quote(
		"That which is desirable on its own account and for the sake of knowing it is more of the nature "
			+ "of wisdom than that which is desirable on account of its results.",
		createRandomCategories(existingCategories), author));
	quotes.add(new Quote("In all things of nature there is something of the marvelous.",
		createRandomCategories(existingCategories), author));
	quotes.add(new Quote("Love is composed of single soul inhabiting two bodies.",
		createRandomCategories(existingCategories), author));
	author.setQuotes(quotes);
	return author;
    }

    public static List<Category> createRandomCategories(List<Category> existingCategories) {
	return existingCategories.subList(0, TestUtil.getRandomNumber(1, existingCategories.size()));
    }

    public static List<Category> createCategories() {
	List<Category> categories = new ArrayList<>();
	categories.add(new Category("Wisdom"));
	categories.add(new Category("Nature"));
	categories.add(new Category("Love"));
	categories.add(new Category("Evil"));
	categories.add(new Category("Beauty"));
	categories.add(new Category("Greatness"));
	categories.add(new Category("Strength"));
	categories.add(new Category("Individualism"));
	return categories;
    }

    public static List<Author> createAuthors() {
	List<Author> authors = new ArrayList<>();
	authors.add(new Author("Aristotle", IMAGE_PLACE_HOLDER));
	authors.add(new Author("Ayn Rand", IMAGE_PLACE_HOLDER));
	authors.add(new Author("Nietzsche", IMAGE_PLACE_HOLDER));
	authors.add(new Author("Plato", IMAGE_PLACE_HOLDER));
	authors.add(new Author("Ludwig von Mieses", IMAGE_PLACE_HOLDER));
	return authors;
    }

    private static Author createAuthor() {
	Author author = new Author("Mock", IMAGE_PLACE_HOLDER);
	author.setAuthorDescription(new AuthorDescription(author, "Mocked Description, very much so"));
	return author;
    }

    private static Quote createQuote() {
	return new Quote("Mocked, very long quote", createAuthor());
    }

    private static Category createCategory() {
	return new Category("Mock");
    }

    public static <Entity extends Identifiable> Entity createEntity(Class<Entity> clazz, boolean proper) {
	if (clazz.isAssignableFrom(Author.class)) {
	    return (Entity) (proper ? createAuthor() : new Author());
	}
	if (clazz.isAssignableFrom(Category.class)) {
	    return (Entity) (proper ? createCategory() : new Category());
	}
	if (clazz.isAssignableFrom(Quote.class)) {
	    return (Entity) (proper ? createQuote() : new Quote());
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

    public static Category createCategoryWithQuotes() {
	Category category = createCategory();
	List<Quote> quotes = new ArrayList<>();
	quotes.add(new Quote("Mocked, very long quote", new Author("AuthorA", IMAGE_PLACE_HOLDER)));
	quotes.add(new Quote("Mocked, even longer quote", new Author("AuthorB", IMAGE_PLACE_HOLDER)));
	quotes.add(new Quote("Mocked, even longer tha long quote", new Author("AuthorC", IMAGE_PLACE_HOLDER)));
	category.setQuotes(quotes);
	return category;
    }

}
