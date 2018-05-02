package control.self.igor.dailywisdom.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.repository.abstraction.AuthorRepository;
import control.self.igor.dailywisdom.repository.abstraction.CategoryRepository;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteCrudService;
import control.self.igor.dailywisdom.service.abstraction.ComparatorService;

@Service
@Transactional
public class CategoryQuoteCrudService extends AbstractEntityQuoteCrudService<Category> {

    @Autowired
    public CategoryQuoteCrudService(CategoryRepository categoryRepository, AuthorRepository authorRepository,
	    EntityQuoteRepository entityQuoteRepository, ComparatorService comparatorService) {
	super(Category.class, authorRepository, categoryRepository, entityQuoteRepository, comparatorService);

    }

}
