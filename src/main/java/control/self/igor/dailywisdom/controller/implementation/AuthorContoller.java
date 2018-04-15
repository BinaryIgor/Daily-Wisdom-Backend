package control.self.igor.dailywisdom.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import control.self.igor.dailywisdom.controller.abstraction.AbstractCrudAndSearchController;
import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;
import control.self.igor.dailywisdom.exception.BadRequestException;
import control.self.igor.dailywisdom.exception.WrongDataException;
import control.self.igor.dailywisdom.model.api.Response;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;
import control.self.igor.dailywisdom.service.abstraction.AbstractAuthorCrudService;
import control.self.igor.dailywisdom.service.abstraction.ImageService;
import control.self.igor.dailywisdom.service.abstraction.SearchService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;

@Controller
@RestController
@RequestMapping("/author")
public class AuthorContoller extends AbstractCrudAndSearchController<Author, SearchByNameCriteria> {

    private ImageService imageService;

    @Autowired
    public AuthorContoller(AbstractAuthorCrudService crudService, SearchService<Author, SearchByNameCriteria> searchService,
	    ValidationService validationService, ImageService imageService) {
	super(crudService, searchService, validationService);
	this.imageService = imageService;
    }

    @GetMapping("/{id}/description")
    public AuthorDescription getAuthorDescription(@PathVariable("id") long id) {
	if (id < 1) {
	    throw new BadRequestException();
	}
	return getAuthorService().getAuthorDescription(id);
    }

    @GetMapping("/{id}/image")
    public byte[] getAuthorImage(long id) {
	return getAuthorService().getAuthorImage(id);
    }

    @PutMapping("/{id}image")
    public Response putAuthorImage(@PathVariable("id") long id, @RequestBody MultipartFile multipartFile) {
	if (id < 1) {
	    throw new BadRequestException();
	}
	byte[] image = imageService.getImageBytes(multipartFile, Author.AUTHOR_IMAGE_WIDTH, Author.AUTHOR_IMAGE_HEIGHT);
	if (image == null || image.length < 1) {
	    throw new WrongDataException();
	}
	Author author = crudService.getEntity(id);
	author.setImage(image);
	if (!crudService.updateEntity(author)) {
	    throw new WrongDataException();
	}
	return new Response(Response.OK);
    }

    private AbstractAuthorCrudService getAuthorService() {
	return (AbstractAuthorCrudService) crudService;
    }

}
