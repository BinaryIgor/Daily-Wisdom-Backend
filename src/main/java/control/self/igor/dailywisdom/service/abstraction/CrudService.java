package control.self.igor.dailywisdom.service.abstraction;

import java.util.List;

public interface CrudService<Entity> {

    List<Entity> getEntities();

    List<Entity> getEntities(Integer page, Integer size);

    long countEntities();

    Entity getEntity(long id);

    long createEntity(Entity entity);

    /**
     * 
     * @param entity
     * @return false if given entity does not exist
     */
    boolean updateEntity(Entity entity);

    /**
     * 
     * @param id
     *            throws RuntimeException if given entity does not exist
     */
    void deleteEntity(long id);

    boolean exists(long id);

}
