package control.self.igor.dailywisdom.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.repository.abstraction.AuthorRepository;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteCrudService;

@Service
@Transactional
public class AuthorQuoteCrudService extends AbstractEntityQuoteCrudService<Author> {

    @Autowired
    public AuthorQuoteCrudService(AuthorRepository authorRepository, EntityQuoteRepository entityQuoteRepository) {
	super(Author.class, authorRepository, entityQuoteRepository);

    }

}
