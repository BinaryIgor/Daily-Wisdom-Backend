package control.self.igor.dailywisdom.controller.implementation;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.controller.abstraction.AbstractCrudAndSearchControllerTest;
import control.self.igor.dailywisdom.controller.frontend.implementation.AuthorController;
import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;
import control.self.igor.dailywisdom.service.abstraction.AbstractAuthorCrudService;
import control.self.igor.dailywisdom.service.abstraction.ImageService;
import control.self.igor.dailywisdom.service.abstraction.JsonService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;
import control.self.igor.dailywisdom.service.implementation.AuthorSearchService;
import control.self.igor.dailywisdom.service.implementation.ImageServiceImpl;
import control.self.igor.dailywisdom.service.implementation.JsonServiceImpl;
import control.self.igor.dailywisdom.service.implementation.ValidationServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest extends AbstractCrudAndSearchControllerTest<Author, SearchByNameCriteria> {

    @TestConfiguration
    static class AuthorControllerTestConfiguration {

	@Bean
	public AbstractAuthorCrudService crudService() {
	    return Mockito.mock(AbstractAuthorCrudService.class);
	}

	@Bean
	public AuthorSearchService searchService() {
	    return Mockito.mock(AuthorSearchService.class);
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

    public AuthorControllerTest() {
	super("/author", Author.class, SearchByNameCriteria.class);

    }

}
