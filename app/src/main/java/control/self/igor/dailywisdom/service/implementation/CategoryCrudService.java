package control.self.igor.dailywisdom.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.repository.abstraction.CategoryRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudService;

@Service
@Transactional
public class CategoryCrudService extends AbstractCrudService<Category> {

    @Autowired
    public CategoryCrudService(CategoryRepository repository) {
	super(repository);
    }
}
