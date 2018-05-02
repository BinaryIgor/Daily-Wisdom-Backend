package control.self.igor.dailywisdom.service.abstraction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.QuoteOwner;
import control.self.igor.dailywisdom.util.DataTestUtil;

@Transactional
public abstract class AbstractEntityQuoteSearchServiceTest<Entity extends QuoteOwner> {

    private static final long NON_EXISTING_ENTITY_ID = 9777;
    private static final String NO_RESULT_SEARCH_CRITERIA = "Lposaf";
    private Class<Entity> entityClazz;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AbstractEntityQuoteSearchService<Entity> searchService;

    public AbstractEntityQuoteSearchServiceTest(Class<Entity> entityClazz) {
	this.entityClazz = entityClazz;
    }

    @Test
    public void searchExistingEntityQuotesTest() {
	Entity entity = DataTestUtil.insertEntityWithDependency(entityManager, entityClazz);
	String searchCriteria = entity.getQuotes().get(0).getContent().substring(0, 2);
	int pageSize = 2;
	List<Quote> searchResults = searchService.searchQuotes(entity.getId(), 1, pageSize, searchCriteria);
	assertTrue(searchResults != null && !searchResults.isEmpty());
	searchResults = searchService.searchQuotes(entity.getId(), 1, pageSize, NO_RESULT_SEARCH_CRITERIA);
	assertFalse(searchResults != null && !searchResults.isEmpty());
	searchResults = searchService.searchQuotes(entity.getId(), 1, pageSize, null);
	assertFalse(searchResults != null && !searchResults.isEmpty());
    }

    @Test
    public void searchNonExistingEntityQuotesTest() {
	int pageSize = 2;
	List<Quote> searchResult = searchService.searchQuotes(NON_EXISTING_ENTITY_ID, 1, pageSize,
		NO_RESULT_SEARCH_CRITERIA);
	assertTrue(searchResult == null || searchResult.isEmpty());
    }

}
