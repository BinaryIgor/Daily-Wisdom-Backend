package control.self.igor.dailywisdom.repository.abstraction;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import control.self.igor.dailywisdom.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    @Query("select c from Category c where c.name = :name")
    Category findByName(@Param("name") String name);

    @Query("select c from Category c where c.name like %:name%")
    Page<Category> searchCategoriesByName(@Param("name") String name, Pageable pageable);

    @Query("select c from Category c where c.name like %:name%")
    List<Category> searchCategoriesByName(@Param("name") String name);

    @Query("select count(c) from Category c where c.name like %:name%")
    long countFoundCategories(@Param("name") String name);

}
