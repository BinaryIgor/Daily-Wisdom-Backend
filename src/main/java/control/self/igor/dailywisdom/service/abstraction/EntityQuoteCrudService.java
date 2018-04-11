package control.self.igor.dailywisdom.service.abstraction;

import java.util.List;

import control.self.igor.dailywisdom.entity.Quote;
import control.self.igor.dailywisdom.entity.QuoteOwner;

public interface EntityQuoteCrudService<Entity extends QuoteOwner> {

    List<Quote> getQuotes(long id);

    List<Quote> getQuotes(long id, Integer page, Integer size);

    long countQuotes(long id);

    Quote getQuote(long id, long quoteId);

    long createQuote(long id, Quote quote);

    boolean updateQuote(long id, Quote quote);

    boolean deleteQuote(long id, long quoteId);

    boolean quoteExists(long id, long quoteId);

    Entity getEntity(long id);

}
