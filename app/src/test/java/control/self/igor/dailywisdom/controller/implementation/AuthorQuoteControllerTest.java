package control.self.igor.dailywisdom.controller.implementation;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.controller.abstraction.AbstractEntityQuoteCrudControllerTest;
import control.self.igor.dailywisdom.controller.frontend.implementation.AuthorQuoteController;
import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteCrudService;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteSearchService;
import control.self.igor.dailywisdom.service.abstraction.JsonService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;
import control.self.igor.dailywisdom.service.implementation.AuthorQuoteCrudService;
import control.self.igor.dailywisdom.service.implementation.AuthorQuoteSearchService;
import control.self.igor.dailywisdom.service.implementation.JsonServiceImpl;
import control.self.igor.dailywisdom.service.implementation.ValidationServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorQuoteController.class)
public class AuthorQuoteControllerTest extends AbstractEntityQuoteCrudControllerTest<Author> {

    @TestConfiguration
    static class AuthorControllerTestConfiguration {

	@Bean
	public AbstractEntityQuoteCrudService<Author> crudService() {
	    return Mockito.mock(AuthorQuoteCrudService.class);
	}

	@Bean
	public AbstractEntityQuoteSearchService<Author> searchService() {
	    return Mockito.mock(AuthorQuoteSearchService.class);
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

    public AuthorQuoteControllerTest() {
	super("/author/quote/", Author.class);
    }

}
