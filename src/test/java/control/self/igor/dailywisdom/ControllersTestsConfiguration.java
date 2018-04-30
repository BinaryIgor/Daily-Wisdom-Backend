package control.self.igor.dailywisdom;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteCrudService;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteSearchService;
import control.self.igor.dailywisdom.service.implementation.CategoryQuoteCrudService;
import control.self.igor.dailywisdom.service.implementation.CategoryQuoteSearchService;

public class ControllersTestsConfiguration {

    @Bean
    public AbstractEntityQuoteCrudService<Category> crudService() {
	return Mockito.mock(CategoryQuoteCrudService.class);
    }

    @Bean
    public AbstractEntityQuoteSearchService<Category> searchService() {
	return Mockito.mock(CategoryQuoteSearchService.class);
    }
}
