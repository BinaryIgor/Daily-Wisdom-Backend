package control.self.igor.dailywisdom.search;

import java.util.List;

import control.self.igor.dailywisdom.entity.Author;
import control.self.igor.dailywisdom.entity.Category;

public class QuotesSearchCriteria {

	private String content;
	private List<Author> authors;
	private List<Category> categories;

	public QuotesSearchCriteria() {

	}

	public QuotesSearchCriteria(String content, List<Author> authors, List<Category> categories) {
		this.content = content;
		this.authors = authors;
		this.categories = categories;
	}

	public String getContent() {
		return content;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public boolean areCriteriaSet() {
		return (content != null && !content.isEmpty()) || (authors != null && !authors.isEmpty())
				|| (categories != null && !categories.isEmpty());
	}

}
