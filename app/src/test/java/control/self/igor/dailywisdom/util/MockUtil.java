package control.self.igor.dailywisdom.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.entity.Identifiable;
import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.User;
import control.self.igor.dailywisdom.entity.UserRole;
import control.self.igor.dailywisdom.entity.UserRole.Role;
import control.self.igor.dailywisdom.model.QuoteSearchCriteria;
import control.self.igor.dailywisdom.model.SearchByNameCriteria;
import control.self.igor.dailywisdom.model.Token;
import control.self.igor.dailywisdom.security.SecurityConstants;
import control.self.igor.dailywisdom.security.SecurityConstants.TokenType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class MockUtil {

    private static final String IMAGE_PLACEHOLDER_FILE_PATH = "image_placeholder.jpg";
    private static final String MOCKED_IMAGES_FILE_PATH = "storage/images";

    public static byte[] createImage() {
	try (BufferedInputStream inputStream = new BufferedInputStream(
		MockUtil.class.getClassLoader().getResourceAsStream(IMAGE_PLACEHOLDER_FILE_PATH))) {
	    byte[] imageBytes = new byte[inputStream.available()];
	    inputStream.read(imageBytes, 0, imageBytes.length);
	    return imageBytes;
	} catch (IOException exception) {
	    exception.printStackTrace();
	    return null;
	}
    }

    public static <Entity extends Identifiable> SearchByNameCriteria createSearchCriteria(Class<Entity> entityClazz,
	    List<Entity> availableEntities, boolean emptyResult) {
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
	if (emptyResult) {
	    return new SearchByNameCriteria(name + name);
	}
	return new SearchByNameCriteria(name.substring(0, name.length() / 2));
    }

    public static QuoteSearchCriteria createSearchCritieria(List<Quote> availableQuotes, boolean emptyResult) {
	if (emptyResult) {
	    return new QuoteSearchCriteria("abcdef", null, null);
	}
	List<Long> categoriesIds = new ArrayList<>();
	Quote firstQuote = availableQuotes.get(0);
	Quote lastQuote = availableQuotes.get(availableQuotes.size() - 1);
	categoriesIds.add(firstQuote.getCategories().get(0).getId());
	categoriesIds.add(lastQuote.getCategories().get(0).getId());
	List<Long> authorsIds = new ArrayList<>();
	authorsIds.add(firstQuote.getAuthor().getId());
	authorsIds.add(lastQuote.getAuthor().getId());
	return new QuoteSearchCriteria(null, authorsIds, categoriesIds);
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
	quotes.add(new Quote("The question isn't who is going to let me; it's who is going to stop me.",
		createRandomCategories(existingCategories), author));
	author.setQuotes(quotes);
	return quotes;
    }

    public static List<Quote> createCategoryQuotes(Author author, Category category) {
	List<Category> categoryAsList = new ArrayList<>();
	categoryAsList.add(category);
	return createQuotes(author, categoryAsList);
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
	authors.add(new Author("Aristotle", MOCKED_IMAGES_FILE_PATH));
	authors.add(new Author("Ayn Rand", MOCKED_IMAGES_FILE_PATH));
	authors.add(new Author("Nietzsche", MOCKED_IMAGES_FILE_PATH));
	authors.add(new Author("Plato", MOCKED_IMAGES_FILE_PATH));
	authors.add(new Author("Ludwig von Mieses", MOCKED_IMAGES_FILE_PATH));
	return authors;
    }

    public static Author createAuthor() {
	Author author = new Author("Mock", MOCKED_IMAGES_FILE_PATH);
	author.setAuthorDescription(new AuthorDescription(author, "Mocked Description, very much so"));
	return author;
    }

    public static AuthorDescription createAuthorDescription(Author author) {
	return new AuthorDescription(author, "He was very wise, inteligent and pragmatic fellow.");
    }

    public static Quote createQuote() {
	return new Quote("Mocked, very long quote", createAuthor());
    }

    public static Quote createQuoteWithDependencies(Author author, List<Category> categories) {
	Quote quote = new Quote("Super, ultra, very long quote", author);
	quote.setCategories(categories);
	return quote;
    }

    public static Category createCategory() {
	return new Category("Mock");
    }

    public static User createGuestUser() {
	return new User("abc", "abc", UserRole.createGuest());
    }

    public static User createAdminUser() {
	return new User("abc", "abc", UserRole.createAdmin());
    }

    public static User createDifferentUser(User user) {
	return new User(user.getName() + "ab", user.getPassword() + "ab", user.getUserRole());
    }

    public static UsernamePasswordAuthenticationToken mockAuthentication(UserRole.Role role) {
	return new UsernamePasswordAuthenticationToken("super", null,
		Collections.singletonList(new SimpleGrantedAuthority(role.getTranslation())));
    }

    public static Token mockAdminAccessToken(String username) {
	return mockToken(TokenType.ACCESS_TOKEN, username, Role.ADMIN.getTranslation());
    }

    private static Token mockToken(TokenType tokenType, String username, String role) {
	long expirationDate = System.currentTimeMillis();
	if (TokenType.ACCESS_TOKEN == tokenType) {
	    expirationDate += SecurityConstants.ACCES_TOKEN_EXPIRATION_TIME;
	} else {
	    expirationDate += SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME;
	}
	String token = Jwts.builder().setSubject(username).claim(SecurityConstants.TOKEN_ROLE_CLAIM, role)
		.claim(SecurityConstants.TOKEN_TYPE_KEY, tokenType.getValue()).setExpiration(new Date(expirationDate))
		.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes()).compact();
	return new Token(token, expirationDate);
    }

    public static List<TestComparable> createComparableList() {
	List<TestComparable> testsComparables = new ArrayList<>();
	testsComparables.add(new TestComparable(1));
	testsComparables.add(new TestComparable(2));
	testsComparables.add(new TestComparable(4));
	testsComparables.add(new TestComparable(5));
	return testsComparables;
    }

    public static File createFile() {
	return new File(MOCKED_IMAGES_FILE_PATH + "/mock.jpg");
    }
}
