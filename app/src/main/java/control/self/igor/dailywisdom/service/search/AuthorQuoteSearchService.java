package control.self.igor.dailywisdom.service.search;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.repository.EntityQuoteRepository;

@Service
@Transactional
public class AuthorQuoteSearchService extends EntityQuoteSearchService<Author> {

    public AuthorQuoteSearchService(EntityQuoteRepository entityQuoteRepository) {
	super(Author.class, entityQuoteRepository);
    }

}
