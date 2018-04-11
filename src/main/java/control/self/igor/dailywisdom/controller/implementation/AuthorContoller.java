package control.self.igor.dailywisdom.controller.implementation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import control.self.igor.dailywisdom.controller.abstraction.AbstractCrudController;
import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;
import control.self.igor.dailywisdom.model.api.EntityCounter;
import control.self.igor.dailywisdom.model.api.Response;
import control.self.igor.dailywisdom.service.abstraction.CrudService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;

@Controller
@RestController
@RequestMapping("/author")
public class AuthorContoller extends AbstractCrudController<Author> {

    public AuthorContoller(CrudService<Author> crudService, ValidationService validationService) {
	super(crudService, validationService);
	// TODO Auto-generated constructor stub
    }

    @Override
    public List<Author> getEntities(Integer page, Integer size) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public EntityCounter countEntities() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Author getEntity(long id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Response createEntity(Author entity) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Response updateEntity(long id, Author entity) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Response deleteEntity(long id) {
	// TODO Auto-generated method stub
	return null;
    }

    public AuthorDescription getAuthorDescription(long id) {
	// TODO Auto-generated method stub
	return null;
    }

    public byte[] getAuthorPicture(long id) {
	return null;
    }

}
