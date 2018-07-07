package control.self.igor.dailywisdom.service.implementation;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.repository.EntityQuoteRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteSearchServiceTest;
import control.self.igor.dailywisdom.service.search.EntityQuoteSearchService;
import control.self.igor.dailywisdom.service.search.CategoryQuoteSearchService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryQuoteSearchServiceTest extends AbstractEntityQuoteSearchServiceTest<Category> {

    @TestConfiguration
    static class CategoryQuoteSearchServiceTestConfiguration {

	@Autowired
	private EntityQuoteRepository entityQuoteRepository;

	@Bean
	public EntityQuoteSearchService<Category> searchService() {
	    return new CategoryQuoteSearchService(entityQuoteRepository);
	}

    }

    public CategoryQuoteSearchServiceTest() {
	super(Category.class);
    }

}
