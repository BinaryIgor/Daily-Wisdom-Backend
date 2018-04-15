package control.self.igor.dailywisdom.service.abstraction;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import control.self.igor.dailywisdom.entity.Quote;

public abstract class AbstractQuoteSearchService {

    private static final int DEFAULT_PAGE_SIZE = 50;
    private JpaSpecificationExecutor<Quote> executor;

    public AbstractQuoteSearchService(JpaSpecificationExecutor<Quote> executor) {
	this.executor = executor;
    }

    public List<Quote> searchQuotes(Integer page, Integer size, Specification<Quote> specification) {
	Sort sort = new Sort(Direction.ASC, "id");
	if ((page == null || page < 1) && (size == null || size < 1)) {
	    return executor.findAll(specification);
	}
	page = (page == null || page < 1) ? 1 : page;
	size = (size == null || size < 1) ? DEFAULT_PAGE_SIZE : size;
	return executor.findAll(specification, PageRequest.of(page - 1, size, sort)).getContent();
    }

    public long countFoundQuotes(Specification<Quote> specification) {
	return executor.count(specification);
    }

}
