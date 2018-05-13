package control.self.igor.dailywisdom.service.abstraction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import control.self.igor.dailywisdom.entity.Identifiable;
import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.util.DataTestUtil;

@Transactional
public abstract class AbstractCrudServiceTest<Entity extends Identifiable> {

    protected static final long NON_EXISTING_ENTITY_ID = 9777;
    private Class<Entity> entityClazz;

    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected AbstractCrudService<Entity> crudService;

    public AbstractCrudServiceTest(Class<Entity> entityClazz) {
	this.entityClazz = entityClazz;
    }

    @Test
    public void properCreateTest() {
	Entity toInsertEntity = DataTestUtil.createEntity(entityClazz, true);
	if (entityClazz.isAssignableFrom(Quote.class)) {
	    Quote quote = (Quote) toInsertEntity;
	    quote.setAuthor(entityManager.persist(quote.getAuthor()));
	}
	long id = crudService.createEntity(toInsertEntity);
	assertTrue(id > 0);
	Entity insertedEntity = crudService.getEntity(id);
	assertTrue(toInsertEntity.equals(insertedEntity));
    }

    @Test(expected = ConstraintViolationException.class)
    public void improperCreateTest() {
	crudService.createEntity(DataTestUtil.createEntity(entityClazz, false));
    }

    @Test
    public void getListTest() {
	DataTestUtil.insertEntities(entityManager, entityClazz);
	List<Entity> entities = crudService.getEntities();
	assertTrue(entities != null && !entities.isEmpty());
	int pageSize = entities.size() / 2;
	List<Entity> entitiesFirstPage = crudService.getEntities(1, pageSize);
	assertTrue(entitiesFirstPage != null && entitiesFirstPage.size() == pageSize);
	List<Entity> entitiesSecondPage = crudService.getEntities(2, pageSize);
	assertTrue(entitiesSecondPage != null && entitiesSecondPage.size() == pageSize);
	for (Entity entity : entitiesFirstPage) {
	    assertFalse(entitiesSecondPage.contains(entity));
	}
    }

    @Test
    public void properUpdateTest() {
	updateTest(true);
    }

    @Test(expected = ConstraintViolationException.class)
    public void improperUpdateTest() {
	updateTest(false);
    }

    private void updateTest(boolean proper) {
	Entity entity = DataTestUtil.insertEntity(entityManager, entityClazz);
	DataTestUtil.changeEntity(entity, entityClazz, proper);
	if (proper) {
	    assertTrue(crudService.updateEntity(entity));
	} else {
	    assertFalse(crudService.updateEntity(entity));
	}
    }

    @Test(expected = NoSuchElementException.class)
    public void nonExistingGetTest() {
	assertNull(crudService.getEntity(NON_EXISTING_ENTITY_ID));
    }

    @Test
    public void existingGetTest() {
	Entity createdEntity = DataTestUtil.insertEntity(entityManager, entityClazz);
	Entity loadedEntity = crudService.getEntity(createdEntity.getId());
	assertEquals(createdEntity, loadedEntity);
    }

    @Test
    public void duplicatedCreateTest() {
	Entity entity = DataTestUtil.createEntity(entityClazz, true);
	if (entityClazz.isAssignableFrom(Quote.class)) {
	    Quote quote = (Quote) entity;
	    quote.setAuthor(entityManager.persist(quote.getAuthor()));
	}
	long firstId = crudService.createEntity(entity);
	long secondId = crudService.createEntity(entity);
	assertEquals(firstId, secondId);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void duplicatedUpdateTest() {
	Entity firstEntity = DataTestUtil.createEntity(entityClazz, true);
	crudService.createEntity(firstEntity);
	Entity secondEntity = DataTestUtil.createEntity(entityClazz, true);
	DataTestUtil.changeEntity(secondEntity, entityClazz, true);
	crudService.createEntity(secondEntity);
	DataTestUtil.likenEntities(entityClazz, firstEntity, secondEntity);
	crudService.updateEntity(secondEntity);
    }

    @Test(expected = NoSuchElementException.class)
    public void properDeleteTest() {
	Entity entity = DataTestUtil.insertEntity(entityManager, entityClazz);
	crudService.deleteEntity(entity.getId());
	entity = crudService.getEntity(entity.getId());
    }

    @Test
    public void deleteIdempotencyTest() {
	crudService.deleteEntity(NON_EXISTING_ENTITY_ID);
	Entity entity = DataTestUtil.insertEntity(entityManager, entityClazz);
	crudService.deleteEntity(entity.getId());
	crudService.deleteEntity(entity.getId());
    }

}
