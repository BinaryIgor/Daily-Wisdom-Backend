package control.self.igor.dailywisdom.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import control.self.igor.dailywisdom.json.View;

@Entity
@Table(name = "author")
@JsonIgnoreProperties(value = { "picture", "quotes", "authorDescription" })
public class Author implements QuoteOwner {

    private static final Logger LOGGER = Logger.getLogger(Author.class.getSimpleName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(View.List.class)
    private long id;

    @NotNull(message = "Author name must have at least 3 characters")
    @Size(min = 3, message = "Author name must have at least 3 characters")
    @Column(name = "name")
    @JsonView(View.List.class)
    private String name;

    @NotNull
    @Column(name = "image")
    private byte[] image;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.ALL)
    private AuthorDescription authorDescription;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.ALL)
    private List<Quote> quotes;

    public Author() {

    }

    public Author(String name, byte[] picture) {
	this.name = name;
	this.image = picture;
    }

    @Override
    public long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public byte[] getImage() {
	return image;
    }

    public void setId(long id) {
	this.id = id;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setImage(byte[] picture) {
	this.image = picture;
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
	LOGGER.info("Checking hashCOde!");
	final int prime = 31;
	int result = (int) (prime * id);
	if (name != null) {
	    result += name.length() * prime;
	}
	return result;
    }

    @Override
    public boolean equals(Object ob) {
	LOGGER.info("Checking equals!");
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
	return String.valueOf(id);
    }

    public static List<Long> getAuthorsIds(@NotNull List<Author> authors) {
	List<Long> ids = new ArrayList<>();
	for (Author author : authors) {
	    ids.add(author.getId());
	}
	return ids;
    }

}
