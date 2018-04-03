package control.self.igor.dailywisdom.model.api;

import control.self.igor.dailywisdom.entity.Author;

public class AuthorMin {

	private long id;
	private String name;

	public AuthorMin() {

	}

	public AuthorMin(Author author) {
		this.id = author.getId();
		this.name = author.getName();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
