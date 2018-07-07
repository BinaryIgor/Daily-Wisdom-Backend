package control.self.igor.dailywisdom.entity;

import java.util.List;

public interface QuoteOwner extends Identifiable {
    List<Quote> getQuotes();
}
