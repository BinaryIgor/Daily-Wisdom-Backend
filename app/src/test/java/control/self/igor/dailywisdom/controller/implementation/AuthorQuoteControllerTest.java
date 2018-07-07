package control.self.igor.dailywisdom.controller.implementation;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import control.self.igor.dailywisdom.controller.abstraction.EntityQuoteCrudControllerTest;
import control.self.igor.dailywisdom.controller.frontend.data.AuthorQuoteController;
import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.service.crud.AuthorQuoteCrudService;
import control.self.igor.dailywisdom.service.crud.EntityQuoteCrudService;
import control.self.igor.dailywisdom.service.json.JsonService;
import control.self.igor.dailywisdom.service.json.JsonServiceImpl;
import control.self.igor.dailywisdom.service.search.AuthorQuoteSearchService;
import control.self.igor.dailywisdom.service.search.EntityQuoteSearchService;
import control.self.igor.dailywisdom.service.validation.ValidationService;
import control.self.igor.dailywisdom.service.validation.ValidationServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorQuoteController.class)
public class AuthorQuoteControllerTest extends EntityQuoteCrudControllerTest<Author> {

    @TestConfiguration
    static class AuthorControllerTestConfiguration {

	@Autowired
	private ObjectMapper objectMapper;

	@Bean
	public EntityQuoteCrudService<Author> crudService() {
	    return Mockito.mock(AuthorQuoteCrudService.class);
	}

	@Bean
	public EntityQuoteSearchService<Author> searchService() {
	    return Mockito.mock(AuthorQuoteSearchService.class);
	}

	@Bean
	public ValidationService validationService() {
	    return new ValidationServiceImpl();
	}

	@Bean
	public JsonService jsonService() {
	    return new JsonServiceImpl(objectMapper);
	}

    }

    public AuthorQuoteControllerTest() {
	this.baseUrl = "/author/quote/";
	this.entityClazz = Author.class;
    }

}
