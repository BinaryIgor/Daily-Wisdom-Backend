package control.self.igor.dailywisdom.controller.frontend.implementation;

import java.io.File;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import control.self.igor.dailywisdom.controller.frontend.abstraction.AbstractCrudAndSearchController;
import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;
import control.self.igor.dailywisdom.exception.BadRequestException;
import control.self.igor.dailywisdom.exception.InternalErrorException;
import control.self.igor.dailywisdom.exception.NotFoundException;
import control.self.igor.dailywisdom.exception.WrongDataException;
import control.self.igor.dailywisdom.model.api.Response;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;
import control.self.igor.dailywisdom.service.abstraction.AbstractAuthorCrudService;
import control.self.igor.dailywisdom.service.abstraction.ImageService;
import control.self.igor.dailywisdom.service.abstraction.SearchService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;

@RestController
@RequestMapping("/author")
public class AuthorController extends AbstractCrudAndSearchController<Author, SearchByNameCriteria> {

    private static final int AUTHOR_IMAGE_WIDTH = 128;
    private static final int AUTHOR_IMAGE_HEIGHT = 128;
    private static final Logger LOGGER = Logger.getLogger(AuthorController.class.getSimpleName());
    private ImageService imageService;

    @Value("${storageAuthorsImagesPath}")
    private String authorsImagesPath;

    @Autowired
    public AuthorController(AbstractAuthorCrudService crudService,
	    SearchService<Author, SearchByNameCriteria> searchService, ValidationService validationService,
	    ImageService imageService) {
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
    public ResponseEntity<byte[]> getAuthorPicture(@PathVariable("id") long id) {
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
	byte[] image = imageService.getImageBytes(multipartFile, AUTHOR_IMAGE_WIDTH, AUTHOR_IMAGE_HEIGHT);
	if ((image == null || image.length < 1)) {
	    throw new WrongDataException();
	}
	String authorImagePath = authorsImagesPath + "/" + imageService.getImageFileNameWithFormat(String.valueOf(id));
	File imageFile = imageService.createImageFileFromBytes(authorImagePath, image);
	if (imageFile == null || !imageFile.exists()) {
	    throw new InternalErrorException();
	}
	if (!((AbstractAuthorCrudService) crudService).saveImagePath(id, authorImagePath)) {
	    throw new WrongDataException();
	}
	return new Response(Response.OK);
    }

    private AbstractAuthorCrudService getAuthorService() {
	return (AbstractAuthorCrudService) crudService;
    }

}
