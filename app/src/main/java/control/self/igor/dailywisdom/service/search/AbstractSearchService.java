package control.self.igor.dailywisdom.service.search;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import control.self.igor.dailywisdom.entity.Identifiable;

public abstract class AbstractSearchService<Entity extends Identifiable> {

    private static final int DEFAULT_PAGE_SIZE = 50;
    private JpaSpecificationExecutor<Entity> executor;
    private String toSortByColumnName;

    public AbstractSearchService(JpaSpecificationExecutor<Entity> executor, String toSortByColumnName) {
	this.executor = executor;
	this.toSortByColumnName = toSortByColumnName;
    }

    public List<Entity> searchEntities(Integer page, Integer size, Specification<Entity> specification) {
	Sort sort = new Sort(Direction.ASC, toSortByColumnName);
	if ((page == null || page < 1) && (size == null || size < 1)) {
	    return executor.findAll(specification);
	}
	page = (page == null || page < 1) ? 1 : page;
	size = (size == null || size < 1) ? DEFAULT_PAGE_SIZE : size;
	return executor.findAll(specification, PageRequest.of(page - 1, size, sort)).getContent();
    }

    public long countFoundEntities(Specification<Entity> specification) {
	return executor.count(specification);
    }

}
