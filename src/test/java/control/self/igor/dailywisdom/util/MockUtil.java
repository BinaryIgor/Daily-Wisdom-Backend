package control.self.igor.dailywisdom.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.entity.Quote;

public class MockUtil {

    private static final String IMAGE_PLACEHOLDER_FILE_PATH = "placeholder.jpg";

    public static byte[] createImage() {
	try (BufferedInputStream inputStream = new BufferedInputStream(
		MockUtil.class.getClassLoader().getResourceAsStream(IMAGE_PLACEHOLDER_FILE_PATH))) {
	    byte[] imageBytes = new byte[inputStream.available()];
	    inputStream.read(imageBytes, 0, imageBytes.length);
	    return imageBytes;
	} catch (IOException exception) {
	    return null;
	}
    }

    public static List<Quote> createQuotes(Author author, List<Category> existingCategories) {
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
	return quotes;
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
	byte[] image = createImage();
	authors.add(new Author("Aristotle", image));
	authors.add(new Author("Ayn Rand", image));
	authors.add(new Author("Nietzsche", image));
	authors.add(new Author("Plato", image));
	authors.add(new Author("Ludwig von Mieses", image));
	return authors;
    }

    public static Author createAuthor() {
	Author author = new Author("Mock", createImage());
	author.setAuthorDescription(new AuthorDescription(author, "Mocked Description, very much so"));
	return author;
    }

    public static Quote createQuote() {
	return new Quote("Mocked, very long quote", createAuthor());
    }

    public static Category createCategory() {
	return new Category("Mock");
    }

    public static Category createCategoryWithQuotes() {
	Category category = createCategory();
	List<Quote> quotes = new ArrayList<>();
	byte[] image = createImage();
	quotes.add(new Quote("Mocked, very long quote", new Author("AuthorA", image)));
	quotes.add(new Quote("Mocked, even longer quote", new Author("AuthorB", image)));
	quotes.add(new Quote("Mocked, even longer tha long quote", new Author("AuthorC", image)));
	category.setQuotes(quotes);
	return category;
    }

    public static Category createCategoryWithQuotes(Author author) {
	Category category = createCategory();
	List<Quote> quotes = new ArrayList<>();
	quotes.add(new Quote("Mocked, very long quote", author));
	quotes.add(new Quote("Mocked, even longer quote", author));
	quotes.add(new Quote("Mocked, even longer tha long quote", author));
	category.setQuotes(quotes);
	return category;
    }
}
