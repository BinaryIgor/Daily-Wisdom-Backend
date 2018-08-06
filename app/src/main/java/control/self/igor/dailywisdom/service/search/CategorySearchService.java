package control.self.igor.dailywisdom.service.search;

import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.repository.CategoryRepository;

@Service
public class CategorySearchService extends AbstractSearchByNameService<Category> {

    public CategorySearchService(CategoryRepository repository) {
	super(repository, "name");
    }

}
