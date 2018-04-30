package control.self.igor.dailywisdom.controller.implementation;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.controller.abstraction.AbstractCrudAndSearchControllerTest;
import control.self.igor.dailywisdom.controller.frontend.implementation.QuoteController;
import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.model.search.QuoteSearchCriteria;
import control.self.igor.dailywisdom.service.abstraction.JsonService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;
import control.self.igor.dailywisdom.service.implementation.JsonServiceImpl;
import control.self.igor.dailywisdom.service.implementation.QuoteCrudService;
import control.self.igor.dailywisdom.service.implementation.QuoteSearchService;
import control.self.igor.dailywisdom.service.implementation.ValidationServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(QuoteController.class)
public class QuoteControllerTest extends AbstractCrudAndSearchControllerTest<Quote, QuoteSearchCriteria> {

    @TestConfiguration
    static class QuoteControllerTestConfiguration {

	@Bean
	public QuoteCrudService crudService() {
	    return Mockito.mock(QuoteCrudService.class);
	}

	@Bean
	public QuoteSearchService searchService() {
	    return Mockito.mock(QuoteSearchService.class);
	}

	@Bean
	public ValidationService validationService() {
	    return new ValidationServiceImpl();
	}

	@Bean
	public JsonService jsonService() {
	    return new JsonServiceImpl();
	}

    }

    public QuoteControllerTest() {
	super("/quote", Quote.class, QuoteSearchCriteria.class);

    }

}
