package control.self.igor.dailywisdom.service.implementation;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.repository.abstraction.QuoteRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudService;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudServiceTest;

@RunWith(SpringRunner.class)
@DataJpaTest
public class QuoteCrudServiceTest extends AbstractCrudServiceTest<Quote> {

    @TestConfiguration
    static class QuoteCrudServiceTestConfiguration {

	@Autowired
	private QuoteRepository quoteRepository;

	@Bean
	public AbstractCrudService<Quote> crudService() {
	    return new QuoteCrudService(quoteRepository);
	}

    }

    public QuoteCrudServiceTest() {
	super(Quote.class);
    }

}
