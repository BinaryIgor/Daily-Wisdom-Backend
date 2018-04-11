package control.self.igor.dailywisdom.service.abstraction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.QuoteOwner;
import control.self.igor.dailywisdom.util.DataTestUtil;

@Transactional
public abstract class AbstractEntityQuoteCrudServiceTest<Entity extends QuoteOwner> {

    private static final long NON_EXISTING_ENTITY_ID = 9777;
    private Class<Entity> entityClazz;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityQuoteCrudService<Entity> crudService;

    public AbstractEntityQuoteCrudServiceTest(Class<Entity> entityClazz) {
	this.entityClazz = entityClazz;
    }

    @Test
    public void existingEntityListTest() {
	long entityId = DataTestUtil.insertEntityWithDependency(entityManager, entityClazz).getId();
	List<Quote> entityQuotes = crudService.getQuotes(entityId);
	assertTrue(entityQuotes != null && !entityQuotes.isEmpty());
	int pageSize = entityQuotes.size() / 2;
	List<Quote> quotesFirstPage = crudService.getQuotes(entityId, 1, pageSize);
	assertTrue(quotesFirstPage != null && quotesFirstPage.size() > 0);
	assertTrue(quotesFirstPage != null && quotesFirstPage.size() == pageSize);
	List<Quote> quotesSecondPage = crudService.getQuotes(entityId, 2, pageSize);
	assertTrue(quotesSecondPage != null && quotesSecondPage.size() == pageSize);
	for (Quote quote : quotesFirstPage) {
	    assertFalse(quotesSecondPage.contains(quote));
	}
    }

    @Test(expected = NoSuchElementException.class)
    public void nonExistingEntityListTest() {
	crudService.getQuotes(NON_EXISTING_ENTITY_ID);
    }

    @Test(expected = NoSuchElementException.class)
    public void createNonExistingEntityQuoteTest() {
	Quote quote = DataTestUtil.createEntity(Quote.class, true);
	assertFalse(crudService.createQuote(NON_EXISTING_ENTITY_ID, quote) > 0);
    }

    @Test
    public void updateExistingEntityProperQuoteTest() {
	assertTrue(updateEntityQuoteTest(true));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void updateExistingEntityImproperQuoteTest() {
	assertTrue(updateEntityQuoteTest(false));
    }

    private boolean updateEntityQuoteTest(boolean proper) {
	Entity entity = DataTestUtil.insertEntityWithDependency(entityManager, entityClazz);
	Quote quote = entity.getQuotes().get(0);
	DataTestUtil.changeEntity(quote, Quote.class, proper);
	return crudService.updateQuote(entity.getId(), quote);
    }

    @Test
    public void deleteExistingEntityQuoteTest() {
	Entity entity = DataTestUtil.insertEntityWithDependency(entityManager, entityClazz);
	Quote quote = entity.getQuotes().get(0);
	assertTrue(crudService.deleteQuote(entity.getId(), quote.getId()));
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteNonExistingEntityQuoteTest() {
	crudService.deleteQuote(NON_EXISTING_ENTITY_ID, NON_EXISTING_ENTITY_ID);
    }

    @Test(expected = NoSuchElementException.class)
    public void updateNonExistingEntityQuoteTest() {
	Quote quote = DataTestUtil.createEntity(Quote.class, true);
	crudService.updateQuote(NON_EXISTING_ENTITY_ID, quote);
    }

    @Test
    public void createExistingEntityProperQuoteTest() {
	Entity entity = DataTestUtil.insertEntity(entityManager, entityClazz);
	assertTrue(crudService.createQuote(entity.getId(), DataTestUtil.createEntity(Quote.class, true)) > 0);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createExistingEntityImproperQuoteTest() {
	Entity entity = DataTestUtil.insertEntity(entityManager, entityClazz);
	crudService.createQuote(entity.getId(), new Quote());
    }

}
