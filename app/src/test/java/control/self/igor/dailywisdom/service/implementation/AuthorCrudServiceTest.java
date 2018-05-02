package control.self.igor.dailywisdom.service.implementation;

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
import control.self.igor.dailywisdom.repository.abstraction.AuthorRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudService;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudServiceTest;
import control.self.igor.dailywisdom.util.DataTestUtil;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorCrudServiceTest extends AbstractCrudServiceTest<Author> {

    @TestConfiguration
    static class AuthorCrudServiceTestConfiguration {

	@Autowired
	private AuthorRepository repository;

	@Bean
	public AbstractCrudService<Author> crudService() {
	    return new AuthorCrudService(repository);
	}

    }

    public AuthorCrudServiceTest() {
	super(Author.class);
    }

    @Test
    public void getExistingAuthorImagePathTest() {
	Author author = DataTestUtil.insertEntity(entityManager, Author.class);
	String authorImagePath = ((AuthorCrudService) crudService).getAuthorImagePath(author.getId());
	assertTrue(author.getImagePath().equals(authorImagePath));
    }

    @Test
    public void getNonExistingAuthorImagePathTest() {
	String authorImagePath = ((AuthorCrudService) crudService).getAuthorImagePath(NON_EXISTING_ENTITY_ID);
	assertFalse(authorImagePath != null && !authorImagePath.isEmpty());
    }

    @Test
    public void getExistingAuthorDescriptionTest() {
	Author author = DataTestUtil.insertEntity(entityManager, Author.class);
	AuthorDescription description = ((AuthorCrudService) crudService).getAuthorDescription(author.getId());
	assertTrue(author.getAuthorDescription().equals(description));
    }

    @Test
    public void getNonExistingAuthorDescriptionTest() {
	assertNull(((AuthorCrudService) crudService).getAuthorDescription(NON_EXISTING_ENTITY_ID));
    }
}
