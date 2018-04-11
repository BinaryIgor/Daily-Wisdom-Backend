package control.self.igor.dailywisdom.service.abstraction;

import static org.junit.Assert.assertTrue;

import java.util.List;

import control.self.igor.dailywisdom.entity.Identifiable;
import control.self.igor.dailywisdom.util.DataTestUtil;

public class AbstractCrudAndSearchServiceTest<Entity extends Identifiable, SearchCriteria>
	extends AbstractCrudServiceTest<Entity> {

    public AbstractCrudAndSearchServiceTest(Class<Entity> entityClazz) {
	super(entityClazz);
    }

    public void searchTest(SearchService<SearchCriteria, Entity> service, Class<SearchCriteria> searchCriteriaClazz) {
	List<Entity> entities = DataTestUtil.insertEntities(entityManager, entityClazz);
	SearchCriteria searchCriteria = DataTestUtil.createSearchCriteria(searchCriteriaClazz, entityClazz, entities);
	List<Entity> searchResults = service.searchEntities(1, entities.size(),
		searchCriteriaClazz.cast(searchCriteria));
	assertTrue(searchResults != null && !searchResults.isEmpty());

    }
}
