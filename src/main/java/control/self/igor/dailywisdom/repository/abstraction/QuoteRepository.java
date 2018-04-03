
package control.self.igor.dailywisdom.repository.abstraction;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import control.self.igor.dailywisdom.entity.Quote;

public interface QuoteRepository extends PagingAndSortingRepository<Quote, Long> {

    @Query("select q from Quote q where q.content like %:content%")
    Page<Quote> searchQuotesByContent(@Param("content") String content, Pageable pageable);

    @Query("select q from Quote q where q.content like %:content%")
    List<Quote> searchQuotesByContent(@Param("content") String content);

    @Query("select count(q) from Quote q where q.content like %:content%")
    long countFoundQuotes(@Param("content") String content);
}
