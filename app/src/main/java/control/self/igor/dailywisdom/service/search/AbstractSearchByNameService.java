package control.self.igor.dailywisdom.service.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import control.self.igor.dailywisdom.entity.Searchable;
import control.self.igor.dailywisdom.model.SearchByNameCriteria;
import control.self.igor.dailywisdom.specification.SpecificationFactory;

public abstract class AbstractSearchByNameService<Entity extends Searchable> extends AbstractSearchService<Entity>
	implements SearchService<Entity, SearchByNameCriteria> {

    public AbstractSearchByNameService(JpaSpecificationExecutor<Entity> executor, String toSortByColumnName) {
	super(executor, toSortByColumnName);
    }

    @Override
    public List<Entity> searchEntities(Integer page, Integer size, SearchByNameCriteria searchCriteria) {
	String name = searchCriteria.getName();
	if (name == null) {
	    return new ArrayList<>();
	}
	name = name.toLowerCase().trim();
	return searchEntities(page, size, SpecificationFactory.searchByTextColumn("name", name));
    }

    @Override
    public long countFoundEntities(SearchByNameCriteria searchCriteria) {
	String name = searchCriteria.getName();
	if (name == null) {
	    return 0;
	}
	name = name.toLowerCase().trim();
	return countFoundEntities(SpecificationFactory.searchByTextColumn("name", name));
    }
}
