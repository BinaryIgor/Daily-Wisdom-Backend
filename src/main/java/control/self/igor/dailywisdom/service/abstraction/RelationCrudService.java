package control.self.igor.dailywisdom.service.abstraction;

import java.util.List;

public interface RelationCrudService<RelatedEntity> {

    List<RelatedEntity> getRelatedEntities(long id);

    List<RelatedEntity> getRelatedEntities(long id, Integer page, Integer size);

    long countRelatedEntities(long id);

    RelatedEntity getRelatedEntity(long mainEntityId, long relatedEntityId);

    long createRelatedEntity(long mainEntityId, RelatedEntity relatedEntity);

    boolean updateRelatedEntity(long mainEntityId, RelatedEntity relatedEntity);

    boolean deleteRelatedEntity(long mainEntityId, long relatedEntityId);

    boolean relatedEntityExists(long mainEntityId, long relatedEntityId);

}
