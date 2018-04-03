package control.self.igor.dailywisdom.service.abstraction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import control.self.igor.dailywisdom.entity.Identifiable;
import control.self.igor.dailywisdom.util.DataTestUtil;

public abstract class AbstractCrudServiceTest<Entity extends Identifiable> {

    protected static final long NON_EXISTING_ENTITY_ID = 9777;
    protected Class<Entity> entityClazz;

    @Autowired
    protected TestEntityManager entityManager;

    public AbstractCrudServiceTest(Class<Entity> entityClazz) {
	this.entityClazz = entityClazz;
    }

    public void properCreateTest(CrudService<Entity> service) {
	Entity toInsertEntity = DataTestUtil.createEntity(entityClazz, true);
	long id = service.createEntity(toInsertEntity);
	assertTrue(id > 0);
	Entity insertedEntity = service.getEntity(id);
	assertTrue(toInsertEntity.equals(insertedEntity));
    }

    public void improperCreateTest(CrudService<Entity> service) {
	service.createEntity(DataTestUtil.createEntity(entityClazz, false));
    }

    public void getListTest(CrudService<Entity> service) {
	DataTestUtil.insertEntities(entityManager, entityClazz);
	List<Entity> entities = service.getEntities();
	assertTrue(entities != null && !entities.isEmpty());
	int pageSize = entities.size() / 2;
	List<Entity> entitiesFirstPage = service.getEntities(1, pageSize);
	assertTrue(entitiesFirstPage != null && entitiesFirstPage.size() == pageSize);
	List<Entity> entitiesSecondPage = service.getEntities(2, pageSize);
	assertTrue(entitiesSecondPage != null && entitiesSecondPage.size() == pageSize);
	for (Entity entity : entitiesFirstPage) {
	    assertFalse(entitiesSecondPage.contains(entity));
	}
    }

    public void properUpdateTest(CrudService<Entity> service) {
	updateTest(service, true);
    }

    public void improperUpdateTest(CrudService<Entity> service) {
	updateTest(service, false);
    }

    private void updateTest(CrudService<Entity> service, boolean proper) {
	Entity entity = DataTestUtil.insertEntity(entityManager, entityClazz);
	DataTestUtil.changeEntity(entity, entityClazz, proper);
	if (proper) {
	    assertTrue(service.updateEntity(entity));
	} else {
	    service.updateEntity(entity);
	}
    }

    public void nonExistingGetTest(CrudService<Entity> service) {
	Entity entity = service.getEntity(NON_EXISTING_ENTITY_ID);
	assertNull(entity);
    }

    public void existingGetTest(CrudService<Entity> service) {
	Entity createdEntity = DataTestUtil.insertEntity(entityManager, entityClazz);
	Entity loadedEntity = service.getEntity(createdEntity.getId());
	assertEquals(createdEntity, loadedEntity);
    }

    public void duplicatedCreateTest(CrudService<Entity> service) {
	Entity entity = DataTestUtil.createEntity(entityClazz, true);
	long firstId = service.createEntity(entity);
	long secondId = service.createEntity(entity);
	assertEquals(firstId, secondId);
    }

    public void duplicatedUpdateTest(CrudService<Entity> service) {
	Entity firstEntity = DataTestUtil.createEntity(entityClazz, true);
	service.createEntity(firstEntity);
	Entity secondEntity = DataTestUtil.createEntity(entityClazz, true);
	DataTestUtil.changeEntity(secondEntity, entityClazz, true);
	service.createEntity(secondEntity);
	DataTestUtil.likenEntities(entityClazz, firstEntity, secondEntity);
	service.updateEntity(secondEntity);
    }

    public void properDeleteTest(CrudService<Entity> service) {
	Entity entity = DataTestUtil.insertEntity(entityManager, entityClazz);
	service.deleteEntity(entity.getId());
	entity = service.getEntity(entity.getId());
    }

    public void improperDeleteTest(CrudService<Entity> service) {
	service.deleteEntity(NON_EXISTING_ENTITY_ID);
    }

}
