package control.self.igor.dailywisdom.service.implementation;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;
import control.self.igor.dailywisdom.repository.abstraction.AuthorRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractSearchServiceTest;
import control.self.igor.dailywisdom.service.abstraction.SearchService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorSearchServiceTest extends AbstractSearchServiceTest<Author, SearchByNameCriteria> {

    @TestConfiguration
    static class AuthorSearchServiceTestConfiguration {

	@Autowired
	private AuthorRepository repository;

	@Bean
	public SearchService<Author, SearchByNameCriteria> searchService() {
	    return new AuthorSearchService(repository);
	}
    }

    public AuthorSearchServiceTest() {
	super(Author.class, SearchByNameCriteria.class);
    }
}
