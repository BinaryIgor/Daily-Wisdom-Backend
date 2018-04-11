package control.self.igor.dailywisdom.service.implementation;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.repository.abstraction.CategoryRepository;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteCrudServiceTest;
import control.self.igor.dailywisdom.service.abstraction.EntityQuoteCrudService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class CategoryQuoteCrudServiceTest extends AbstractEntityQuoteCrudServiceTest<Category> {

    @TestConfiguration
    static class CategoryQuoteServiceImplTestConfiguration {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private EntityQuoteRepository entityQuoteRepository;

	@Bean
	public EntityQuoteCrudService<Category> crudService() {
	    return new CategoryQuoteCrudService(categoryRepository, entityQuoteRepository);
	}

    }

    public CategoryQuoteCrudServiceTest() {
	super(Category.class);
    }

}
