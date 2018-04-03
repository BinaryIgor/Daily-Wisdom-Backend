
package control.self.igor.dailywisdom.repository.abstraction;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;

public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {

    @Query("select a.authorDescription from Author a where a.id = :id")
    AuthorDescription getAuthorDescription(@Param("id") long id);

    @Query("select a.image from Author a where a.id = :id")
    byte[] getAuthorImage(@Param("id") long id);

    @Query("select a from Author a where a.name like %:name%")
    Page<Author> searchAuthorsByName(@Param("name") String name, Pageable pageable);

    @Query("select a from Author a where a.name like %:name%")
    List<Author> searchAuthorsByName(@Param("name") String name);

    @Query("select count(a) from Author a where a.name like %:name%")
    long countFoundAuthors(@Param("name") String name);

}
