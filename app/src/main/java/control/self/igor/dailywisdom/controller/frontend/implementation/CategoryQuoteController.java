package control.self.igor.dailywisdom.controller.frontend.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import control.self.igor.dailywisdom.controller.frontend.abstraction.AbstractEntityQuoteCrudAndSearchController;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteCrudService;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteSearchService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;

@RestController
@RequestMapping("/category/quote")
public class CategoryQuoteController extends AbstractEntityQuoteCrudAndSearchController<Category> {

    @Autowired
    public CategoryQuoteController(AbstractEntityQuoteCrudService<Category> crudService,
	    AbstractEntityQuoteSearchService<Category> searchService, ValidationService validationService) {
	super(crudService, searchService, validationService);
    }

}
