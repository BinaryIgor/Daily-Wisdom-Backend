package control.self.igor.dailywisdom.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import control.self.igor.dailywisdom.json.View;

@Entity
@Table(name = "quote")
public class Quote implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(View.List.class)
    private long id;

    @Column(name = "content")
    @NotNull
    @Size(min = 10, message = "is required")
    @JsonView(View.List.class)
    private String content;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
	    CascadeType.REFRESH })
    @JoinTable(name = "category_quote", joinColumns = @JoinColumn(name = "quote_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
	    CascadeType.REFRESH })
    @JoinColumn(name = "author_id")
    @JsonView(View.List.class)
    private Author author;

    public Quote() {

    }

    public Quote(String content, Author author) {
	this.content = content;
	this.author = author;
    }

    public Quote(String content, List<Category> categories, Author author) {
	this.content = content;
	this.categories = categories;
	this.author = author;
    }

    @Override
    public long getId() {
	return id;
    }

    public String getContent() {
	return content;
    }

    public List<Category> getCategories() {
	return categories;
    }

    public Author getAuthor() {
	return author;
    }

    public void setId(long id) {
	this.id = id;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public void setCategories(List<Category> categories) {
	this.categories = categories;
    }

    public void setAuthor(Author author) {
	this.author = author;
    }

    public void addCategory(@NotNull Category category) {
	if (categories == null) {
	    categories = new ArrayList<>();
	}
	categories.add(category);
    }

    public boolean hasCategory(long categoryId) {
	if (categories == null || categories.isEmpty()) {
	    return false;
	}

	for (Category category : categories) {
	    if (category.getId() == categoryId) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = (int) (prime * id);
	if (content != null) {
	    result += content.length() * prime;
	}
	return result;
    }

    @Override
    public boolean equals(Object ob) {
	if (this == ob) {
	    return true;
	}
	if (ob == null) {
	    return false;
	}
	if (!(ob instanceof Quote)) {
	    return false;
	}
	Quote other = (Quote) ob;
	if (id == other.id) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public String toString() {
	return String.valueOf(id);
    }

}
