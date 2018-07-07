package control.self.igor.dailywisdom.controller.frontend.data;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import control.self.igor.dailywisdom.controller.frontend.data.CrudAndSearchController;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.model.SearchByNameCriteria;
import control.self.igor.dailywisdom.service.crud.CrudService;
import control.self.igor.dailywisdom.service.search.SearchService;
import control.self.igor.dailywisdom.service.validation.ValidationService;

@RestController
@RequestMapping("/category")
public class CategoryController extends CrudAndSearchController<Category, SearchByNameCriteria> {

    public static final Logger LOGGER = Logger.getLogger(CategoryController.class);

    @Autowired
    public CategoryController(CrudService<Category> crudService,
	    SearchService<Category, SearchByNameCriteria> searchService, ValidationService validationService) {
	super(crudService, searchService, validationService);
    }

}
