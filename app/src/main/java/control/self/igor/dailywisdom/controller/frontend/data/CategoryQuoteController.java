package control.self.igor.dailywisdom.controller.frontend.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import control.self.igor.dailywisdom.controller.frontend.data.EntityQuoteCrudAndSearchController;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.service.crud.EntityQuoteCrudService;
import control.self.igor.dailywisdom.service.search.EntityQuoteSearchService;
import control.self.igor.dailywisdom.service.validation.ValidationService;

@RestController
@RequestMapping("/category/quote")
public class CategoryQuoteController extends EntityQuoteCrudAndSearchController<Category> {

    @Autowired
    public CategoryQuoteController(EntityQuoteCrudService<Category> crudService,
	    EntityQuoteSearchService<Category> searchService, ValidationService validationService) {
	super(crudService, searchService, validationService);
    }

}
