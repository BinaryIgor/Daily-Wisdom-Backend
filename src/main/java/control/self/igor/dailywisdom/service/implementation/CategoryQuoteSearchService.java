package control.self.igor.dailywisdom.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.repository.abstraction.CategoryRepository;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteSearchService;

@Service
@Transactional
public class CategoryQuoteSearchService extends AbstractEntityQuoteSearchService<Category> {

    @Autowired
    public CategoryQuoteSearchService(CategoryRepository crudRepository, EntityQuoteRepository entityQuoteRepository) {
	super(Category.class, crudRepository, entityQuoteRepository);
    }

}
