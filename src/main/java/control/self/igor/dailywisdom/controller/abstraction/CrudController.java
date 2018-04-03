package control.self.igor.dailywisdom.controller.abstraction;

import java.util.List;

import control.self.igor.dailywisdom.model.api.EntityCounter;
import control.self.igor.dailywisdom.model.api.Response;

public interface CrudController<Entity> {

    List<Entity> getEntities(Integer page, Integer size);

    EntityCounter countEntities();

    Entity getEntity(long id);

    Response createEntity(Entity entity);

    Response updateEntity(long id, Entity entity);

    Response deleteEntity(long id);

}
