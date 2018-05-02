package control.self.igor.dailywisdom.service.implementation;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.repository.abstraction.AuthorRepository;
import control.self.igor.dailywisdom.repository.abstraction.CategoryRepository;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteCrudService;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteCrudServiceTest;
import control.self.igor.dailywisdom.service.abstraction.ComparatorService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorQuoteCrudServiceTest extends AbstractEntityQuoteCrudServiceTest<Author> {

    @TestConfiguration
    static class AuthorQuoteCrudServiceTestConfiguration {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private EntityQuoteRepository entityQuoteRepository;

	@Bean
	public ComparatorService comparatorService() {
	    return new ComparatorServiceImpl();
	}

	@Bean
	public AbstractEntityQuoteCrudService<Author> crudService() {
	    return new AuthorQuoteCrudService(authorRepository, categoryRepository, entityQuoteRepository,
		    comparatorService());
	}

    }

    public AuthorQuoteCrudServiceTest() {
	super(Author.class);
    }

}
