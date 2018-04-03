package control.self.igor.dailywisdom.service.abstraction;

public interface CrudAndSearchService<Entity, SearchCriteria>
	extends CrudService<Entity>, SearchService<SearchCriteria, Entity> {

}
