package control.self.igor.dailywisdom.service.abstraction;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import control.self.igor.dailywisdom.entity.Identifiable;
import control.self.igor.dailywisdom.service.search.SearchService;
import control.self.igor.dailywisdom.util.DataTestUtil;

@Transactional
public abstract class AbstractSearchServiceTest<Entity extends Identifiable, SearchCriteria> {

    private Class<Entity> entityClazz;
    private Class<SearchCriteria> searchCriteriaClazz;

    @Autowired
    private SearchService<Entity, SearchCriteria> searchService;

    @Autowired
    protected TestEntityManager entityManager;

    public AbstractSearchServiceTest(Class<Entity> entityClazz, Class<SearchCriteria> searchCriteriaClazz) {
	this.entityClazz = entityClazz;
	this.searchCriteriaClazz = searchCriteriaClazz;
    }

    @Test
    public void searchTest() {
	List<Entity> entities = DataTestUtil.insertEntities(entityManager, entityClazz);
	SearchCriteria searchCriteria = DataTestUtil.createSearchCriteria(searchCriteriaClazz, entityClazz, entities,
		false);
	List<Entity> searchResults = searchService.searchEntities(1, entities.size(), searchCriteria);
	assertTrue(searchResults != null && !searchResults.isEmpty());
	System.out.println("Search criteria = " + searchCriteria);
	searchCriteria = DataTestUtil.createSearchCriteria(searchCriteriaClazz, entityClazz, entities, true);
	searchResults = searchService.searchEntities(1, entities.size(), searchCriteria);
	assertTrue(searchResults == null || searchResults.isEmpty());
	searchCriteria = DataTestUtil.createEmptySearchCriteria(searchCriteriaClazz, entityClazz);
	searchResults = searchService.searchEntities(1, entities.size(), searchCriteria);
	assertTrue(searchResults != null && searchResults.size() == entities.size());
    }

    @Test
    public void countSearchResultTest() {
	List<Entity> entities = DataTestUtil.insertEntities(entityManager, entityClazz);
	SearchCriteria searchCriteria = DataTestUtil.createSearchCriteria(searchCriteriaClazz, entityClazz, entities,
		false);
	long foundEntities = searchService.countFoundEntities(searchCriteria);
	assertTrue(foundEntities > 0);
	searchCriteria = DataTestUtil.createEmptySearchCriteria(searchCriteriaClazz, entityClazz);
	foundEntities = searchService.countFoundEntities(searchCriteria);
	assertTrue(foundEntities == entities.size());
	searchCriteria = DataTestUtil.createSearchCriteria(searchCriteriaClazz, entityClazz, entities, true);
	foundEntities = searchService.countFoundEntities(searchCriteria);
	assertTrue(foundEntities == 0);
    }
}
