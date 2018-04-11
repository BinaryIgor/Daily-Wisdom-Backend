package control.self.igor.dailywisdom.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

    public static final String OK = "OK";
    public static final String NOT_EXIST = "Entity of given id does not exist.";

    private String status;

    @JsonCreator
    public Response(@JsonProperty("status") String status) {
	this.status = status;
    }

    public String getStatus() {
	return status;
    }

    @JsonIgnore
    public boolean isSuccessful() {
	return OK.equals(status);
    }

}
