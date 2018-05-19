package control.self.igor.dailywisdom.controller.frontend.abstraction;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonView;

import control.self.igor.dailywisdom.entity.Identifiable;
import control.self.igor.dailywisdom.exception.BadRequestException;
import control.self.igor.dailywisdom.exception.NotFoundException;
import control.self.igor.dailywisdom.json.View;
import control.self.igor.dailywisdom.model.api.EntityCounter;
import control.self.igor.dailywisdom.model.api.Response;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;

public abstract class AbstractCrudController<Entity extends Identifiable> {

    public static final Logger LOGGER = Logger.getLogger(AbstractCrudController.class);

    public AbstractCrudService<Entity> crudService;
    protected ValidationService validationService;

    public AbstractCrudController(AbstractCrudService<Entity> crudService, ValidationService validationService) {
	this.crudService = crudService;
	this.validationService = validationService;
    }

    @JsonView(View.List.class)
    @GetMapping("/list")
    public List<Entity> getEntities(@RequestParam(value = "page", required = false) Integer page,
	    @RequestParam(value = "size", required = false) Integer size) {
	System.out.println("Will we search?");
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
	    LOGGER.log(Level.WARN, exception.toString(), exception);
	    throw new NotFoundException();
	}
    }

    @PostMapping("")
    public Response createEntity(@Valid @RequestBody Entity entity) {
	long id;
	try {
	    id = crudService.createEntity(entity);
	} catch (DataIntegrityViolationException exception) {
	    LOGGER.log(Level.WARN, exception.toString(), exception);
	    throw new BadRequestException(Response.EXISTS);
	}
	return Response.okWithId(id);
    }

    @PutMapping("/{id}")
    public Response updateEntity(@PathVariable("id") long id, @Valid @RequestBody Entity entity) {
	if (!crudService.updateEntity(entity)) {
	    throw new NotFoundException();
	}
	return new Response(Response.OK);
    }

    @DeleteMapping("/{id}")
    public Response deleteEntity(@PathVariable("id") long id) {
	if (id < 1) {
	    throw new BadRequestException();
	}
	try {
	    crudService.deleteEntity(id);
	} catch (EmptyResultDataAccessException exception) {
	    LOGGER.log(Level.WARN, exception.toString(), exception);
	    throw new NotFoundException();
	}
	crudService.deleteEntity(id);
	return new Response(Response.OK);
    }

    @GetMapping("/list/count")
    public EntityCounter countEntities() {
	return new EntityCounter(crudService.countEntities());
    }

}
