package control.self.igor.dailywisdom.service.implementation;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;
import control.self.igor.dailywisdom.repository.abstraction.CategoryRepository;
import control.self.igor.dailywisdom.service.abstraction.SearchService;

@Service
public class CategorySearchService implements SearchService<SearchByNameCriteria, Category> {

    private static final int DEFAULT_PAGE_SIZE = 50;
    public static final Logger LOGGER = Logger.getLogger(CategorySearchService.class.getSimpleName());

    @Autowired
    private CategoryRepository repository;

    @Override
    public List<Category> searchEntities(Integer page, Integer size, SearchByNameCriteria searchCriteria) {
	Sort sort = new Sort(Direction.ASC, "id");
	String name = searchCriteria.getName();
	if (name != null && !name.isEmpty()) {
	    name = name.toLowerCase().trim();
	}
	LOGGER.info("querying with : " + searchCriteria);
	if ((page == null || page < 1) && (size == null || size < 1)) {
	    return repository.searchCategoriesByName(name);
	}
	if (page == null || page < 1) {
	    page = 1;
	}
	if (size == null || size < 1) {
	    size = DEFAULT_PAGE_SIZE;
	}
	return repository.searchCategoriesByName(name, PageRequest.of(page - 1, size, sort)).getContent();
    }

    @Override
    public long countFoundEntities(SearchByNameCriteria searchCriteria) {
	String name = searchCriteria.getName();
	if (name != null && !name.isEmpty()) {
	    name = name.toLowerCase().trim();
	}
	return repository.countFoundCategories(name);
    }
}
