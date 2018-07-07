package control.self.igor.dailywisdom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import control.self.igor.dailywisdom.json.View;

@Entity
@Table(name = "author_description")
@JsonIgnoreProperties(value = { "author" })
public class AuthorDescription implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(View.AuthorDetails.class)
    private long id;

    @OneToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @NotNull(message = "is required")
    @Size(min = 10, message = "description has to have at least 10 characters")
    @Column(name = "description")
    @JsonView(View.AuthorDetails.class)
    private String description;

    public AuthorDescription(Author author, String description) {
	this.author = author;
	this.description = description;
    }

    public AuthorDescription() {

    }

    @Override
    public long getId() {
	return id;
    }

    public Author getAuthor() {
	return author;
    }

    public String getDescription() {
	return description;
    }

    public void setId(long id) {
	this.id = id;
    }

    public void setAuthor(Author author) {
	this.author = author;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((author == null) ? 0 : author.hashCode());
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + (int) (id ^ (id >>> 32));
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
	AuthorDescription other = (AuthorDescription) obj;
	if (author == null) {
	    if (other.author != null)
		return false;
	} else if (!author.equals(other.author))
	    return false;
	if (description == null) {
	    if (other.description != null)
		return false;
	} else if (!description.equals(other.description))
	    return false;
	if (id != other.id)
	    return false;
	return true;
    }

}
