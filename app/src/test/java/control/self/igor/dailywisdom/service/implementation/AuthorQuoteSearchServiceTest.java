package control.self.igor.dailywisdom.service.implementation;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteSearchService;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteSearchServiceTest;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorQuoteSearchServiceTest extends AbstractEntityQuoteSearchServiceTest<Author> {

    @TestConfiguration
    static class AuthorQuoteSearchServiceTestConfiguration {

	@Autowired
	private EntityQuoteRepository entityQuoteRepository;

	@Bean
	public AbstractEntityQuoteSearchService<Author> searchService() {
	    return new AuthorQuoteSearchService(entityQuoteRepository);
	}

    }

    public AuthorQuoteSearchServiceTest() {
	super(Author.class);
    }

}
