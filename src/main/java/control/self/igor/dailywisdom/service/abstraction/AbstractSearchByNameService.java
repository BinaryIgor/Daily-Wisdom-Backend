package control.self.igor.dailywisdom.service.abstraction;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import control.self.igor.dailywisdom.entity.Searchable;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;
import control.self.igor.dailywisdom.repository.abstraction.SearchingByNameRepository;

public abstract class AbstractSearchByNameService<Entity extends Searchable>
	implements SearchService<Entity, SearchByNameCriteria> {

    private static final int DEFAULT_PAGE_SIZE = 50;
    public static final Logger LOGGER = Logger.getLogger(AbstractSearchByNameService.class.getSimpleName());
    private SearchingByNameRepository<Entity> repository;

    @Autowired
    public AbstractSearchByNameService(SearchingByNameRepository<Entity> repository) {
	this.repository = repository;
    }

    @Override
    public List<Entity> searchEntities(Integer page, Integer size, SearchByNameCriteria searchCriteria) {
	Sort sort = new Sort(Direction.ASC, "id");
	String name = searchCriteria.getName();
	if (name != null && !name.isEmpty()) {
	    name = name.toLowerCase().trim();
	}
	LOGGER.info("querying with : " + searchCriteria);
	if ((page == null || page < 1) && (size == null || size < 1)) {
	    return repository.searchEntities(name);
	}
	if (page == null || page < 1) {
	    page = 1;
	}
	if (size == null || size < 1) {
	    size = DEFAULT_PAGE_SIZE;
	}
	return repository.searchEntities(name, PageRequest.of(page - 1, size, sort)).getContent();
    }

    @Override
    public long countFoundEntities(SearchByNameCriteria searchCriteria) {
	String name = searchCriteria.getName();
	if (name != null && !name.isEmpty()) {
	    name = name.toLowerCase().trim();
	}
	return repository.countFoundEntities(name);
    }
}
