package control.self.igor.dailywisdom.service.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;
import control.self.igor.dailywisdom.service.abstraction.AbstractSearchServiceTest;
import control.self.igor.dailywisdom.service.abstraction.SearchService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class CategorySearchServiceTest extends AbstractSearchServiceTest<Category, SearchByNameCriteria> {

    @TestConfiguration
    static class CategoryServiceImplTestConfiguration {

	@Bean
	public CategorySearchService searchService() {
	    return new CategorySearchService();
	}
    }

    @Autowired
    private SearchService<SearchByNameCriteria, Category> searchService;

    public CategorySearchServiceTest() {
	super(Category.class, SearchByNameCriteria.class);
    }

    @Test
    public void searchTest() {
	searchTest(searchService);
    }
}
