package control.self.igor.dailywisdom.service.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteServiceTest;
import control.self.igor.dailywisdom.service.abstraction.EntityQuoteService;
import control.self.igor.dailywisdom.service.abstraction.EntityQuoteServiceTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class CategoryQuoteServiceTest extends AbstractEntityQuoteServiceTest<Category>
	implements EntityQuoteServiceTest {

    @TestConfiguration
    static class CategoryQuoteServiceImplTestConfiguration {

	@Bean
	public EntityQuoteService categoryQuoteService() {
	    return new CategoryQuoteService();
	}
    }

    @Autowired
    private EntityQuoteService service;

    @Test
    @Override
    public void existingEntityListTest() {
	existingEntityListTest(service);

    }

    public CategoryQuoteServiceTest() {
	super(Category.class);
    }

    @Test
    @Override
    public void nonExistingEntityListTest() {
	nonExistingEntityListTest(service);
    }

    @Test
    @Override
    public void searchExistingEntityQuotesTest() {
	searchExistingEntityQuotesTest(service);
    }

    @Test
    @Override
    public void searchNonExistingEntityQuotesTest() {
	searchNonExistingEntityQuotesTest(service);
    }

    @Test
    @Override
    public void createExistingEntityProperQuoteTest() {
	createExistingEntityProperQuoteTest(service);
    }

    @Test(expected = DataIntegrityViolationException.class)
    @Override
    public void createExistingEntityImproperQuoteTest() {
	createExistingEntityImproperQuoteTest(service);
    }

    @Override
    public void createNonExistingEntityQuoteTest() {
	createNonExistingEntityQuoteTest(service);

    }

    @Test
    @Override
    public void updateExistingEntityProperQuoteTest() {
	updateExistingEntityProperQuoteTest(service);
    }

    @Test(expected = DataIntegrityViolationException.class)
    @Override
    public void updateExistingEntityImproperQuoteTest() {
	updateExistingEntityImproperQuoteTest(service);
    }

    @Test
    @Override
    public void deleteExistingEntityQuoteTest() {
	deleteExistingEntityQuoteTest(service);
    }

    @Test
    @Override
    public void deleteNonExistingEntityQuoteTest() {
	deleteNonExistingEntityQuoteTest(service);
    }

    @Test
    @Override
    public void updateNonExistingEntityQuoteTest() {
	updateNonExistingEntityQuoteTest(service);
    }

}
