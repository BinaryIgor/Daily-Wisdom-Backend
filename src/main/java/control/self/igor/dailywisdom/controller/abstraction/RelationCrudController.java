package control.self.igor.dailywisdom.controller.abstraction;

import java.util.List;

import control.self.igor.dailywisdom.model.api.EntityCounter;
import control.self.igor.dailywisdom.model.api.Response;

public interface RelationCrudController<RelatedEntity> {

    List<RelatedEntity> getRelatedEntities(long id, Integer page, Integer size);

    EntityCounter countRelatedEntities(long id);

    RelatedEntity getRelatedEntity(long mainEntityId, long relatedEntityId);

    Response createRelatedEntity(long mainEntityId, RelatedEntity relatedEntity);

    Response updateRelatedEntity(long mainEntityId, long relatedEntityId, RelatedEntity relatedEntity);

    Response deleteRelatedEntity(long mainEntityId, long relatedEntityId);

}
