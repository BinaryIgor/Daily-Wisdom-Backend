package control.self.igor.dailywisdom.service.abstraction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.QuoteOwner;
import control.self.igor.dailywisdom.util.DataTestUtil;

public abstract class AbstractEntityQuoteServiceTest<EntityWithQuotes extends QuoteOwner> {

    protected static final long NON_EXISTING_ENTITY_ID = 9777;
    private static final String NO_RESULT_SEARCH_CRITERIA = "Lposaf";
    private Class<EntityWithQuotes> entityClazz;

    @Autowired
    private TestEntityManager entityManager;

    public AbstractEntityQuoteServiceTest(Class<EntityWithQuotes> entityClazz) {
	this.entityClazz = entityClazz;
    }

    protected void existingEntityListTest(EntityQuoteService service) {
	long entityId = DataTestUtil.insertEntityWithDependency(entityManager, entityClazz).getId();
	List<Quote> entityQuotes = service.getRelatedEntities(entityId);
	assertTrue(entityQuotes != null && !entityQuotes.isEmpty());
	int pageSize = entityQuotes.size() / 2;
	List<Quote> quotesFirstPage = service.getRelatedEntities(entityId, 1, pageSize);
	assertTrue(quotesFirstPage != null && quotesFirstPage.size() > 0);
	assertTrue(quotesFirstPage != null && quotesFirstPage.size() == pageSize);
	List<Quote> quotesSecondPage = service.getRelatedEntities(entityId, 2, pageSize);
	assertTrue(quotesSecondPage != null && quotesSecondPage.size() == pageSize);
	for (Quote quote : quotesFirstPage) {
	    assertFalse(quotesSecondPage.contains(quote));
	}
    }

    protected void nonExistingEntityListTest(EntityQuoteService service) {
	List<Quote> entityQuotes = service.getRelatedEntities(NON_EXISTING_ENTITY_ID);
	assertTrue(entityQuotes == null || entityQuotes.isEmpty());
	int pageSize = 3;
	List<Quote> quotesFirstPage = service.getRelatedEntities(NON_EXISTING_ENTITY_ID, 1, pageSize);
	assertTrue(quotesFirstPage == null || quotesFirstPage.isEmpty());
	List<Quote> quotesSecondPage = service.getRelatedEntities(NON_EXISTING_ENTITY_ID, 2, pageSize);
	assertTrue(quotesSecondPage == null || quotesSecondPage.isEmpty());
    }

    protected void searchExistingEntityQuotesTest(EntityQuoteService service) {
	long entityId = DataTestUtil.insertEntityWithDependency(entityManager, entityClazz).getId();
	List<Quote> entityQuotes = service.getRelatedEntities(entityId);
	String searchCriteria = DataTestUtil.createSearchCriteria(String.class, Quote.class, entityQuotes);
	int pageSize = 2;
	List<Quote> searchResults = service.searchRelatedEntities(entityId, 1, pageSize, searchCriteria);
	assertTrue(searchResults != null && !searchResults.isEmpty());
	searchResults = service.searchRelatedEntities(entityId, 1, pageSize, NO_RESULT_SEARCH_CRITERIA);
	assertFalse(searchResults != null && !searchResults.isEmpty());
	searchResults = service.searchRelatedEntities(entityId, 1, pageSize, null);
	assertFalse(searchResults != null && !searchResults.isEmpty());
    }

    protected void searchNonExistingEntityQuotesTest(EntityQuoteService service) {
	int pageSize = 2;
	List<Quote> searchResults = service.searchRelatedEntities(NON_EXISTING_ENTITY_ID, 1, pageSize,
		NO_RESULT_SEARCH_CRITERIA);
	assertTrue(searchResults == null || searchResults.isEmpty());
    }

    protected void createExistingEntityProperQuoteTest(EntityQuoteService service) {
	EntityWithQuotes entity = DataTestUtil.insertEntity(entityManager, entityClazz);
	assertTrue(service.createRelatedEntity(entity.getId(), DataTestUtil.createEntity(Quote.class, true)) > 0);
    }

    protected void createExistingEntityImproperQuoteTest(EntityQuoteService service) {
	EntityWithQuotes entity = DataTestUtil.insertEntity(entityManager, entityClazz);
	service.createRelatedEntity(entity.getId(), new Quote());
    }

    protected void createNonExistingEntityQuoteTest(EntityQuoteService service) {
	Quote quote = DataTestUtil.createEntity(Quote.class, true);
	assertFalse(service.createRelatedEntity(NON_EXISTING_ENTITY_ID, quote) > 0);
    }

    protected void updateExistingEntityProperQuoteTest(EntityQuoteService service) {
	assertTrue(updateEntityQuoteTest(service, true));
    }

    public void updateExistingEntityImproperQuoteTest(EntityQuoteService service) {
	assertTrue(updateEntityQuoteTest(service, false));
    }

    private boolean updateEntityQuoteTest(EntityQuoteService service, boolean proper) {
	EntityWithQuotes entity = DataTestUtil.insertEntityWithDependency(entityManager, entityClazz);
	Quote quote = entity.getQuotes().get(0);
	DataTestUtil.changeEntity(quote, Quote.class, proper);
	return service.updateRelatedEntity(entity.getId(), quote);
    }

    public void deleteExistingEntityQuoteTest(EntityQuoteService service) {
	EntityWithQuotes entity = DataTestUtil.insertEntityWithDependency(entityManager, entityClazz);
	Quote quote = entity.getQuotes().get(0);
	assertTrue(service.deleteRelatedEntity(entity.getId(), quote.getId()));
    }

    public void deleteNonExistingEntityQuoteTest(EntityQuoteService service) {
	assertFalse(service.deleteRelatedEntity(NON_EXISTING_ENTITY_ID, NON_EXISTING_ENTITY_ID));
    }

    public void updateNonExistingEntityQuoteTest(EntityQuoteService service) {
	Quote quote = DataTestUtil.createEntity(Quote.class, true);
	assertFalse(service.updateRelatedEntity(NON_EXISTING_ENTITY_ID, quote));
    }

}
