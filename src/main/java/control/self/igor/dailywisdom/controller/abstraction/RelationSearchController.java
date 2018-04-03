package control.self.igor.dailywisdom.controller.abstraction;

import java.util.List;

import control.self.igor.dailywisdom.model.api.EntityCounter;

public interface RelationSearchController<RelatedEntitySearchCriteria, RelatedEntity> {

    List<RelatedEntity> searchRelatedEntities(Integer page, Integer size, long mainEntityId,
	    RelatedEntitySearchCriteria relatedEntitySearchCriteria);

    EntityCounter countFoundRelatedEntities(long mainEntityId, RelatedEntitySearchCriteria relatedEntitySearchCriteria);
}
