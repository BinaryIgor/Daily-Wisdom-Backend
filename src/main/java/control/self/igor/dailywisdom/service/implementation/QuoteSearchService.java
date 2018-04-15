package control.self.igor.dailywisdom.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.model.search.QuoteSearchCriteria;
import control.self.igor.dailywisdom.repository.abstraction.QuoteRepository;
import control.self.igor.dailywisdom.repository.abstraction.QuoteSpecification;
import control.self.igor.dailywisdom.service.abstraction.AbstractQuoteSearchService;
import control.self.igor.dailywisdom.service.abstraction.SearchService;

@Service
public class QuoteSearchService extends AbstractQuoteSearchService
	implements SearchService<Quote, QuoteSearchCriteria> {

    @Autowired
    public QuoteSearchService(QuoteRepository repository) {
	super(repository);
    }

    @Override
    public List<Quote> searchEntities(Integer page, Integer size, QuoteSearchCriteria searchCriteria) {
	return searchQuotes(page, size, QuoteSpecification.searchQuotes(searchCriteria));
    }

    @Override
    public long countFoundEntities(QuoteSearchCriteria searchCriteria) {
	return countFoundQuotes(QuoteSpecification.searchQuotes(searchCriteria));
    }

}
