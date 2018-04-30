package control.self.igor.dailywisdom.controller.frontend.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import control.self.igor.dailywisdom.controller.frontend.abstraction.AbstractEntityQuoteCrudAndSearchController;
import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;
import control.self.igor.dailywisdom.service.implementation.AuthorQuoteCrudService;
import control.self.igor.dailywisdom.service.implementation.AuthorQuoteSearchService;

@RestController
@RequestMapping("/author/quote")
public class AuthorQuoteController extends AbstractEntityQuoteCrudAndSearchController<Author> {

    @Autowired
    public AuthorQuoteController(AuthorQuoteCrudService crudService, AuthorQuoteSearchService searchService,
	    ValidationService validationService) {
	super(crudService, searchService, validationService);
    }

}
