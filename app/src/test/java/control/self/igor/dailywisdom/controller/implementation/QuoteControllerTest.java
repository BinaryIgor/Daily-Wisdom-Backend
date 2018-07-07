package control.self.igor.dailywisdom.controller.implementation;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import control.self.igor.dailywisdom.controller.abstraction.CrudAndSearchControllerTest;
import control.self.igor.dailywisdom.controller.frontend.data.QuoteController;
import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.model.QuoteSearchCriteria;
import control.self.igor.dailywisdom.service.crud.QuoteCrudService;
import control.self.igor.dailywisdom.service.json.JsonService;
import control.self.igor.dailywisdom.service.json.JsonServiceImpl;
import control.self.igor.dailywisdom.service.search.QuoteSearchService;
import control.self.igor.dailywisdom.service.validation.ValidationService;
import control.self.igor.dailywisdom.service.validation.ValidationServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(QuoteController.class)
public class QuoteControllerTest extends CrudAndSearchControllerTest<Quote, QuoteSearchCriteria> {

    @TestConfiguration
    static class QuoteControllerTestConfiguration {

	@Autowired
	private ObjectMapper objectMapper;

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
	    return new JsonServiceImpl(objectMapper);
	}
    }

    public QuoteControllerTest() {
	this.baseUrl = "/quote";
	this.searchUrl = baseUrl + "/search";
	this.entityClazz = Quote.class;
	this.searchCriteriaClazz = QuoteSearchCriteria.class;
    }

}
