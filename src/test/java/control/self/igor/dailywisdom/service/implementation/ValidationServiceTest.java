package control.self.igor.dailywisdom.service.implementation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.service.abstraction.ValidationService;

@RunWith(SpringRunner.class)
public class ValidationServiceTest {

    @TestConfiguration
    static class ValidationServiceTestConfiguration {

	@Bean
	public ValidationService validationService() {
	    return new ValidationServiceImpl();
	}
    }

    @Autowired
    private ValidationService service;

    @Test
    public void properIdsTest() {
	long firstId = 1;
	long secondId = 2;
	assertTrue(service.validateIds(firstId, secondId));
    }

    @Test
    public void improperIdsTest() {
	long firstId = -3;
	long secondId = 4;
	assertFalse(service.validateIds(firstId, secondId));
	secondId = -5;
	assertFalse(service.validateIds(firstId, secondId));
	firstId = 45;
	assertFalse(service.validateIds(firstId, secondId));
    }

    @Test
    public void properPageRequestTest() {
	Integer page = 1;
	Integer size = 4;
	assertTrue(service.validatePageRequest(page, size));
	page = 3;
	size = null;
	assertTrue(service.validatePageRequest(page, size));
	page = null;
	size = 10;
	assertTrue(service.validatePageRequest(page, size));
	size = null;
	assertTrue(service.validatePageRequest(page, size));
    }

    @Test
    public void improperPageRequestTest() {
	Integer page = -1;
	Integer size = -1;
	assertFalse(service.validatePageRequest(page, size));
	page = 1;
	assertFalse(service.validatePageRequest(page, size));
	page = -11;
	size = null;
	assertFalse(service.validatePageRequest(page, size));
	page = null;
	size = -44;
	assertFalse(service.validatePageRequest(page, size));
	page = 1;
	size = -1;
	assertFalse(service.validatePageRequest(page, size));
    }

}
