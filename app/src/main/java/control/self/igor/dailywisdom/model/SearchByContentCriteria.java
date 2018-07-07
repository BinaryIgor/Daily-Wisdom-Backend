package control.self.igor.dailywisdom.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchByContentCriteria {

    private String content;

    @JsonCreator
    public SearchByContentCriteria(@JsonProperty("content") String content) {
	this.content = content;
    }

    public String getContent() {
	return content;
    }

    @Override
    public String toString() {
	return "SearchByContentCriteria [content=" + content + ", getContent()=" + getContent() + ", getClass()="
		+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

}
