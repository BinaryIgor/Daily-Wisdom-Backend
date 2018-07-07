package control.self.igor.dailywisdom.controller.frontend.data;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonView;

import control.self.igor.dailywisdom.entity.Identifiable;
import control.self.igor.dailywisdom.exception.BadRequestException;
import control.self.igor.dailywisdom.json.View;
import control.self.igor.dailywisdom.model.EntityCounter;
import control.self.igor.dailywisdom.service.crud.CrudService;
import control.self.igor.dailywisdom.service.search.SearchService;
import control.self.igor.dailywisdom.service.validation.ValidationService;

public abstract class CrudAndSearchController<Entity extends Identifiable, SearchCriteria>
	extends CrudController<Entity> {

    private SearchService<Entity, SearchCriteria> searchService;

    public CrudAndSearchController(CrudService<Entity> crudService, SearchService<Entity, SearchCriteria> searchService,
	    ValidationService validationService) {
	super(crudService, validationService);
	this.searchService = searchService;

    }

    @JsonView(View.List.class)
    @PostMapping("/search")
    public List<Entity> searchEntities(@RequestParam(value = "page", required = false) Integer page,
	    @RequestParam(value = "size", required = false) Integer size, @RequestBody SearchCriteria searchCriteria) {
	if (validationService.validatePageRequest(page, size)) {
	    return searchService.searchEntities(page, size, searchCriteria);
	}
	throw new BadRequestException();
    }

    @PostMapping("/search/count")
    public EntityCounter countFoundEntities(@RequestBody SearchCriteria searchCriteria) {
	return new EntityCounter(searchService.countFoundEntities(searchCriteria));
    }

}
