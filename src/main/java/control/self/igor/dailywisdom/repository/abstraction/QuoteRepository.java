
package control.self.igor.dailywisdom.repository.abstraction;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import control.self.igor.dailywisdom.entity.Quote;

public interface QuoteRepository extends PagingAndSortingRepository<Quote, Long>, JpaSpecificationExecutor<Quote> {

    // @Query("select q from Quote q where q.content like %:content%")
    // Page<Quote> searchQuotesByContent(@Param("content") String content, Pageable
    // pageable);
    //
    // @Query("select q from Quote q where author.id in :ids")
    // Page<Quote> searchQuotesByAuthorsIds(@Param("ids") List<Long> ids, Pageable
    // pageable);
    //
    // @Query("select q from Quote q inner join q.categories as c where c.id in
    // :ids")
    // Page<Quote> searchQuotesByCategoriesIds(@Param("ids") List<Long> ids,
    // Pageable pageable);
    //
    // @Query("select q from Quote q where q.content like %:content%")
    // List<Quote> searchQuotesByContent(@Param("content") String content);
    //
    // @Query("select count(q) from Quote q where q.content like %:content%")
    // long countFoundQuotes(@Param("content") String content);
}
