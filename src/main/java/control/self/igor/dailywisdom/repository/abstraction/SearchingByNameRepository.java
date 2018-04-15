package control.self.igor.dailywisdom.repository.abstraction;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import control.self.igor.dailywisdom.entity.Searchable;

@NoRepositoryBean
public interface SearchingByNameRepository<Entity extends Searchable> extends PagingAndSortingRepository<Entity, Long> {

    @Query("select e from #{#entityName} e where lower(e.name) like %:name%")
    Page<Entity> searchEntities(@Param("name") String name, Pageable pageable);

    @Query("select e from #{#entityName} e where lower(e.name) like %:name%")
    List<Entity> searchEntities(@Param("name") String name);

    @Query("select count(e) from #{#entityName} e where lower(e.name) like %:name%")
    long countFoundEntities(@Param("name") String name);
}
