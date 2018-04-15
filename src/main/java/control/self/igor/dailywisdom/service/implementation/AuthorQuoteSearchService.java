package control.self.igor.dailywisdom.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.repository.abstraction.EntityQuoteRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteSearchService;

@Service
@Transactional
public class AuthorQuoteSearchService extends AbstractEntityQuoteSearchService<Author> {

    @Autowired
    public AuthorQuoteSearchService(EntityQuoteRepository entityQuoteRepository) {
	super(Author.class, entityQuoteRepository);
    }

}
