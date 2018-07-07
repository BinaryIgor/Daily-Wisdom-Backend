package control.self.igor.dailywisdom.controller.frontend.data;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonView;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.QuoteOwner;
import control.self.igor.dailywisdom.exception.BadRequestException;
import control.self.igor.dailywisdom.json.View;
import control.self.igor.dailywisdom.model.EntityCounter;
import control.self.igor.dailywisdom.model.SearchByContentCriteria;
import control.self.igor.dailywisdom.model.SearchByNameCriteria;
import control.self.igor.dailywisdom.service.crud.EntityQuoteCrudService;
import control.self.igor.dailywisdom.service.search.EntityQuoteSearchService;
import control.self.igor.dailywisdom.service.validation.ValidationService;

public abstract class EntityQuoteCrudAndSearchController<Entity extends QuoteOwner>
	extends EntityQuoteCrudController<Entity> {

    private EntityQuoteSearchService<Entity> searchService;

    public EntityQuoteCrudAndSearchController(EntityQuoteCrudService<Entity> crudService,
	    EntityQuoteSearchService<Entity> searchService, ValidationService validationService) {
	super(crudService, validationService);
	this.searchService = searchService;
    }

    @PostMapping("/{id}/search")
    @JsonView(View.List.class)
    public List<Quote> searchRelatedEntities(@RequestParam(value = "page", required = false) Integer page,
	    @RequestParam(value = "size", required = false) Integer size, @PathVariable("id") long id,
	    @RequestBody SearchByContentCriteria searchCriteria) {
	if (id > 0 && validationService.validatePageRequest(page, size)) {
	    return searchService.searchQuotes(id, page, size, searchCriteria.getContent());
	}
	throw new BadRequestException();
    }

    @PostMapping("/{id}/search/count")
    public EntityCounter countFoundRelatedEntities(@PathVariable("id") long id, SearchByNameCriteria searchCriteria) {
	if (id < 1) {
	    throw new BadRequestException();
	}
	return new EntityCounter(searchService.countFoundQuotes(id, searchCriteria.getName()));
    }

}
