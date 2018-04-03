package control.self.igor.dailywisdom.repository.abstraction;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import control.self.igor.dailywisdom.entity.Category;
import control.self.igor.dailywisdom.entity.Quote;

public interface CategoryQuoteRepository extends CrudRepository<Quote, Long> {

    @Query("select c from Category c where c.id = :id")
    Category getCategory(@Param("id") long id);

    @Query("select q from Quote q join q.categories as c where c.id = :categoryId and q.id = :quoteId")
    Quote getQuote(@Param("categoryId") long categoryId, @Param("quoteId") long quoteId);

    @Query("select q from Quote q join q.categories as c where c.id = :id")
    List<Quote> getQuotes(@Param("id") long id);

    @Query("select q from Quote q join q.categories as c where c.id = :id")
    Page<Quote> getQuotes(@Param("id") long id, Pageable pageable);

    @Query("select count(q) from Quote q join q.categories as c where c.id = :id")
    long countQuotes(@Param("id") long id);

    @Query("select q from Quote q join q.categories as c where c.id = :id and q.content like %:content%")
    List<Quote> searchQuotes(@Param("id") long id, @Param("content") String content);

    @Query("select q from Quote q join q.categories as c where c.id = :id and q.content like %:content%")
    Page<Quote> searchQuotes(@Param("id") long id, @Param("content") String content, Pageable pageable);

    @Query("select count(q) from Quote q join q.categories as c where c.id = :id and q.content like %:content%")
    long countFoundQuotes(@Param("id") long id, @Param("content") String content);

    @Query("select case when count(q)>0  then true else false end from Quote q join q.categories as c where c.id = :categoryId and q.id = :quoteId")
    boolean hasQuote(@Param("categoryId") long categoryId, @Param("quoteId") long quoteId);
}
