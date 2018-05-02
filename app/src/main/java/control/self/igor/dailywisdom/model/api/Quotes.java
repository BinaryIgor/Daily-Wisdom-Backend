package control.self.igor.dailywisdom.model.api;

import java.util.List;

import control.self.igor.dailywisdom.entity.Quote;

public class Quotes {

	private List<Quote> quotes;

	public Quotes(List<Quote> quotes) {
		this.quotes = quotes;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

}
