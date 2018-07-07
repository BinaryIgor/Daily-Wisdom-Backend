package control.self.igor.dailywisdom.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import control.self.igor.dailywisdom.entity.Quote;

public interface EntityQuoteRepository extends CrudRepository<Quote, Long>, JpaSpecificationExecutor<Quote> {

}
