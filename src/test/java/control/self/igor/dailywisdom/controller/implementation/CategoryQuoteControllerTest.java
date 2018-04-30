package control.self.igor.dailywisdom.controller.implementation;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.controller.abstraction.AbstractEntityQuoteCrudControllerTest;
import control.self.igor.dailywisdom.controller.frontend.implementation.CategoryQuoteController;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteCrudService;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteSearchService;
import control.self.igor.dailywisdom.service.abstraction.JsonService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;
import control.self.igor.dailywisdom.service.implementation.CategoryQuoteCrudService;
import control.self.igor.dailywisdom.service.implementation.CategoryQuoteSearchService;
import control.self.igor.dailywisdom.service.implementation.JsonServiceImpl;
import control.self.igor.dailywisdom.service.implementation.ValidationServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryQuoteController.class)
public class CategoryQuoteControllerTest extends AbstractEntityQuoteCrudControllerTest<Category> {

    @TestConfiguration
    static class CategoryControllerTestConfiguration {

	@Bean
	public AbstractEntityQuoteCrudService<Category> crudService() {
	    return Mockito.mock(CategoryQuoteCrudService.class);
	}

	@Bean
	public AbstractEntityQuoteSearchService<Category> searchService() {
	    return Mockito.mock(CategoryQuoteSearchService.class);
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

    public CategoryQuoteControllerTest() {
	super("/category/quote/", Category.class);
    }

}
