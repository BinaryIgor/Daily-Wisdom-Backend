package control.self.igor.dailywisdom.service.crud;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;
import control.self.igor.dailywisdom.repository.AuthorRepository;

public abstract class AbstractAuthorCrudService extends CrudService<Author> {

    public AbstractAuthorCrudService(AuthorRepository repository) {
	super(repository, "name");
    }

    public abstract AuthorDescription getAuthorDescription(long id);

    public abstract boolean saveAuthorDescription(long id, AuthorDescription authorDescription);

    public abstract String getImagePath(long id);

    public abstract boolean saveImagePath(long id, String imagePath);

    public AuthorRepository getAuthorRepository() {
	return (AuthorRepository) repository;
    }

}
