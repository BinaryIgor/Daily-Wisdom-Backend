package control.self.igor.dailywisdom.controller.implementation;

import java.util.List;

import javax.validation.Valid;

import org.jboss.logging.Logger;
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

import control.self.igor.dailywisdom.controller.abstraction.CategoryController;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.exception.BadRequestException;
import control.self.igor.dailywisdom.exception.InternalErrorException;
import control.self.igor.dailywisdom.json.View;
import control.self.igor.dailywisdom.model.api.EntityCounter;
import control.self.igor.dailywisdom.model.api.Response;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;
import control.self.igor.dailywisdom.service.abstraction.CategoryService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;

@Controller
@RestController
@RequestMapping("/category")
public class CategoryControllerImpl implements CategoryController {

    public static final Logger LOGGER = Logger.getLogger(CategoryControllerImpl.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ValidationService validationService;

    @JsonView(View.CategoryList.class)
    @GetMapping("/list")
    @Override
    public List<Category> getEntities(@RequestParam(value = "page", required = false) Integer page,
	    @RequestParam(value = "size", required = false) Integer size) {
	if (validationService.validatePageRequest(page, size)) {
	    return categoryService.getEntities(page, size);
	}
	throw new BadRequestException();

    }

    @JsonView(View.CategoryDetails.class)
    @GetMapping("/{id}")
    @Override
    public Category getEntity(@PathVariable("id") long id) {
	if (id < 1) {
	    throw new BadRequestException();
	}
	return categoryService.getEntity(id);
    }

    @PostMapping("")
    @Override
    public Response createEntity(@Valid @RequestBody Category entity) {
	if (categoryService.createEntity(entity) > 0) {
	    throw new InternalErrorException();
	}
	return new Response(Response.OK);
    }

    @PutMapping("/{id}")
    @Override
    public Response updateEntity(@PathVariable("id") long id, @Valid @RequestBody Category entity) {
	if (!categoryService.updateEntity(entity)) {
	    throw new InternalErrorException();
	}
	return new Response(Response.OK);
    }

    @DeleteMapping("/{id}")
    @Override
    public Response deleteEntity(@PathVariable("id") long id) {
	categoryService.deleteEntity(id);
	return new Response(Response.OK);
    }

    @GetMapping("/list/count")
    @Override
    public EntityCounter countEntities() {
	return new EntityCounter(categoryService.countEntities());
    }

    @GetMapping("/search")
    @Override
    public List<Category> searchEntities(@RequestParam(value = "page", required = false) Integer page,
	    @RequestParam(value = "size", required = false) Integer size,
	    @RequestBody SearchByNameCriteria searchCriteria) {
	if (validationService.validatePageRequest(page, size)) {
	    categoryService.searchEntities(page, size, searchCriteria.getName());
	}
	throw new BadRequestException();
    }

    @GetMapping("/search/count")
    @Override
    public EntityCounter countFoundEntities(@RequestBody SearchByNameCriteria searchCriteria) {
	return new EntityCounter(categoryService.countFoundEntities(searchCriteria.getName()));
    }

}
