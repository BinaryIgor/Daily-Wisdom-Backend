package control.self.igor.dailywisdom.service.implementation;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.model.QuoteSearchCriteria;
import control.self.igor.dailywisdom.repository.QuoteRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractSearchServiceTest;
import control.self.igor.dailywisdom.service.search.QuoteSearchService;
import control.self.igor.dailywisdom.service.search.SearchService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class QuoteSearchServiceTest extends AbstractSearchServiceTest<Quote, QuoteSearchCriteria> {

    @TestConfiguration
    static class AuthorSearchServiceTestConfiguration {

	@Autowired
	private QuoteRepository repository;

	@Bean
	public SearchService<Quote, QuoteSearchCriteria> searchService() {
	    return new QuoteSearchService(repository);
	}
    }

    public QuoteSearchServiceTest() {
	super(Quote.class, QuoteSearchCriteria.class);
    }
}
