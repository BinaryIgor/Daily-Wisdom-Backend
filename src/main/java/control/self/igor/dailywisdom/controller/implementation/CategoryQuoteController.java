package control.self.igor.dailywisdom.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import control.self.igor.dailywisdom.controller.abstraction.AbstractEntityQuoteCrudAndSearchController;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;
import control.self.igor.dailywisdom.service.implementation.CategoryQuoteCrudService;
import control.self.igor.dailywisdom.service.implementation.CategoryQuoteSearchService;

@Controller
@RestController
@RequestMapping("/category/quote")
public class CategoryQuoteController extends AbstractEntityQuoteCrudAndSearchController<Category> {

    @Autowired
    public CategoryQuoteController(CategoryQuoteCrudService crudService, CategoryQuoteSearchService searchService,
	    ValidationService validationService) {
	super(crudService, searchService, validationService);
    }

}
