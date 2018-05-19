package control.self.igor.dailywisdom.model.search;

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
}
