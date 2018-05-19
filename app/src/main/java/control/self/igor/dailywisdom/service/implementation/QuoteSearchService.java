package control.self.igor.dailywisdom.service.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.model.search.QuoteSearchCriteria;
import control.self.igor.dailywisdom.repository.abstraction.QuoteRepository;
import control.self.igor.dailywisdom.repository.abstraction.SpecificationFactory;
import control.self.igor.dailywisdom.service.abstraction.AbstractSearchService;
import control.self.igor.dailywisdom.service.abstraction.SearchService;

@Service
@Transactional
public class QuoteSearchService extends AbstractSearchService<Quote>
	implements SearchService<Quote, QuoteSearchCriteria> {

    @Autowired
    public QuoteSearchService(QuoteRepository repository) {
	super(repository);
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
