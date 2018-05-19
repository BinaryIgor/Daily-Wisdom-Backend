package control.self.igor.dailywisdom.service.abstraction;

import java.util.List;

import control.self.igor.dailywisdom.entity.Identifiable;

public interface SearchService<Entity extends Identifiable, SearchCriteria> {

    List<Entity> searchEntities(Integer page, Integer size, SearchCriteria searchCriteria);

    long countFoundEntities(SearchCriteria searchCriteria);
}