package control.self.igor.dailywisdom.controller.abstraction;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import control.self.igor.dailywisdom.entity.Identifiable;
import control.self.igor.dailywisdom.entity.UserRole.Role;
import control.self.igor.dailywisdom.model.EntityCounter;
import control.self.igor.dailywisdom.model.Token;
import control.self.igor.dailywisdom.service.crud.CrudService;
import control.self.igor.dailywisdom.service.json.JsonService;
import control.self.igor.dailywisdom.util.DataTestUtil;
import control.self.igor.dailywisdom.util.MockUtil;
import control.self.igor.dailywisdom.util.TestUtil;

public class CrudControllerTest<Entity extends Identifiable> {

    protected String baseUrl;
    protected Class<Entity> entityClazz;
    protected Token token;
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    protected CrudService<Entity> crudService;

    @Autowired
    protected JsonService jsonService;

    @Before
    public void setup() {
	Authentication authentication = MockUtil.mockAuthentication(Role.ADMIN);
	SecurityContextHolder.getContext().setAuthentication(authentication);
	token = MockUtil.mockAdminAccessToken(authentication.getName());
	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getListProperParamsTest() throws Exception {
	String url = baseUrl + "/list";
	List<Entity> entities = DataTestUtil.createEntities(entityClazz);
	when(crudService.getEntities(null, null)).thenReturn(entities);
	mockMvc.perform(get(url)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(entities.size())));
	int page = 1;
	int size = 2;
	url = TestUtil.createPageUrl(url, page, size);
	when(crudService.getEntities(page, size)).thenReturn(entities.subList(0, size));
	mockMvc.perform(get(url)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(size)));
    }

    @Test
    public void getListImproperParamsTest() throws Exception {
	String url = TestUtil.createPageUrl(baseUrl + "/list", -1, 3);
	mockMvc.perform(get(url)).andExpect(status().isBadRequest());
	url = TestUtil.createPageUrl(baseUrl + "/list", 1, -2);
	mockMvc.perform(get(url)).andExpect(status().isBadRequest());
	url = TestUtil.createPageUrl(baseUrl + "/list", -1, -3);
	mockMvc.perform(get(url)).andExpect(status().isBadRequest());
    }

    @Test
    public void countListTest() throws Exception {
	String url = baseUrl + "/list/count";
	List<Entity> entities = DataTestUtil.createEntities(entityClazz);
	EntityCounter entityCounter = new EntityCounter(entities.size());
	when(crudService.countEntities()).thenReturn((long) entities.size());
	getObjectAndCompare(url, entityCounter, EntityCounter.class);
	entities = new ArrayList<>();
	entityCounter = new EntityCounter(entities.size());
	when(crudService.countEntities()).thenReturn((long) entities.size());
	getObjectAndCompare(url, entityCounter, EntityCounter.class);
    }

    @Test
    public void getExistingEntityTest() throws Exception {
	getEntityTest(true);
    }

    @Test
    public void getNonExistingEntityTest() throws Exception {
	getEntityTest(false);
    }

    private void getEntityTest(boolean exists) throws Exception {
	long id = 1;
	String url = baseUrl + "/" + id;
	if (exists) {
	    Entity entity = DataTestUtil.createEntity(entityClazz, true);
	    when(crudService.getEntity(id)).thenReturn(entity);
	    getEntityAndCompare(url, entity);
	} else {
	    when(crudService.getEntity(id)).thenThrow(NoSuchElementException.class);
	    mockMvc.perform(get(url)).andExpect(status().isNotFound());
	}
    }

    @Test
    public void createProperEntityTest() throws Exception {
	createEntityTest(true);
    }

    @Test
    public void createImproperEntityTest() throws Exception {
	createEntityTest(false);
    }

    private void createEntityTest(boolean proper) throws Exception {
	Entity entity = DataTestUtil.createEntity(entityClazz, proper);
	ResultMatcher matcher;
	if (proper) {
	    matcher = status().isOk();
	    when(crudService.createEntity(entity)).thenReturn(2l);
	} else {
	    matcher = status().isBadRequest();
	    when(crudService.createEntity(entity)).thenThrow(DataIntegrityViolationException.class);
	}
	mockMvc.perform(post(baseUrl).content(jsonService.serialize(entity)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(matcher);
    }

    @Test
    public void properUpdateTest() throws Exception {
	updateTest(UpdateTestMode.proper);

    }

    @Test
    public void existingEntityDeleteTest() throws Exception {
	deleteEntityTest();
    }

    @Test
    public void nonExistingEntityDeleteTest() throws Exception {
	deleteEntityTest();
    }

    private void deleteEntityTest() throws Exception {
	long id = 2;
	doNothing().when(crudService).deleteEntity(id);
	mockMvc.perform(delete(baseUrl + "/" + id).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void improperEntityUpdateTest() throws Exception {
	updateTest(UpdateTestMode.improperEntity);

    }

    @Test
    public void nonExistingEntityUpdateTest() throws Exception {
	updateTest(UpdateTestMode.nonExistingEntity);
    }

    private void updateTest(UpdateTestMode mode) throws Exception {
	Entity entity = DataTestUtil.createEntity(entityClazz, !UpdateTestMode.improperEntity.equals(mode));
	long id = 2;
	String url = baseUrl + "/" + id;
	ResultMatcher matcher;
	if (UpdateTestMode.nonExistingEntity.equals(mode)) {
	    when(crudService.updateEntity(entity)).thenReturn(false);
	    matcher = status().isNotFound();
	} else if (UpdateTestMode.improperEntity.equals(mode)) {
	    matcher = status().isBadRequest();
	} else {
	    when(crudService.updateEntity(entity)).thenReturn(true);
	    matcher = status().isOk();
	}
	mockMvc.perform(put(url).content(jsonService.serialize(entity)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(matcher);

    }

    private void getEntityAndCompare(String url, Entity entity) throws Exception {
	getObjectAndCompare(url, entity, entityClazz);
    }

    protected <T> void getObjectAndCompare(String url, T object, Class<T> clazz) throws Exception {
	String responseBody = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn().getResponse()
		.getContentAsString();
	assertTrue(responseBody != null && !responseBody.isEmpty());
	T toCompare = jsonService.deserialize(responseBody, clazz);
	assertEquals(toCompare, object);
    }

    private enum UpdateTestMode {
	proper, improperEntity, nonExistingEntity;
    }

}
