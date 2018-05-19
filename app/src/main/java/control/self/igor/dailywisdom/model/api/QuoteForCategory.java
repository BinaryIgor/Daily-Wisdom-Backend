package control.self.igor.dailywisdom.model.api;

public class QuoteForCategory {

    private long id;
    private String content;

    public QuoteForCategory(long id, String content) {
	this.id = id;
	this.content = content;
    }

    public long getId() {
	return id;
    }

    public String getContent() {
	return content;
    }

}
