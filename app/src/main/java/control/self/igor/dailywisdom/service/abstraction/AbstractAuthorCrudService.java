package control.self.igor.dailywisdom.service.abstraction;

import org.springframework.beans.factory.annotation.Autowired;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;
import control.self.igor.dailywisdom.repository.abstraction.AuthorRepository;

public abstract class AbstractAuthorCrudService extends AbstractCrudService<Author> {

    @Autowired
    public AbstractAuthorCrudService(AuthorRepository repository) {
	super(repository);
    }

    public abstract AuthorDescription getAuthorDescription(long id);

    public abstract String getAuthorImagePath(long id);

    public AuthorRepository getAuthorRepository() {
	return (AuthorRepository) repository;
    }

}
