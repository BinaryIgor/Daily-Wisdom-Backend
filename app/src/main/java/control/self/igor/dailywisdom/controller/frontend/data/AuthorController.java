package control.self.igor.dailywisdom.controller.frontend.data;

import java.io.File;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;
import control.self.igor.dailywisdom.exception.BadRequestException;
import control.self.igor.dailywisdom.exception.InternalErrorException;
import control.self.igor.dailywisdom.exception.NotFoundException;
import control.self.igor.dailywisdom.exception.WrongDataException;
import control.self.igor.dailywisdom.model.Response;
import control.self.igor.dailywisdom.model.SearchByNameCriteria;
import control.self.igor.dailywisdom.service.crud.AbstractAuthorCrudService;
import control.self.igor.dailywisdom.service.image.ImageService;
import control.self.igor.dailywisdom.service.search.SearchService;
import control.self.igor.dailywisdom.service.validation.ValidationService;

@RestController
@RequestMapping("/author")
public class AuthorController extends CrudAndSearchController<Author, SearchByNameCriteria> {

    private static final int AUTHOR_IMAGE_WIDTH = 128;
    private static final int AUTHOR_IMAGE_HEIGHT = 128;
    private ImageService imageService;
    private String authorsImagesPath;

    @Autowired
    public AuthorController(AbstractAuthorCrudService crudService,
	    SearchService<Author, SearchByNameCriteria> searchService, ValidationService validationService,
	    ImageService imageService, @Value("${storageAuthorsImagesPath}") String authorsImagesPath) {
	super(crudService, searchService, validationService);
	this.imageService = imageService;
	this.authorsImagesPath = authorsImagesPath;
    }

    @GetMapping("/{id}/description")
    public AuthorDescription getAuthorDescription(@PathVariable("id") long id) {
	if (id < 1) {
	    throw new BadRequestException();
	}
	return getAuthorService().getAuthorDescription(id);
    }

    @PutMapping("/{id}/description")
    public Response putAuthorDescription(@PathVariable("id") long id,
	    @Valid @RequestBody AuthorDescription authorDescription) {
	if (id < 1) {
	    throw new BadRequestException();
	}
	validateRole();
	if (!getAuthorService().saveAuthorDescription(id, authorDescription)) {
	    throw new WrongDataException();
	}
	return new Response(Response.OK);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getAuthorImage(@PathVariable("id") long id) {
	String authorImagePath = getAuthorService().getImagePath(id);
	if (authorImagePath == null || authorImagePath.isEmpty()) {
	    throw new NotFoundException();
	}
	byte[] authorImage = imageService.getImageBytes(authorImagePath);
	if (authorImage == null || authorImage.length < 1) {
	    throw new NotFoundException();
	}
	return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpg")
		.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(authorImage.length)).body(authorImage);
    }

    @PutMapping("/{id}/image")
    public Response putAuthorImage(@PathVariable("id") long id, @RequestPart("image") MultipartFile multipartFile) {
	if (id < 1) {
	    throw new BadRequestException();
	}
	validateRole();
	byte[] image = imageService.getImageBytes(multipartFile, AUTHOR_IMAGE_WIDTH, AUTHOR_IMAGE_HEIGHT);
	if ((image == null || image.length < 1)) {
	    throw new WrongDataException();
	}
	String authorImagePath = authorsImagesPath + "/" + imageService.getImageFileNameWithFormat(String.valueOf(id));
	File imageFile = imageService.createImageFileFromBytes(authorImagePath, image);
	if (imageFile == null || !imageFile.exists()) {
	    throw new InternalErrorException();
	}
	if (!getAuthorService().saveImagePath(id, authorImagePath)) {
	    throw new WrongDataException("Can not save image path!");
	}
	return new Response(Response.OK);
    }

    private AbstractAuthorCrudService getAuthorService() {
	return (AbstractAuthorCrudService) crudService;
    }

}
