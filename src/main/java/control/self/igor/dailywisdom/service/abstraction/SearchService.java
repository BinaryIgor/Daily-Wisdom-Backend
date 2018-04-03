package control.self.igor.dailywisdom.service.abstraction;

import java.util.List;

public interface SearchService<SearchCriteria, Entity> {

    List<Entity> searchEntities(Integer page, Integer size, SearchCriteria searchCriteria);

    long countFoundEntities(SearchCriteria searchCriteria);
}