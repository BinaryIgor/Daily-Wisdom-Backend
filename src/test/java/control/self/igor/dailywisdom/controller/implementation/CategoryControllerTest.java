package control.self.igor.dailywisdom.controller.implementation;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.controller.abstraction.AbstractCrudAndSearchControllerTest;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;
import control.self.igor.dailywisdom.service.implementation.CategoryCrudService;
import control.self.igor.dailywisdom.service.implementation.CategorySearchService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
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

    }

    public CategoryControllerTest() {
	super("/category", Category.class, SearchByNameCriteria.class);

    }

    // @Test
    // public void getListTest() throws Exception {
    // super.getListTest(crudService);
    // }
    //
    // @Test
    // public void getExistingEntityTest() throws Exception {
    // getExistingEntityTest(crudService);
    // }
    //
    // @Test
    // public void getNonExistingEntityTest() throws Exception {
    // getNonExistingEntityTest(crudService);
    // }
    //
    // @Test
    // public void countListTest() throws Exception {
    // countListTest(crudService);
    // }
    //
    // @Test
    // public void createProperEntityTest() throws Exception {
    // createProperEntityTest(crudService);
    // }
    //
    // @Test
    // public void createImproperEntityTest() throws Exception {
    // createImproperEntityTest(crudService);
    // }

}
