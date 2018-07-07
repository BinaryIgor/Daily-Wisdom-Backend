package control.self.igor.dailywisdom.controller.abstraction;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
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
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.QuoteOwner;
import control.self.igor.dailywisdom.entity.UserRole.Role;
import control.self.igor.dailywisdom.model.EntityCounter;
import control.self.igor.dailywisdom.service.crud.EntityQuoteCrudService;
import control.self.igor.dailywisdom.service.json.JsonService;
import control.self.igor.dailywisdom.util.DataTestUtil;
import control.self.igor.dailywisdom.util.MockUtil;
import control.self.igor.dailywisdom.util.TestUtil;

public class EntityQuoteCrudControllerTest<Entity extends QuoteOwner> {

    protected String baseUrl;
    protected Class<Entity> entityClazz;
    protected MockMvc mockMvc;

    @Autowired
    private EntityQuoteCrudService<Entity> crudService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    protected JsonService jsonService;

    @Before
    public void setUp() {
	Authentication authentication = MockUtil.mockAuthentication(Role.ADMIN);
	SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void getListProperParamsTest() throws Exception {
	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	long entityId = 1;
	String url = baseUrl + entityId + "/list";
	List<Quote> quotes = DataTestUtil.createEntities(Quote.class);
	when(crudService.getQuotes(entityId, null, null)).thenReturn(quotes);
	mockMvc.perform(get(url)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(quotes.size())));
	Integer page = 1;
	Integer size = 2;
	url = TestUtil.createPageUrl(url, page, size);
	when(crudService.getQuotes(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(Integer.class),
		ArgumentMatchers.any(Integer.class))).thenReturn(quotes.subList(0, size));
	mockMvc.perform(get(url)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(size)));
    }

    @Test
    public void getListImproperParamsTest() throws Exception {
	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	String url = TestUtil.createPageUrl(baseUrl + "/2/list", -1, 3);
	mockMvc.perform(get(url)).andExpect(status().isBadRequest());
	url = TestUtil.createPageUrl(baseUrl + "/1/list", -1, -3);
	mockMvc.perform(get(url)).andExpect(status().isBadRequest());
    }

    @Test
    public void countListTest() throws Exception {
	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	String url = baseUrl + "/1/list/count";
	List<Entity> entities = DataTestUtil.createEntities(entityClazz);
	EntityCounter entityCounter = new EntityCounter(entities.size());
	when(crudService.countQuotes(ArgumentMatchers.anyLong())).thenReturn((long) entities.size());
	getObjectAndCompare(url, entityCounter, EntityCounter.class);
	entities = new ArrayList<>();
	entityCounter = new EntityCounter(entities.size());
	when(crudService.countQuotes(ArgumentMatchers.anyLong())).thenReturn((long) entities.size());
	getObjectAndCompare(url, entityCounter, EntityCounter.class);
    }

    @Test
    public void getExistingEntityQuoteTest() throws Exception {
	getEntityQuoteTest(true);
    }

    @Test
    public void getNonExistingEntityQuoteTest() throws Exception {
	getEntityQuoteTest(false);
    }

    private void getEntityQuoteTest(boolean entityExists) throws Exception {
	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	long id = 1;
	String url = baseUrl + "/" + id + "/" + id;
	if (entityExists) {
	    Quote quote = DataTestUtil.createEntity(Quote.class, true);
	    when(crudService.getQuote(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(quote);
	    getQuoteAndCompare(url, quote);
	} else {
	    when(crudService.getQuote(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(null);
	    mockMvc.perform(get(url)).andExpect(status().isNotFound());
	}
    }

    @Test
    public void createProperQuoteTest() throws Exception {
	createQuoteTest(true);
    }

    @Test
    public void createImproperQuoteTest() throws Exception {
	createQuoteTest(false);
    }

    private void createQuoteTest(boolean proper) throws Exception {
	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	Quote quote = DataTestUtil.createEntity(Quote.class, proper);
	ResultMatcher matcher;
	if (proper) {
	    matcher = status().isOk();
	    when(crudService.createQuote(ArgumentMatchers.anyLong(), ArgumentMatchers.any(Quote.class))).thenReturn(4l);
	} else {
	    matcher = status().isBadRequest();
	    when(crudService.createQuote(ArgumentMatchers.anyLong(), ArgumentMatchers.any(Quote.class)))
		    .thenThrow(DataIntegrityViolationException.class);
	}
	String url = baseUrl + "/1";
	mockMvc.perform(post(url).content(jsonService.serialize(quote)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(matcher);
    }

    @Test
    public void properUpdateTest() throws Exception {
	updateTest(UpdateTestMode.proper);

    }

    @Test
    public void existingEntityDeleteTest() throws Exception {
	deleteEntityTest(true);
    }

    @Test
    public void nonExistingEntityDeleteTest() throws Exception {
	deleteEntityTest(false);
    }

    private void deleteEntityTest(boolean entityExists) throws Exception {
	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	long id = 2;
	ResultMatcher matcher;
	if (entityExists) {
	    when(crudService.deleteQuote(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(true);
	    matcher = status().isOk();
	} else {
	    doThrow(NoSuchElementException.class).when(crudService).deleteQuote(ArgumentMatchers.anyLong(),
		    ArgumentMatchers.anyLong());
	    matcher = status().isNotFound();
	}
	String url = baseUrl + "/" + id + "/" + id;
	mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON)).andExpect(matcher);
    }

    @Test
    public void improperUpdateTest() throws Exception {
	updateTest(UpdateTestMode.improperEntity);

    }

    @Test
    public void nonExistingEntityUpdateTest() throws Exception {
	updateTest(UpdateTestMode.nonExistingEntity);
    }

    private void updateTest(UpdateTestMode mode) throws Exception {
	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	Quote quote = DataTestUtil.createEntity(Quote.class, !UpdateTestMode.improperEntity.equals(mode));
	long id = 2;
	String url = baseUrl + "/" + id + "/" + id;
	ResultMatcher matcher;
	if (UpdateTestMode.nonExistingEntity.equals(mode)) {
	    when(crudService.updateQuote(ArgumentMatchers.anyLong(), ArgumentMatchers.any(Quote.class)))
		    .thenReturn(false);
	    matcher = status().isNotFound();
	} else if (UpdateTestMode.improperEntity.equals(mode)) {
	    matcher = status().isBadRequest();
	} else {
	    when(crudService.updateQuote(ArgumentMatchers.anyLong(), ArgumentMatchers.any(Quote.class)))
		    .thenReturn(true);
	    matcher = status().isOk();
	}
	mockMvc.perform(put(url).content(jsonService.serialize(quote)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(matcher);

    }

    private void getQuoteAndCompare(String url, Quote quote) throws Exception {
	getObjectAndCompare(url, quote, Quote.class);
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
