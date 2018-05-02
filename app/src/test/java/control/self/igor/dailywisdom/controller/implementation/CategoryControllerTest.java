package control.self.igor.dailywisdom.controller.implementation;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.controller.abstraction.AbstractCrudAndSearchControllerTest;
import control.self.igor.dailywisdom.controller.frontend.implementation.CategoryController;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;
import control.self.igor.dailywisdom.service.abstraction.ImageService;
import control.self.igor.dailywisdom.service.abstraction.JsonService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;
import control.self.igor.dailywisdom.service.implementation.CategoryCrudService;
import control.self.igor.dailywisdom.service.implementation.CategorySearchService;
import control.self.igor.dailywisdom.service.implementation.ImageServiceImpl;
import control.self.igor.dailywisdom.service.implementation.JsonServiceImpl;
import control.self.igor.dailywisdom.service.implementation.ValidationServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest extends AbstractCrudAndSearchControllerTest<Category, SearchByNameCriteria> {

    @TestConfiguration
    static class CategoryControllerTestConfiguration {

	@Bean
	public CategoryCrudService crudService() {
	    return Mockito.mock(CategoryCrudService.class);
	}

	@Bean
	public CategorySearchService searchService() {
	    return Mockito.mock(CategorySearchService.class);
	}

	@Bean
	public ValidationService validationService() {
	    return new ValidationServiceImpl();
	}

	@Bean
	public ImageService imageService() {
	    return new ImageServiceImpl();
	}

	@Bean
	public JsonService jsonService() {
	    return new JsonServiceImpl();
	}

    }

    public CategoryControllerTest() {
	super("/category", Category.class, SearchByNameCriteria.class);

    }

}
