package control.self.igor.dailywisdom.service.implementation;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;
import control.self.igor.dailywisdom.repository.abstraction.CategoryRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractSearchServiceTest;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategorySearchServiceTest extends AbstractSearchServiceTest<Category, SearchByNameCriteria> {

    @TestConfiguration
    static class CategorySearchServiceTestConfiguration {

	@Autowired
	private CategoryRepository repository;

	@Bean
	public CategorySearchService searchService() {
	    return new CategorySearchService(repository);
	}
    }

    public CategorySearchServiceTest() {
	super(Category.class, SearchByNameCriteria.class);
    }

}
