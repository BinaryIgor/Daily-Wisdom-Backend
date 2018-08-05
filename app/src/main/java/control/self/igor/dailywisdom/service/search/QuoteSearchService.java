package control.self.igor.dailywisdom.service.search;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.model.QuoteSearchCriteria;
import control.self.igor.dailywisdom.repository.QuoteRepository;
import control.self.igor.dailywisdom.specification.SpecificationFactory;

@Service
@Transactional
public class QuoteSearchService extends AbstractSearchService<Quote>
	implements SearchService<Quote, QuoteSearchCriteria> {

    @Autowired
    public QuoteSearchService(QuoteRepository repository) {
	super(repository, "id");
    }

    @Override
    public List<Quote> searchEntities(Integer page, Integer size, QuoteSearchCriteria searchCriteria) {
	return searchEntities(page, size, SpecificationFactory.searchQuotes(searchCriteria));
    }

    @Override
    public long countFoundEntities(QuoteSearchCriteria searchCriteria) {
	return countFoundEntities(SpecificationFactory.searchQuotes(searchCriteria));
    }

}
