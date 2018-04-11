package control.self.igor.dailywisdom.controller.abstraction;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonView;

import control.self.igor.dailywisdom.entity.Identifiable;
import control.self.igor.dailywisdom.exception.BadRequestException;
import control.self.igor.dailywisdom.json.View;
import control.self.igor.dailywisdom.model.api.EntityCounter;
import control.self.igor.dailywisdom.service.abstraction.CrudService;
import control.self.igor.dailywisdom.service.abstraction.SearchService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;

public abstract class AbstractCrudAndSearchController<Entity extends Identifiable, SearchCriteria>
	extends AbstractCrudController<Entity> {

    private SearchService<SearchCriteria, Entity> searchService;

    public AbstractCrudAndSearchController(CrudService<Entity> crudService,
	    SearchService<SearchCriteria, Entity> searchService, ValidationService validationService) {
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

    @GetMapping("/search/count")
    public EntityCounter countFoundEntities(@RequestBody SearchCriteria searchCriteria) {
	return new EntityCounter(searchService.countFoundEntities(searchCriteria));
    }

}
