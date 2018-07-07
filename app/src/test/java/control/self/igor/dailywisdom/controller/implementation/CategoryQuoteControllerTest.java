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
import control.self.igor.dailywisdom.controller.frontend.data.CategoryQuoteController;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.service.crud.CategoryQuoteCrudService;
import control.self.igor.dailywisdom.service.crud.EntityQuoteCrudService;
import control.self.igor.dailywisdom.service.json.JsonService;
import control.self.igor.dailywisdom.service.json.JsonServiceImpl;
import control.self.igor.dailywisdom.service.search.CategoryQuoteSearchService;
import control.self.igor.dailywisdom.service.search.EntityQuoteSearchService;
import control.self.igor.dailywisdom.service.validation.ValidationService;
import control.self.igor.dailywisdom.service.validation.ValidationServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryQuoteController.class)
public class CategoryQuoteControllerTest extends EntityQuoteCrudControllerTest<Category> {

    @TestConfiguration
    static class CategoryControllerTestConfiguration {

	@Autowired
	private ObjectMapper objectMapper;

	@Bean
	public EntityQuoteCrudService<Category> crudService() {
	    return Mockito.mock(CategoryQuoteCrudService.class);
	}

	@Bean
	public EntityQuoteSearchService<Category> searchService() {
	    return Mockito.mock(CategoryQuoteSearchService.class);
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

    public CategoryQuoteControllerTest() {
	super("/category/quote/", Category.class);
    }

}
