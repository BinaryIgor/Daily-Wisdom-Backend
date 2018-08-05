package control.self.igor.dailywisdom.service.crud;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.repository.AuthorRepository;
import control.self.igor.dailywisdom.repository.CategoryRepository;
import control.self.igor.dailywisdom.repository.EntityQuoteRepository;

@Service
@Transactional
public class CategoryQuoteCrudService extends EntityQuoteCrudService<Category> {

    @Autowired
    public CategoryQuoteCrudService(CategoryRepository categoryRepository, AuthorRepository authorRepository,
	    EntityQuoteRepository entityQuoteRepository) {
	super(Category.class, authorRepository, categoryRepository, entityQuoteRepository);
    }

}
