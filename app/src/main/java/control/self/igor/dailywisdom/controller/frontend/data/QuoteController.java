package control.self.igor.dailywisdom.controller.frontend.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import control.self.igor.dailywisdom.controller.frontend.data.CrudAndSearchController;
import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.model.QuoteSearchCriteria;
import control.self.igor.dailywisdom.service.crud.CrudService;
import control.self.igor.dailywisdom.service.search.SearchService;
import control.self.igor.dailywisdom.service.validation.ValidationService;

@RestController
@RequestMapping("/quote")
public class QuoteController extends CrudAndSearchController<Quote, QuoteSearchCriteria> {

    @Autowired
    public QuoteController(CrudService<Quote> crudService,
	    SearchService<Quote, QuoteSearchCriteria> searchService, ValidationService validationService) {
	super(crudService, searchService, validationService);
    }

}
