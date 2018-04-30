package control.self.igor.dailywisdom.controller.frontend.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import control.self.igor.dailywisdom.controller.frontend.abstraction.AbstractCrudAndSearchController;
import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.model.search.QuoteSearchCriteria;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudService;
import control.self.igor.dailywisdom.service.abstraction.SearchService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;

@RestController
@RequestMapping("/quote")
public class QuoteController extends AbstractCrudAndSearchController<Quote, QuoteSearchCriteria> {

    @Autowired
    public QuoteController(AbstractCrudService<Quote> crudService,
	    SearchService<Quote, QuoteSearchCriteria> searchService, ValidationService validationService) {
	super(crudService, searchService, validationService);
    }

}
