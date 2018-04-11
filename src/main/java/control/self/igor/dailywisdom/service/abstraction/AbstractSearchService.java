package control.self.igor.dailywisdom.service.abstraction;

public abstract class AbstractSearchService<SearchCriteria, Entity> {

    // protected static final int DEFAULT_PAGE_SIZE = 50;
    //
    // public List<Entity> searchEntities(Integer page, Integer size, SearchCriteria
    // searchCriteria) {
    // Sort sort = new Sort(Direction.ASC, "id");
    // if (page == null || page < 1) {
    // return repository.searchCategoriesByName(searchCriteria);
    // }
    //
    // if (size == null || size < 1) {
    // size = DEFAULT_PAGE_SIZE;
    // }
    // return repository.searchCategoriesByName(searchCriteria, PageRequest.of(page
    // - 1, size, sort)).getContent();
    // }
    //
    // @Override
    // public long countFoundEntities(String searchCriteria) {
    // return repository.countFoundCategories(searchCriteria);
    // }
}
