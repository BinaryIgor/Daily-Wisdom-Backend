package control.self.igor.dailywisdom.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.repository.abstraction.AuthorRepository;
import control.self.igor.dailywisdom.repository.abstraction.CategoryRepository;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteCrudService;
import control.self.igor.dailywisdom.service.abstraction.ComparatorService;

@Service
@Transactional
public class AuthorQuoteCrudService extends AbstractEntityQuoteCrudService<Author> {

    @Autowired
    public AuthorQuoteCrudService(AuthorRepository authorRepository, CategoryRepository categoryRepository,
	    EntityQuoteRepository entityQuoteRepository, ComparatorService comparatorService) {
	super(Author.class, authorRepository, categoryRepository, entityQuoteRepository, comparatorService);

    }

}
