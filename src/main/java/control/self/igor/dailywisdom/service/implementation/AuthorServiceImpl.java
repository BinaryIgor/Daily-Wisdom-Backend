package control.self.igor.dailywisdom.service.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;
import control.self.igor.dailywisdom.repository.abstraction.AuthorRepository;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudService;
import control.self.igor.dailywisdom.service.abstraction.AuthorService;

@Service
@Transactional
public class AuthorServiceImpl extends AbstractCrudService<Author> implements AuthorService {

    @Autowired
    private AuthorRepository repository;

    @Override
    public List<Author> getEntities() {
	return getEntities(repository);
    }

    @Override
    public List<Author> getEntities(Integer page, Integer size) {
	return getEntities(repository, page, size);
    }

    @Override
    public long countEntities() {
	return countEntities(repository);
    }

    @Override
    public Author getEntity(long id) {
	return getEntity(repository, id);
    }

    @Override
    public long createEntity(Author entity) {
	return createEntity(repository, entity);
    }

    @Override
    public boolean updateEntity(Author entity) {
	return updateEntity(repository, entity);
    }

    @Override
    public void deleteEntity(long id) {
	deleteEntity(repository, id);
    }

    @Override
    public boolean exists(long id) {
	return exists(repository, id);
    }

    @Override
    public List<Author> searchEntities(Integer page, Integer size, String searchCriteria) {
	Sort sort = new Sort(Direction.ASC, "id");
	if (page == null || page < 1) {
	    return repository.searchAuthorsByName(searchCriteria);
	}
	if (size == null || size < 1) {
	    size = DEFAULT_PAGE_SIZE;
	}
	return repository.searchAuthorsByName(searchCriteria, PageRequest.of(page - 1, size, sort)).getContent();
    }

    @Override
    public long countFoundEntities(String searchCriteria) {
	return repository.countFoundAuthors(searchCriteria);
    }

    @Override
    public AuthorDescription getAuthorDescription(long id) {
	return repository.getAuthorDescription(id);
    }

    @Override
    public byte[] getAuthorImage(long id) {
	return repository.getAuthorImage(id);
    }

}
