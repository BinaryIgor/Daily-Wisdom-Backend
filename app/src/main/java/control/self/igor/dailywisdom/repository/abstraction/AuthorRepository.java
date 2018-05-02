
package control.self.igor.dailywisdom.repository.abstraction;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;

public interface AuthorRepository extends PagingAndSortingRepository<Author, Long>, JpaSpecificationExecutor<Author> {

    @Query("select a.authorDescription from Author a where a.id = :id")
    AuthorDescription getAuthorDescription(@Param("id") long id);

    @Query("select a.imagePath from Author a where a.id = :id")
    String getAuthorImagePath(@Param("id") long id);
}
