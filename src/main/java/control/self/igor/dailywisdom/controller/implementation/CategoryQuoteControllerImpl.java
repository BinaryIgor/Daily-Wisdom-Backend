package control.self.igor.dailywisdom.controller.implementation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import control.self.igor.dailywisdom.controller.abstraction.CategoryQuoteController;
import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.exception.BadRequestException;
import control.self.igor.dailywisdom.exception.InternalErrorException;
import control.self.igor.dailywisdom.exception.WrongDataException;
import control.self.igor.dailywisdom.json.View;
import control.self.igor.dailywisdom.model.api.EntityCounter;
import control.self.igor.dailywisdom.model.api.Response;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;
import control.self.igor.dailywisdom.service.implementation.CategoryQuoteService;

@Controller
@RestController
@RequestMapping("/category/quote")
public class CategoryQuoteControllerImpl implements CategoryQuoteController {

    @Autowired
    private CategoryQuoteService categoryQuoteService;

    @Autowired
    private ValidationService validationService;

    @JsonView(View.QuoteForCategory.class)
    @GetMapping("/{id}/list")
    @Override
    public List<Quote> getRelatedEntities(@PathVariable("id") long id, Integer page, Integer size) {
	if (id > 0 && validationService.validatePageRequest(page, size)) {
	    return categoryQuoteService.getRelatedEntities(id, page, size);
	}
	throw new BadRequestException();
    }

    @GetMapping("/list/count")
    @Override
    public EntityCounter countRelatedEntities(@PathVariable("id") long id) {
	if (id < 1) {
	    throw new BadRequestException();
	}
	return new EntityCounter(categoryQuoteService.countRelatedEntities(id));
    }

    @GetMapping("/{categoryId}/{quoteId}")
    @JsonView(View.QuoteForCategory.class)
    @Override
    public Quote getRelatedEntity(@PathVariable("categoryId") long categoryId, @PathVariable("quoteId") long quoteId) {
	if (validationService.validateIds(categoryId, quoteId)) {
	    return categoryQuoteService.getRelatedEntity(categoryId, quoteId);
	}
	throw new BadRequestException();
    }

    @PostMapping("/{categoryId}")
    @Override
    public Response createRelatedEntity(@PathVariable("categoryId") long categoryId, @Valid @RequestBody Quote quote) {
	if (categoryId < 1) {
	    throw new WrongDataException();
	}
	if (categoryQuoteService.createRelatedEntity(categoryId, quote) < 1) {
	    throw new InternalErrorException();
	}
	return new Response(Response.OK);
    }

    @PutMapping("/{categoryId}/{quoteId}")
    @Override
    public Response updateRelatedEntity(@PathVariable("categoryId") long categoryId,
	    @PathVariable("quoteId") long quoteId, @Valid @RequestBody Quote quote) {
	if (!validationService.validateIds(categoryId, quoteId)) {
	    throw new WrongDataException();
	}
	boolean updated = categoryQuoteService.updateRelatedEntity(categoryId, quote);
	if (!updated) {
	    throw new WrongDataException();
	}
	return new Response(Response.OK);
    }

    @DeleteMapping("/{categoryId}/quoteId}")
    @Override
    public Response deleteRelatedEntity(@PathVariable("categoryId") long categoryId,
	    @PathVariable("quoteId") long quoteId) {
	boolean wrongData = !validationService.validateIds(categoryId, quoteId)
		|| (categoryQuoteService.deleteRelatedEntity(categoryId, quoteId));
	if (wrongData) {
	    throw new WrongDataException();
	}
	return new Response(Response.OK);
    }

    @GetMapping("/{categoryId}/search")
    @Override
    public List<Quote> searchRelatedEntities(@RequestParam(value = "page", required = false) Integer page,
	    @RequestParam(value = "size", required = false) Integer size, @PathVariable("categoryId") long categoryId,
	    @RequestBody SearchByNameCriteria relatedEntitySearchCriteria) {
	if (categoryId > 0 && validationService.validatePageRequest(page, size)) {
	    return categoryQuoteService.searchRelatedEntities(categoryId, page, size,
		    relatedEntitySearchCriteria.getName());
	}
	throw new BadRequestException();
    }

    @GetMapping("/{categoryId}/search/count")
    @Override
    public EntityCounter countFoundRelatedEntities(@PathVariable("categoryId") long categoryId,
	    SearchByNameCriteria relatedEntitySearchCriteria) {
	if (categoryId < 1) {
	    throw new BadRequestException();
	}
	return new EntityCounter(
		categoryQuoteService.countFoundRelatedEntities(categoryId, relatedEntitySearchCriteria.getName()));
    }

}
