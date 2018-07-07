package control.self.igor.dailywisdom.service.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;
import control.self.igor.dailywisdom.repository.AuthorDescriptionRepository;
import control.self.igor.dailywisdom.repository.AuthorRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudServiceTest;
import control.self.igor.dailywisdom.service.crud.CrudService;
import control.self.igor.dailywisdom.service.crud.AuthorCrudService;
import control.self.igor.dailywisdom.util.DataTestUtil;
import control.self.igor.dailywisdom.util.MockUtil;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorCrudServiceTest extends AbstractCrudServiceTest<Author> {

    @TestConfiguration
    static class AuthorCrudServiceTestConfiguration {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private AuthorDescriptionRepository authorDescriptionRepository;

	@Bean
	public CrudService<Author> crudService() {
	    return new AuthorCrudService(authorRepository, authorDescriptionRepository);
	}

    }

    public AuthorCrudServiceTest() {
	super(Author.class);
    }

    @Test
    public void getExistingAuthorImagePathTest() {
	Author author = DataTestUtil.insertEntity(entityManager, Author.class);
	String authorImagePath = getAuthorCrudService().getImagePath(author.getId());
	assertTrue(author.getImagePath().equals(authorImagePath));
    }

    @Test
    public void getNonExistingAuthorImagePathTest() {
	String authorImagePath = ((AuthorCrudService) crudService).getImagePath(NON_EXISTING_ENTITY_ID);
	assertFalse(authorImagePath != null && !authorImagePath.isEmpty());
    }

    @Test
    public void getExistingAuthorDescriptionTest() {
	Author author = DataTestUtil.insertEntity(entityManager, Author.class);
	AuthorDescription description = getAuthorCrudService().getAuthorDescription(author.getId());
	assertTrue(author.getAuthorDescription().equals(description));
    }

    @Test
    public void getNonExistingAuthorDescriptionTest() {
	assertNull(getAuthorCrudService().getAuthorDescription(NON_EXISTING_ENTITY_ID));
    }

    @Test
    public void createExisitingAuthorDescriptionTest() {
	Author author = DataTestUtil.insertEntity(entityManager, Author.class);
	AuthorDescription authorDescription = MockUtil.createAuthorDescription(author);
	assertTrue(getAuthorCrudService().saveAuthorDescription(author.getId(), authorDescription));
    }

    @Test
    public void saveNonExistingAuthorDescriptionTest() {
	AuthorDescription authorDescription = MockUtil.createAuthorDescription(new Author());
	assertFalse(getAuthorCrudService().saveAuthorDescription(NON_EXISTING_ENTITY_ID, authorDescription));
    }

    @Test
    public void properUpdateAuthorDescriptionTest() {
	updateAuthorDescriptionTest(true);
    }

    @Test
    public void improperUpdateAuthorDescriptionTest() {
	updateAuthorDescriptionTest(true);
    }

    private void updateAuthorDescriptionTest(boolean proper) {
	Author author = DataTestUtil.insertEntity(entityManager, Author.class);
	AuthorDescription authorDescription = author.getAuthorDescription();
	DataTestUtil.changeEntity(authorDescription, AuthorDescription.class, proper);
	long authorId = authorDescription.getAuthor().getId();
	if (proper) {
	    assertTrue(getAuthorCrudService().saveAuthorDescription(authorId, authorDescription));
	    assertEquals(getAuthorCrudService().getEntity(authorId).getAuthorDescription(), authorDescription);
	} else {
	    assertFalse(getAuthorCrudService().saveAuthorDescription(authorId, authorDescription));
	}
    }

    private AuthorCrudService getAuthorCrudService() {
	return (AuthorCrudService) crudService;
    }
}
