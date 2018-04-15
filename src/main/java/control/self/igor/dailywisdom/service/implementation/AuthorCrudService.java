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
    public byte[] getAuthorImage(long id) {
	return getAuthorRepository().getAuthorImage(id);
    }

}
