package control.self.igor.dailywisdom.controller.abstraction;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import control.self.igor.dailywisdom.model.api.EntityCounter;

public interface SearchController<SearchCriteria, Entity> {

    List<Entity> searchEntities(@RequestParam(value = "page", required = false) Integer page,
	    @RequestParam(value = "size", required = false) Integer size, SearchCriteria searchCriteria);

    EntityCounter countFoundEntities(SearchCriteria searchCriteria);
}