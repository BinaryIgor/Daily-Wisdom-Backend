package control.self.igor.dailywisdom.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
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
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((quotes == null) ? 0 : quotes.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Category other = (Category) obj;
	if (id != other.id)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (quotes == null) {
	    if (other.quotes != null)
		return false;
	} else if (!quotes.equals(other.quotes))
	    return false;
	return true;
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
