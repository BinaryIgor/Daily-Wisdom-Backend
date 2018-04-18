package control.self.igor.dailywisdom.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import control.self.igor.dailywisdom.controller.abstraction.AbstractCrudAndSearchController;
import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.model.search.QuoteSearchCriteria;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudService;
import control.self.igor.dailywisdom.service.abstraction.SearchService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;

@RestController
@RequestMapping("/quote")
public class QuoteContoller extends AbstractCrudAndSearchController<Quote, QuoteSearchCriteria> {

    @Autowired
    public QuoteContoller(AbstractCrudService<Quote> crudService,
	    SearchService<Quote, QuoteSearchCriteria> searchService, ValidationService validationService) {
	super(crudService, searchService, validationService);
    }

}
