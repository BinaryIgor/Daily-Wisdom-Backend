package control.self.igor.dailywisdom.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.repository.abstraction.CategoryRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractSearchByNameService;

@Service
public class CategorySearchService extends AbstractSearchByNameService<Category> {

    @Autowired
    public CategorySearchService(CategoryRepository repository) {
	super(repository);
    }

}
