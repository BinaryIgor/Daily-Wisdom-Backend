package control.self.igor.dailywisdom.service.abstraction;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import control.self.igor.dailywisdom.entity.Identifiable;
import control.self.igor.dailywisdom.util.DataTestUtil;

public abstract class AbstractSearchServiceTest<Entity extends Identifiable, SearchCriteria> {

    public void searchTest(TestEntityManager entityManager, SearchService<SearchCriteria, Entity> service,
	    Class<Entity> entityClazz, Class<SearchCriteria> searchCriteriaClazz) {
	List<Entity> entities = DataTestUtil.insertEntities(entityManager, entityClazz);
	SearchCriteria searchCriteria = DataTestUtil.createSearchCriteria(searchCriteriaClazz, entityClazz, entities);
	List<Entity> searchResults = service.searchEntities(1, entities.size(),
		searchCriteriaClazz.cast(searchCriteria));

	assertTrue(searchResults != null && !searchResults.isEmpty());
    }
}
