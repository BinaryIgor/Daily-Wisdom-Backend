package control.self.igor.dailywisdom.service.implementation;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.repository.abstraction.AuthorRepository;
import control.self.igor.dailywisdom.repository.abstraction.CategoryRepository;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteCrudService;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteCrudServiceTest;
import control.self.igor.dailywisdom.service.abstraction.ComparatorService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryQuoteCrudServiceTest extends AbstractEntityQuoteCrudServiceTest<Category> {

    @TestConfiguration
    static class CategoryQuoteCrudServiceTestConfiguration {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private EntityQuoteRepository entityQuoteRepository;

	@Bean
	public ComparatorService comparatorService() {
	    return new ComparatorServiceImpl();
	}

	@Bean
	public AbstractEntityQuoteCrudService<Category> crudService() {
	    return new CategoryQuoteCrudService(categoryRepository, authorRepository, entityQuoteRepository,
		    comparatorService());
	}

    }

    public CategoryQuoteCrudServiceTest() {
	super(Category.class);
    }

}