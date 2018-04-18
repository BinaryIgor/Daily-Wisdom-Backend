package control.self.igor.dailywisdom.model.search;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuoteSearchCriteria {

    private String content;
    private List<Long> authorsIds;
    private List<Long> categoriesIds;

    @JsonCreator
    public QuoteSearchCriteria(@JsonProperty("content") String content,
	    @JsonProperty("authorsIds") List<Long> authorsIds,
	    @JsonProperty("categoriesIds") List<Long> categoriesIds) {
	this.content = content;
	this.authorsIds = authorsIds;
	this.categoriesIds = categoriesIds;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public List<Long> getAuthorsIds() {
	return authorsIds;
    }

    public void setAuthorsIds(List<Long> authorsIds) {
	this.authorsIds = authorsIds;
    }

    public List<Long> getCategoriesIds() {
	return categoriesIds;
    }

    public void setCategoriesIds(List<Long> categoriesIds) {
	this.categoriesIds = categoriesIds;
    }

    @JsonIgnore
    public boolean isSet() {
	return (content != null && !content.isEmpty()) || (authorsIds != null && !authorsIds.isEmpty())
		|| (categoriesIds != null && !categoriesIds.isEmpty());
    }

}
