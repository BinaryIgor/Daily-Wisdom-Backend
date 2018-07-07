package control.self.igor.dailywisdom.service.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.repository.AuthorRepository;

@Service
public class AuthorSearchService extends AbstractSearchByNameService<Author> {

    @Autowired
    public AuthorSearchService(AuthorRepository repository) {
	super(repository);
    }

}
