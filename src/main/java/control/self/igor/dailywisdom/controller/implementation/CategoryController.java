package control.self.igor.dailywisdom.controller.implementation;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import control.self.igor.dailywisdom.controller.abstraction.AbstractCrudAndSearchController;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;
import control.self.igor.dailywisdom.service.abstraction.AbstractCrudService;
import control.self.igor.dailywisdom.service.abstraction.SearchService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;

@Controller
@RestController
@RequestMapping("/category")
public class CategoryController extends AbstractCrudAndSearchController<Category, SearchByNameCriteria> {

    public static final Logger LOGGER = Logger.getLogger(CategoryController.class);

    @Autowired
    public CategoryController(AbstractCrudService<Category> crudService,
	    SearchService<Category, SearchByNameCriteria> searchService, ValidationService validationService) {
	super(crudService, searchService, validationService);
    }

}
