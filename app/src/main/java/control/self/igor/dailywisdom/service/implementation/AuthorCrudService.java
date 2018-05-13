package control.self.igor.dailywisdom.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.AuthorDescription;
import control.self.igor.dailywisdom.repository.abstraction.AuthorRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractAuthorCrudService;

@Service
@Transactional
public class AuthorCrudService extends AbstractAuthorCrudService {

    @Autowired
    public AuthorCrudService(AuthorRepository repository) {
	super(repository);
    }

    @Override
    public AuthorDescription getAuthorDescription(long id) {
	return getAuthorRepository().getAuthorDescription(id);
    }

    @Override
    public String getImagePath(long id) {
	return getAuthorRepository().getAuthorImagePath(id);
    }

    @Override
    public boolean saveAuthorDescription(long id, AuthorDescription authorDescription) {

	return false;
    }

    @Override
    public boolean saveImagePath(long id, String imagePath) {
	return getAuthorRepository().updateImagePath(id, imagePath) > 0;
    }

}
