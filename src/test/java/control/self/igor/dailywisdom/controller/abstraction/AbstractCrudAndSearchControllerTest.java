package control.self.igor.dailywisdom.controller.abstraction;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import control.self.igor.dailywisdom.entity.Identifiable;
import control.self.igor.dailywisdom.service.abstraction.SearchService;
import control.self.igor.dailywisdom.util.DataTestUtil;
import control.self.igor.dailywisdom.util.TestUtil;

public abstract class AbstractCrudAndSearchControllerTest<Entity extends Identifiable, SearchCriteria>
	extends AbstractCrudControllerTest<Entity> {

    private Class<SearchCriteria> searchCriteriaClazz;
    private String searchUrl;
    @Autowired
    private SearchService<Entity, SearchCriteria> searchService;

    public AbstractCrudAndSearchControllerTest(String baseUrl, Class<Entity> entityClazz,
	    Class<SearchCriteria> searchCriteriaClazz) {
	super(baseUrl, entityClazz);
	this.searchCriteriaClazz = searchCriteriaClazz;
	this.searchUrl = baseUrl + "/search";
    }

    @Test
    public void searchProperParamsTest() throws Exception {
	List<Entity> entities = DataTestUtil.createEntities(entityClazz);
	SearchCriteria searchCriteria = DataTestUtil.createSearchCriteria(searchCriteriaClazz, entityClazz, entities,
		false);
	when(searchService.searchEntities(ArgumentMatchers.any(Integer.class), ArgumentMatchers.any(Integer.class),
		ArgumentMatchers.any(searchCriteriaClazz))).thenReturn(entities);
	String url = baseUrl + "/search";
	// mockMvc.perform(
	// post(url).contentType(MediaType.APPLICATION_JSON).content(jsonService.serialize(searchCriteria)))
	// .andExpect(status().isOk()).andExpect(jsonPath("$",
	// hasSize(entities.size())));
	int searchResultSize = entities.size() / 2;
	List<Entity> firstPageSearchResult = getPageSearchResult(entities, searchCriteria, 1, searchResultSize);
	assertTrue(firstPageSearchResult != null && !firstPageSearchResult.isEmpty());
	List<Entity> secondPageSearchResult = getPageSearchResult(entities, searchCriteria, 2, searchResultSize);
	assertTrue(secondPageSearchResult != null && !secondPageSearchResult.isEmpty());
	// for (Entity entity : firstPageSearchResult) {
	// assertFalse(secondPageSearchResult.contains(entity));
	// }

    }

    private List<Entity> getPageSearchResult(List<Entity> entities, SearchCriteria searchCriteria, int page, int size)
	    throws Exception {
	String url = TestUtil.createPageUrl(searchUrl, page, size);
	when(searchService.searchEntities(ArgumentMatchers.any(Integer.class), ArgumentMatchers.any(Integer.class),
		ArgumentMatchers.any(searchCriteriaClazz))).thenReturn(entities.subList(page - 1, size));
	String requestBody = mockMvc
		.perform(post(url).contentType(MediaType.APPLICATION_JSON)
			.content(jsonService.serialize(searchCriteria)))
		.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
	return jsonService.deserializeList(requestBody, entityClazz);
    }

    @Test
    public void searchImproperParamsTest() throws Exception {
	List<Entity> entities = DataTestUtil.createEntities(entityClazz);
	SearchCriteria searchCriteria = DataTestUtil.createSearchCriteria(searchCriteriaClazz, entityClazz, entities,
		false);
	String url = TestUtil.createPageUrl(searchUrl, -1, -2);
	mockMvc.perform(
		post(url).contentType(MediaType.APPLICATION_JSON).content(jsonService.serialize(searchCriteria)))
		.andExpect(status().isBadRequest());

	url = TestUtil.createPageUrl(searchUrl, 1, -2);
	mockMvc.perform(
		post(url).contentType(MediaType.APPLICATION_JSON).content(jsonService.serialize(searchCriteria)))
		.andExpect(status().isBadRequest());
	url = TestUtil.createPageUrl(searchUrl, -1, 2);
	mockMvc.perform(
		post(url).contentType(MediaType.APPLICATION_JSON).content(jsonService.serialize(searchCriteria)))
		.andExpect(status().isBadRequest());
    }

}
