package control.self.igor.dailywisdom.service.implementation;

import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.repository.abstraction.CategoryRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudService;
import control.self.igor.dailywisdom.service.abstraction.CrudService;

@Service
@Transactional
public class CategoryCrudService extends AbstractCrudService<Category> implements CrudService<Category> {

    private static final Logger LOGGER = Logger.getLogger(CategoryCrudService.class.getSimpleName());

    @Autowired
    private CategoryRepository repository;

    @Override
    public List<Category> getEntities(Integer page, Integer size) {
	return getEntities(repository, page, size);
    }

    @Override
    public long countEntities() {
	return countEntities(repository);
    }

    @Override
    public Category getEntity(long id) {
	return getEntity(repository, id);
    }

    @Override
    public void deleteEntity(long id) {
	deleteEntity(repository, id);
    }

    @Override
    public long createEntity(Category entity) {
	return createEntity(repository, entity);
    }

    @Override
    public boolean updateEntity(Category entity) {
	return updateEntity(repository, entity);
    }

    @Override
    public boolean exists(long id) {
	return exists(repository, id);
    }

    @Override
    public List<Category> getEntities() {
	return getEntities(repository);
    }

}
