package control.self.igor.dailywisdom.service.abstraction;

import java.util.List;

public interface RelationSearchService<RelatedEntitySearchCriteria, RelatedEntity> {

    List<RelatedEntity> searchRelatedEntities(long mainEntityId, Integer page, Integer size,
	    RelatedEntitySearchCriteria relatedEntitySearchCriteria);

    long countFoundRelatedEntities(long mainEntityId, RelatedEntitySearchCriteria relatedEntitySearchCriteria);
}
