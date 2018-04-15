
package control.self.igor.dailywisdom.repository.abstraction;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.AuthorDescription;

public interface AuthorRepository extends SearchingByNameRepository<Author> {

    @Query("select a.authorDescription from Author a where a.id = :id")
    AuthorDescription getAuthorDescription(@Param("id") long id);

    @Query("select a.image from Author a where a.id = :id")
    byte[] getAuthorImage(@Param("id") long id);
}
