package control.self.igor.dailywisdom.controller.implementation;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import control.self.igor.dailywisdom.controller.abstraction.AbstractCrudAndSearchControllerTest;
import control.self.igor.dailywisdom.controller.frontend.implementation.AuthorController;
import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.model.search.SearchByNameCriteria;
import control.self.igor.dailywisdom.service.abstraction.AbstractAuthorCrudService;
import control.self.igor.dailywisdom.service.abstraction.ImageService;
import control.self.igor.dailywisdom.service.abstraction.JsonService;
import control.self.igor.dailywisdom.service.abstraction.ValidationService;
import control.self.igor.dailywisdom.service.implementation.AuthorSearchService;
import control.self.igor.dailywisdom.service.implementation.JsonServiceImpl;
import control.self.igor.dailywisdom.service.implementation.ValidationServiceImpl;
import control.self.igor.dailywisdom.util.MockUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest extends AbstractCrudAndSearchControllerTest<Author, SearchByNameCriteria> {

    @TestConfiguration
    static class AuthorControllerTestConfiguration {

	@Bean
	public AbstractAuthorCrudService crudService() {
	    return Mockito.mock(AbstractAuthorCrudService.class);
	}

	@Bean
	public AuthorSearchService searchService() {
	    return Mockito.mock(AuthorSearchService.class);
	}

	@Bean
	public ValidationService validationService() {
	    return new ValidationServiceImpl();
	}

	@Bean
	public ImageService imageService() {
	    return Mockito.mock(ImageService.class);
	}

	@Bean
	public JsonService jsonService() {
	    return new JsonServiceImpl();
	}

    }

    @Autowired
    private AbstractAuthorCrudService crudService;

    @Autowired
    private ImageService imageService;

    public AuthorControllerTest() {
	super("/author", Author.class, SearchByNameCriteria.class);

    }

    @Test
    public void getExistingAuthorImageTest() throws Exception {
	byte[] mockImage = MockUtil.createImage();
	when(crudService.getAuthorImagePath(ArgumentMatchers.any(Long.class))).thenReturn("/storage/tmp/1.jpg");
	when(imageService.getImageBytes(ArgumentMatchers.any(String.class))).thenReturn(mockImage);
	String url = baseUrl + "/1/image";
	byte[] returnedImage = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn().getResponse()
		.getContentAsByteArray();
	assertTrue(Arrays.equals(mockImage, returnedImage));
    }

    @Test
    public void getNonExistingAuthorImageTest() throws Exception {
	when(crudService.getAuthorImagePath(ArgumentMatchers.any(Long.class))).thenReturn(null);
	String url = baseUrl + "/1/image";
	mockMvc.perform(get(url)).andExpect(status().isNotFound());
    }

    @Test
    public void putExistingAuthorImageTest() throws Exception {
	byte[] imageMock = MockUtil.createImage();
	File fileMock = Mockito.mock(File.class);
	when(fileMock.exists()).thenReturn(true);
	when(crudService.getEntity(ArgumentMatchers.any(Long.class))).thenReturn(MockUtil.createAuthor());
	when(imageService.getImageBytes(ArgumentMatchers.any(MultipartFile.class), ArgumentMatchers.any(Integer.class),
		ArgumentMatchers.any(Integer.class))).thenReturn(imageMock);
	when(imageService.createImageFileFromBytes(ArgumentMatchers.any(String.class),
		ArgumentMatchers.any(byte[].class))).thenReturn(fileMock);
	when(crudService.updateEntity(ArgumentMatchers.any(Author.class))).thenReturn(true);
	String url = baseUrl + "/1/image";
	mockMvc.perform(mockPutImageRequest(url).file(new MockMultipartFile("image", imageMock)))
		.andExpect(status().isOk());
    }

    @Test
    public void putNonExistingAuthorImageTest() throws Exception {
	byte[] imageMock = MockUtil.createImage();
	String url = baseUrl + "/-1/image";
	RequestBuilder builder = mockPutImageRequest(url).file(new MockMultipartFile("image", imageMock));
	mockMvc.perform(builder).andExpect(status().isBadRequest());
	url = baseUrl + "/1/image";
	when(crudService.getEntity(ArgumentMatchers.any(Long.class))).thenReturn(null);
	mockMvc.perform(builder).andExpect(status().isBadRequest());
    }

    private MockMultipartHttpServletRequestBuilder mockPutImageRequest(String url) {
	MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(url);
	builder.with(request -> {
	    request.setMethod(HttpMethod.PUT.toString());
	    return request;
	});
	return builder;
    }

}
