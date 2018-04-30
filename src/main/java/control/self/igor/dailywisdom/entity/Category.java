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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import control.self.igor.dailywisdom.json.View;

@Entity
@Table(name = "category")
public class Category implements QuoteOwner, Searchable, Comparable<Category> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(value = { View.List.class, View.AuthorDetails.class })
    private long id;

    @NotNull(message = "is required")
    @Size(min = 3, message = "is required")
    @Column(name = "name")
    @JsonView(value = { View.List.class, View.AuthorDetails.class })
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(name = "category_quote", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "quote_id"))
    private List<Quote> quotes;

    public Category(String name) {
	this.name = name;
    }

    public Category() {

    }

    public Category(long id, String name) {
	this.id = id;
	this.name = name;
    }

    @Override
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

    @Override
    public List<Quote> getQuotes() {
	return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
	this.quotes = quotes;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = (int) (prime * id);
	if (name != null) {
	    result += name.length() * prime;
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
	if (!(ob instanceof Category)) {
	    return false;
	}
	Category other = (Category) ob;
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

    public static List<Long> getCategoriesIds(@NotNull List<Category> categories) {
	List<Long> ids = new ArrayList<>();
	for (Category category : categories) {
	    ids.add(category.getId());
	}
	return ids;
    }

    @Override
    public int compareTo(Category category) {
	if (category == null) {
	    return 1;
	}
	long idsDifference = id - category.getId();
	if (idsDifference > 0) {
	    return 1;
	} else if (idsDifference < 0) {
	    return -1;
	} else {
	    return 0;
	}
    }

}
