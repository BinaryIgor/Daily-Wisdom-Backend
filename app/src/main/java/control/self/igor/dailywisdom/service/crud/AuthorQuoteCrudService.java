package control.self.igor.dailywisdom.service.crud;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.repository.AuthorRepository;
import control.self.igor.dailywisdom.repository.CategoryRepository;
import control.self.igor.dailywisdom.repository.EntityQuoteRepository;
import control.self.igor.dailywisdom.service.comparator.ComparatorService;

@Service
@Transactional
public class AuthorQuoteCrudService extends EntityQuoteCrudService<Author> {

    @Autowired
    public AuthorQuoteCrudService(AuthorRepository authorRepository, CategoryRepository categoryRepository,
	    EntityQuoteRepository entityQuoteRepository, ComparatorService comparatorService) {
	super(Author.class, authorRepository, categoryRepository, entityQuoteRepository);
    }

}
