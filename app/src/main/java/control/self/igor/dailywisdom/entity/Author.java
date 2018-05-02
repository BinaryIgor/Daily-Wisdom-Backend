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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import control.self.igor.dailywisdom.json.View;

@Entity
@Table(name = "author")
public class Author implements QuoteOwner, Searchable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(value = { View.List.class, View.AuthorDetails.class })
    private long id;

    @NotNull(message = "Author name must have at least 3 characters")
    @Size(min = 3, message = "Author name must have at least 3 characters")
    @Column(name = "name")
    @JsonView(value = { View.List.class, View.AuthorDetails.class })
    private String name;

    @Column(name = "image_path")
    private String imagePath;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.ALL)
    @JsonView(View.AuthorDetails.class)
    private AuthorDescription authorDescription;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.ALL)
    @JsonView(View.AuthorDetails.class)
    private List<Quote> quotes;

    public Author() {

    }

    public Author(String name, String imagePath) {
	this.name = name;
	this.imagePath = imagePath;
    }

    @Override
    public long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public String getImagePath() {
	return imagePath;
    }

    public void setId(long id) {
	this.id = id;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setImagePath(String imagePath) {
	this.imagePath = imagePath;
    }

    public AuthorDescription getAuthorDescription() {
	return authorDescription;
    }

    @Override
    public List<Quote> getQuotes() {
	return quotes;
    }

    public void setAuthorDescription(AuthorDescription authorDescription) {
	this.authorDescription = authorDescription;
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
	if (!(ob instanceof Author)) {
	    return false;
	}
	Author other = (Author) ob;
	if (id == other.id) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public String toString() {
	return "Author [id=" + id + ", name=" + name + "]";
    }

    public static List<Long> getAuthorsIds(@NotNull List<Author> authors) {
	List<Long> ids = new ArrayList<>();
	for (Author author : authors) {
	    ids.add(author.getId());
	}
	return ids;
    }

}
