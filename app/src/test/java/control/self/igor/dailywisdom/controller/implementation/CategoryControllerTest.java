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
import control.self.igor.dailywisdom.controller.frontend.data.CategoryController;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.model.SearchByNameCriteria;
import control.self.igor.dailywisdom.service.crud.CategoryCrudService;
import control.self.igor.dailywisdom.service.image.ImageService;
import control.self.igor.dailywisdom.service.image.ImageServiceImpl;
import control.self.igor.dailywisdom.service.json.JsonService;
import control.self.igor.dailywisdom.service.json.JsonServiceImpl;
import control.self.igor.dailywisdom.service.search.CategorySearchService;
import control.self.igor.dailywisdom.service.validation.ValidationService;
import control.self.igor.dailywisdom.service.validation.ValidationServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest extends CrudAndSearchControllerTest<Category, SearchByNameCriteria> {

    @TestConfiguration
    static class CategoryControllerTestConfiguration {

	@Autowired
	private ObjectMapper objectMapper;

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
	    return new JsonServiceImpl(objectMapper);
	}

    }

    public CategoryControllerTest() {
	super("/category", Category.class, SearchByNameCriteria.class);

    }

}
