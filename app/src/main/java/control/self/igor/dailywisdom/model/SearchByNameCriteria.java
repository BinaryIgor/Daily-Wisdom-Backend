package control.self.igor.dailywisdom.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchByNameCriteria {

    private String name;

    @JsonCreator
    public SearchByNameCriteria(@JsonProperty("name") String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    @Override
    public String toString() {
	return "SearchByNameCriteria [name=" + name + ", getName()=" + getName() + ", getClass()=" + getClass()
		+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

}
