
package control.self.igor.dailywisdom.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import control.self.igor.dailywisdom.entity.Author;

public interface AuthorRepository extends PagingAndSortingRepository<Author, Long>, JpaSpecificationExecutor<Author> {

    @Query("select a.imagePath from Author a where a.id = :id")
    String getAuthorImagePath(@Param("id") long id);

    @Modifying
    @Query("update Author a set a.imagePath = :imagePath where a.id = :id")
    int updateImagePath(@Param("id") long id, @Param("imagePath") String imagePath);
}
