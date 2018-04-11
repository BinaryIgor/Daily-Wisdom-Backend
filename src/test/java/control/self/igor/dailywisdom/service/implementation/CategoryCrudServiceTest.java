package control.self.igor.dailywisdom.service.implementation;

import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudServiceTest;
import control.self.igor.dailywisdom.service.abstraction.CrudService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryCrudServiceTest extends AbstractCrudServiceTest<Category> {

    @TestConfiguration
    static class CategoryServiceTestConfiguration {

	@Bean
	public CategoryCrudService crudService() {
	    return new CategoryCrudService();
	}

    }

    @Autowired
    private CrudService<Category> crudService;

    public CategoryCrudServiceTest() {
	super(Category.class);
    }

    @Test
    public void properCreateTest() {
	properCreateTest(crudService);

    }

    @Test(expected = ValidationException.class)
    public void improperCreateTest() {
	improperCreateTest(crudService);
    }

    @Test
    public void getListTest() {
	getListTest(crudService);
    }

    @Test
    public void properUpdateTest() {
	properUpdateTest(crudService);
    }

    @Test(expected = ConstraintViolationException.class)
    public void improperUpdateTest() {
	improperUpdateTest(crudService);
    }

    @Test(expected = NoSuchElementException.class)
    public void nonExistingGetTest() {
	nonExistingGetTest(crudService);
    }

    @Test
    public void existingGetTest() {
	existingGetTest(crudService);
    }

    @Test
    public void duplicatedCreateTest() {
	duplicatedCreateTest(crudService);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void duplicatedUpdateTest() {
	duplicatedUpdateTest(crudService);

    }

    @Test(expected = NoSuchElementException.class)
    public void properDeleteTest() {
	properDeleteTest(crudService);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void improperDeleteTest() {
	improperDeleteTest(crudService);
    }

}
