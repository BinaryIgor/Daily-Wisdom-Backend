package control.self.igor.dailywisdom.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import control.self.igor.dailywisdom.service.abstraction.CategoryService;
import control.self.igor.dailywisdom.service.abstraction.JsonService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    private static final Logger LOGGER = Logger.getLogger(CategoryControllerTest.class.getSimpleName());

    @MockBean
    private CategoryService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonService jsonService;

    @Test
    public void shouldGetListOfCategories() throws Exception {
	mockMvc.perform(get("/category/list")).andExpect(status().isOk()).andReturn().getResponse()
		.getContentAsString();

    }
    //
    // @Test
    // public void createCategoryTest() throws Exception {
    // mockMvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON_VALUE)
    // .content(jsonService.serialize(DatabaseInitializatorUtil.createCategory()))).andExpect(status().isOk());
    // }

}
