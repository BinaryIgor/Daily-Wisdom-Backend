package control.self.igor.dailywisdom.service.crud;

import org.springframework.beans.factory.annotation.Autowired;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;
import control.self.igor.dailywisdom.repository.AuthorRepository;

public abstract class AbstractAuthorCrudService extends CrudService<Author> {

    @Autowired
    public AbstractAuthorCrudService(AuthorRepository repository) {
	super(repository);
    }

    public abstract AuthorDescription getAuthorDescription(long id);

    public abstract boolean saveAuthorDescription(long id, AuthorDescription authorDescription);

    public abstract String getImagePath(long id);

    public abstract boolean saveImagePath(long id, String imagePath);

    public AuthorRepository getAuthorRepository() {
	return (AuthorRepository) repository;
    }

}
