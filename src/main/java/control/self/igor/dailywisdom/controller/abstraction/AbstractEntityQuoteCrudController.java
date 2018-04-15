package control.self.igor.dailywisdom.controller.abstraction;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonView;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.QuoteOwner;
import control.self.igor.dailywisdom.exception.BadRequestException;
import control.self.igor.dailywisdom.exception.InternalErrorException;
import control.self.igor.dailywisdom.exception.NotFoundException;
import control.self.igor.dailywisdom.exception.WrongDataException;
import control.self.igor.dailywisdom.json.View;
import control.self.igor.dailywisdom.model.api.EntityCounter;
import control.self.igor.dailywisdom.model.api.Response;
import control.self.igor.dailywisdom.service.abstraction.AbstractEntityQuoteCrudService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;

public abstract class AbstractEntityQuoteCrudController<Entity extends QuoteOwner> {

    private static final Logger LOGGER = Logger.getLogger(AbstractEntityQuoteCrudController.class.getSimpleName());
    protected AbstractEntityQuoteCrudService<Entity> crudService;
    protected ValidationService validationService;

    public AbstractEntityQuoteCrudController(AbstractEntityQuoteCrudService<Entity> crudService,
	    ValidationService validationService) {
	this.crudService = crudService;
	this.validationService = validationService;
    }

    @JsonView(View.List.class)
    @GetMapping("/{id}/list")
    public List<Quote> getQuotes(@PathVariable("id") long id, Integer page, Integer size) {
	if (id > 0 && validationService.validatePageRequest(page, size)) {
	    return crudService.getQuotes(id, page, size);
	}
	throw new BadRequestException();
    }

    @GetMapping("/list/count")
    public EntityCounter countQuotes(@PathVariable("id") long id) {
	if (id < 1) {
	    throw new BadRequestException();
	}
	return new EntityCounter(crudService.countQuotes(id));
    }

    @GetMapping("/{id}/{quoteId}")
    @JsonView(View.List.class)
    public Quote Quote(@PathVariable("id") long id, @PathVariable("quoteId") long quoteId) {
	if (!validationService.validateIds(id, quoteId)) {
	    throw new BadRequestException();
	}
	try {
	    return crudService.getQuote(id, quoteId);
	} catch (NoSuchElementException exception) {
	    LOGGER.log(Level.WARN, exception.toString(), exception);
	    throw new NotFoundException();
	}
    }

    @PostMapping("/{id}")
    public Response createQuote(@PathVariable("id") long id, @Valid @RequestBody Quote quote) {
	if (id < 1) {
	    throw new WrongDataException();
	}
	if (crudService.createQuote(id, quote) < 1) {
	    throw new InternalErrorException();
	}
	return new Response(Response.OK);
    }

    @PutMapping("/{id}/{quoteId}")
    public Response updateQuote(@PathVariable("id") long id, @PathVariable("quoteId") long quoteId,
	    @Valid @RequestBody Quote quote) {
	if (!validationService.validateIds(id, quoteId)) {
	    throw new WrongDataException();
	}
	boolean updated = crudService.updateQuote(id, quote);
	if (!updated) {
	    throw new WrongDataException();
	}
	return new Response(Response.OK);
    }

    @DeleteMapping("/{id}/quoteId}")
    public Response deleteQuote(@PathVariable("id") long id, @PathVariable("quoteId") long quoteId) {
	boolean wrongData = !validationService.validateIds(id, quoteId) || (crudService.deleteQuote(id, quoteId));
	if (wrongData) {
	    throw new WrongDataException();
	}
	return new Response(Response.OK);
    }

}
