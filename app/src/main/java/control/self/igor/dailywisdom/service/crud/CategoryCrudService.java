package control.self.igor.dailywisdom.service.crud;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.repository.CategoryRepository;

@Service
@Transactional
public class CategoryCrudService extends CrudService<Category> {

    public CategoryCrudService(CategoryRepository repository) {
	super(repository, "name");
    }
}
