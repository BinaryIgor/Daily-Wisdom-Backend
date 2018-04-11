package control.self.igor.dailywisdom.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.repository.abstraction.CategoryRepository;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteCrudService;

@Service
@Transactional
public class CategoryQuoteCrudService extends AbstractEntityQuoteCrudService<Category> {

    @Autowired
    public CategoryQuoteCrudService(CategoryRepository categoryRepository,
	    EntityQuoteRepository entityQuoteRepository) {
	super(Category.class, categoryRepository, entityQuoteRepository);

    }

}
