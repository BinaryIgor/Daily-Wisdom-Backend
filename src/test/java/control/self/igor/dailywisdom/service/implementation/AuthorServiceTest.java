package control.self.igor.dailywisdom.service.implementation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudAndSearchServiceTest;
import control.self.igor.dailywisdom.service.abstraction.AuthorService;
import control.self.igor.dailywisdom.util.DataTestUtil;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest
public class AuthorServiceTest extends AbstractCrudAndSearchServiceTest<Author, String> {

    @TestConfiguration
    static class AuthorServiceImplTestConfiguration {

	@Bean
	public AuthorService cauthorService() {
	    return new AuthorServiceImpl();
	}
    }

    @Autowired
    private AuthorService service;

    public AuthorServiceTest() {
	super(Author.class);
    }

    @Test
    public void properCreateTest() {
	properCreateTest(service);
    }

    @Test(expected = ConstraintViolationException.class)
    public void improperCreateTest() {
	improperCreateTest(service);
    }

    @Test
    public void getListTest() {
	getListTest(service);
    }

    @Test
    public void properUpdateTest() {
	properUpdateTest(service);
    }

    @Test(expected = ConstraintViolationException.class)
    public void improperUpdateTest() {
	improperUpdateTest(service);
    }

    @Test(expected = NoSuchElementException.class)
    public void nonExistingGetTest() {
	nonExistingGetTest(service);

    }

    @Test
    public void existingGetTest() {
	existingGetTest(service);
    }

    @Test
    public void duplicatedCreateTest() {
	duplicatedCreateTest(service);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void duplicatedUpdateTest() {
	duplicatedUpdateTest(service);
    }

    @Test(expected = NoSuchElementException.class)
    public void properDeleteTest() {
	properDeleteTest(service);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void improperDeleteTest() {
	improperDeleteTest(service);
    }

    @Test
    public void searchTest() {
	searchTest(service, String.class);
    }

    @Test
    public void getExistingAuthorPictureTest() {
	Author author = DataTestUtil.insertEntity(entityManager, Author.class);
	byte[] authorImage = service.getAuthorImage(author.getId());
	assertTrue(Arrays.equals(authorImage, author.getImage()));
    }

    @Test
    public void getNonExistingAuthorPictureTest() {
	byte[] authorImage = service.getAuthorImage(NON_EXISTING_ENTITY_ID);
	assertFalse(authorImage != null && authorImage.length > 0);
    }

    @Test
    public void getExistingAuthorDescriptionTest() {
	Author author = DataTestUtil.insertEntity(entityManager, Author.class);
	AuthorDescription description = service.getAuthorDescription(author.getId());
	assertTrue(author.getAuthorDescription().equals(description));
    }

    @Test
    public void getNonExistingAuthorDescriptionTest() {
	assertNull(service.getAuthorDescription(NON_EXISTING_ENTITY_ID));
    }
}
