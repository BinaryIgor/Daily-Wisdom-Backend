package control.self.igor.dailywisdom.service.abstraction;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.PagingAndSortingRepository;

import control.self.igor.dailywisdom.entity.Identifiable;

public abstract class AbstractCrudService<Entity extends Identifiable> {

    protected static final int DEFAULT_PAGE_SIZE = 50;
    protected PagingAndSortingRepository<Entity, Long> repository;

    public AbstractCrudService(PagingAndSortingRepository<Entity, Long> repository) {
	this.repository = repository;
    }

    public List<Entity> getEntities() {
	return (List<Entity>) repository.findAll();
    }

    public List<Entity> getEntities(Integer page, Integer size) {
	Sort sort = new Sort(Direction.ASC, "id");
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
	return createEntity(entity) > 0;
    }

    public void deleteEntity(long id) {
	repository.deleteById(id);
    }

    public boolean exists(long id) {
	return repository.existsById(id);
    }

}
