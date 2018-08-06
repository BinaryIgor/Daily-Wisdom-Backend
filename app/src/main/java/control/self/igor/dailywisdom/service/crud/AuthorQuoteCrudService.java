package control.self.igor.dailywisdom.service.crud;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.repository.AuthorRepository;
import control.self.igor.dailywisdom.repository.CategoryRepository;
import control.self.igor.dailywisdom.repository.EntityQuoteRepository;

@Service
@Transactional
public class AuthorQuoteCrudService extends EntityQuoteCrudService<Author> {

    public AuthorQuoteCrudService(AuthorRepository authorRepository, CategoryRepository categoryRepository,
	    EntityQuoteRepository entityQuoteRepository) {
	super(Author.class, authorRepository, categoryRepository, entityQuoteRepository);
    }

}
