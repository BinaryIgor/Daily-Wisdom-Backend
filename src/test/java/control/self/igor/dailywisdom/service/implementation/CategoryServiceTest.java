package control.self.igor.dailywisdom.service.implementation;

import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudAndSearchServiceTest;
import control.self.igor.dailywisdom.service.abstraction.CategoryService;
import control.self.igor.dailywisdom.service.abstraction.CrudAndSearchServiceTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class CategoryServiceTest extends AbstractCrudAndSearchServiceTest<Category, String>
	implements CrudAndSearchServiceTest {

    @TestConfiguration
    static class CategoryServiceImplTestConfiguration {

	@Bean
	public CategoryService categoryService() {
	    return new CategoryServiceImpl();
	}
    }

    @Autowired
    private CategoryService service;

    public CategoryServiceTest() {
	super(Category.class);
    }

    @Test
    @Override
    public void properCreateTest() {
	properCreateTest(service);

    }

    @Test(expected = ValidationException.class)
    @Override
    public void improperCreateTest() {
	improperCreateTest(service);
    }

    @Test
    @Override
    public void getListTest() {
	getListTest(service);
    }

    @Test
    @Override
    public void properUpdateTest() {
	properUpdateTest(service);
    }

    @Test(expected = ConstraintViolationException.class)
    @Override
    public void improperUpdateTest() {
	improperUpdateTest(service);
    }

    @Test(expected = NoSuchElementException.class)
    @Override
    public void nonExistingGetTest() {
	nonExistingGetTest(service);
    }

    @Test
    @Override
    public void existingGetTest() {
	existingGetTest(service);
    }

    @Test
    @Override
    public void duplicatedCreateTest() {
	duplicatedCreateTest(service);
    }

    @Test(expected = DataIntegrityViolationException.class)
    @Override
    public void duplicatedUpdateTest() {
	duplicatedUpdateTest(service);

    }

    @Test(expected = NoSuchElementException.class)
    @Override
    public void properDeleteTest() {
	properDeleteTest(service);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Override
    public void improperDeleteTest() {
	improperDeleteTest(service);
    }

    @Test
    @Override
    public void searchTest() {
	searchTest(service, String.class);
    }

}
