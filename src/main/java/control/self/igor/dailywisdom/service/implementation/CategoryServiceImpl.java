package control.self.igor.dailywisdom.service.implementation;

import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.repository.abstraction.CategoryRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudService;
import control.self.igor.dailywisdom.service.abstraction.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl extends AbstractCrudService<Category> implements CategoryService {

    private static final Logger LOGGER = Logger.getLogger(CategoryServiceImpl.class.getSimpleName());

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
    public List<Category> searchEntities(Integer page, Integer size, String searchCriteria) {
	Sort sort = new Sort(Direction.ASC, "id");
	if (page == null || page < 1) {
	    return repository.searchCategoriesByName(searchCriteria);
	}

	if (size == null || size < 1) {
	    size = DEFAULT_PAGE_SIZE;
	}
	return repository.searchCategoriesByName(searchCriteria, PageRequest.of(page - 1, size, sort)).getContent();
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
    public long countFoundEntities(String searchCriteria) {
	return repository.countFoundCategories(searchCriteria);
    }

    @Override
    public List<Category> getEntities() {
	return getEntities(repository);
    }

}
