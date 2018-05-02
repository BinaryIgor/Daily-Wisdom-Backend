package control.self.igor.dailywisdom.model.api;

import java.util.List;

public class Authors {

	private List<AuthorMin> authors;

	public Authors(List<AuthorMin> authors) {
		this.authors = authors;
	}

	public List<AuthorMin> getAuthors() {
		return authors;
	}

}
