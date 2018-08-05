package control.self.igor.dailywisdom.controller.frontend.data;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonView;

import control.self.igor.dailywisdom.entity.Identifiable;
import control.self.igor.dailywisdom.entity.UserRole.Role;
import control.self.igor.dailywisdom.exception.BadRequestException;
import control.self.igor.dailywisdom.exception.ForbiddenException;
import control.self.igor.dailywisdom.exception.NotFoundException;
import control.self.igor.dailywisdom.json.View;
import control.self.igor.dailywisdom.model.EntityCounter;
import control.self.igor.dailywisdom.model.Response;
import control.self.igor.dailywisdom.service.crud.CrudService;
import control.self.igor.dailywisdom.service.validation.ValidationService;

public abstract class CrudController<Entity extends Identifiable> {

    public CrudService<Entity> crudService;
    protected ValidationService validationService;

    public CrudController(CrudService<Entity> crudService, ValidationService validationService) {
	this.crudService = crudService;
	this.validationService = validationService;
    }

    @JsonView(View.List.class)
    @GetMapping("/list")
    public List<Entity> getEntities(@RequestParam(value = "page", required = false) Integer page,
	    @RequestParam(value = "size", required = false) Integer size) {
	if (validationService.validatePageRequest(page, size)) {
	    return crudService.getEntities(page, size);
	}
	throw new BadRequestException();

    }

    @JsonView(View.Details.class)
    @GetMapping("/{id}")
    public Entity getEntity(@PathVariable("id") long id) {
	if (id < 1) {
	    throw new BadRequestException();
	}
	try {
	    return crudService.getEntity(id);
	} catch (NoSuchElementException exception) {
	    exception.printStackTrace();
	    throw new NotFoundException();
	}
    }

    @PostMapping("")
    public Response createEntity(@Valid @RequestBody Entity entity) {
	validateRole();
	long id;
	try {
	    id = crudService.createEntity(entity);
	} catch (DataIntegrityViolationException exception) {
	    exception.printStackTrace();
	    throw new BadRequestException(Response.EXISTS);
	}
	return Response.okWithId(id);
    }

    @PutMapping("/{id}")
    public Response updateEntity(@PathVariable("id") long id, @Valid @RequestBody Entity entity) {
	validateRole();
	try {
	    if (!crudService.updateEntity(entity)) {
		throw new NotFoundException();
	    }
	} catch (DataIntegrityViolationException exception) {
	    exception.printStackTrace();
	    throw new BadRequestException(Response.EXISTS);
	}
	return new Response(Response.OK);
    }

    @DeleteMapping("/{id}")
    public Response deleteEntity(@PathVariable("id") long id) {
	if (id < 1) {
	    throw new BadRequestException();
	}
	validateRole();
	try {
	    crudService.deleteEntity(id);
	} catch (EmptyResultDataAccessException exception) {
	    exception.printStackTrace();
	    throw new NotFoundException();
	}
	return new Response(Response.OK);
    }

    @GetMapping("/list/count")
    public EntityCounter countEntities() {
	return new EntityCounter(crudService.countEntities());
    }

    protected void validateRole() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (!validationService.validateRole(authentication, Role.ADMIN)) {
	    throw ForbiddenException.createAdminOnlyException();
	}
    }

}
