package control.self.igor.dailywisdom.controller.mobile;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.exception.BadRequestException;
import control.self.igor.dailywisdom.exception.NotFoundException;
import control.self.igor.dailywisdom.json.View;
import control.self.igor.dailywisdom.service.crud.AbstractAuthorCrudService;
import control.self.igor.dailywisdom.service.crud.CrudService;
import control.self.igor.dailywisdom.service.image.ImageService;

@RestController
@RequestMapping("/data")
public class DataController {

    private AbstractAuthorCrudService authorService;
    private CrudService<Category> categoryService;
    private ImageService imageService;

    public DataController(AbstractAuthorCrudService authorService, CrudService<Category> categoryService,
	    ImageService imageService) {
	this.authorService = authorService;
	this.categoryService = categoryService;
	this.imageService = imageService;
    }

    @GetMapping("/categories")
    @JsonView(View.List.class)
    public List<Category> getCategories() {
	return categoryService.getEntities();
    }

    @GetMapping("/authors")
    @JsonView(View.List.class)
    public List<Author> getAuthors() {
	return authorService.getEntities();
    }

    @GetMapping("/author/{id}")
    @JsonView(View.AuthorDetails.class)
    public Author getAuthor(@PathVariable("id") long id) {
	return authorService.getEntity(id);
    }

    @GetMapping("/author/{id}/image")
    public ResponseEntity<byte[]> getAuthorImage(@PathVariable("id") long id) {
	if (id < 0) {
	    throw new BadRequestException();
	}
	String authorImagePath = authorService.getImagePath(id);
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

}
