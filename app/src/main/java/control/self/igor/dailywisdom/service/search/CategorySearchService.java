package control.self.igor.dailywisdom.service.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.repository.CategoryRepository;

@Service
public class CategorySearchService extends AbstractSearchByNameService<Category> {

    @Autowired
    public CategorySearchService(CategoryRepository repository) {
	super(repository, "name");
    }

}
