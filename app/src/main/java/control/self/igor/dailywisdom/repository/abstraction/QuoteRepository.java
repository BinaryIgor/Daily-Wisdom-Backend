
package control.self.igor.dailywisdom.repository.abstraction;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import control.self.igor.dailywisdom.entity.Quote;

public interface QuoteRepository extends PagingAndSortingRepository<Quote, Long>, JpaSpecificationExecutor<Quote> {

}
