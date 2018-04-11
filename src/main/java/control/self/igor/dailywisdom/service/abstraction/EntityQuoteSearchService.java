package control.self.igor.dailywisdom.service.abstraction;

import java.util.List;

import control.self.igor.dailywisdom.entity.Quote;

public interface EntityQuoteSearchService {

    List<Quote> searchQuotes(long id, Integer page, Integer size, String content);

    long countFoundQuotes(long id, String content);
}
