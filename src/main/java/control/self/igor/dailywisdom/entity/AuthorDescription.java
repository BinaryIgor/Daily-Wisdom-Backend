package control.self.igor.dailywisdom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "author_description")
@JsonIgnoreProperties(value = { "id", "author" })
public class AuthorDescription implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @NotBlank(message = "is required")
    @Size(min = 10, message = "description has to have at least 10 characters!")
    @Column(name = "description")
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

}
