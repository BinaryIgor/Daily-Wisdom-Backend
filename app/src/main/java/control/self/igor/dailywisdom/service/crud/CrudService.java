package control.self.igor.dailywisdom.service.crud;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.PagingAndSortingRepository;

import control.self.igor.dailywisdom.entity.Identifiable;

public abstract class CrudService<Entity extends Identifiable> {

    protected static final int DEFAULT_PAGE_SIZE = 50;
    protected static final Logger LOGGER = Logger.getLogger(CrudService.class.getSimpleName());
    private String toSortByColumnName;
    protected PagingAndSortingRepository<Entity, Long> repository;

    public CrudService(PagingAndSortingRepository<Entity, Long> repository, String toSortByColumnName) {
	this.repository = repository;
	this.toSortByColumnName = toSortByColumnName;
    }

    public List<Entity> getEntities() {
	return (List<Entity>) repository.findAll();
    }

    public List<Entity> getEntities(Integer page, Integer size) {
	Sort sort = new Sort(Direction.ASC, toSortByColumnName);
	if ((page == null || page < 1) && (size == null || size < 1)) {
	    return (List<Entity>) repository.findAll(sort);
	}
	if (page == null || page < 1) {
	    page = 1;
	}
	if (size == null || size < 1) {
	    size = DEFAULT_PAGE_SIZE;
	}
	return repository.findAll(PageRequest.of(page - 1, size, sort)).getContent();
    }

    public long countEntities() {
	return repository.count();
    }

    public Entity getEntity(long id) {
	return repository.findById(id).get();
    }

    public long createEntity(Entity entity) {
	return repository.save(entity).getId();
    }

    public boolean updateEntity(Entity entity) {
	if (!repository.existsById(entity.getId())) {
	    return false;
	}
	return repository.save(entity).getId() > 0;
    }

    public void deleteEntity(long id) {
	try {
	    Entity entity = repository.findById(id).get();
	    repository.delete(entity);
	} catch (NoSuchElementException exception) {
	    exception.printStackTrace();
	}
    }

    public boolean exists(long id) {
	return repository.existsById(id);
    }

}
