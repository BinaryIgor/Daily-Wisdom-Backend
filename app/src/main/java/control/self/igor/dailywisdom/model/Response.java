package control.self.igor.dailywisdom.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

    public static final String OK = "OK";
    public static final String EXISTS = "Given entity already exists.";
    public static final String NOT_EXIST = "Entity of given id does not exist.";
    private String status;
    private Data data;

    @JsonCreator
    public Response(@JsonProperty("status") String status) {
	this.status = status;
    }

    public Response(String status, Data data) {
	this.status = status;
	this.data = data;
    }

    public static Response okWithId(long id) {
	return new Response(OK, new Data(id));
    }

    public String getStatus() {
	return status;
    }

    public Data getData() {
	return data;
    }

    @JsonIgnore
    public boolean isSuccessful() {
	return OK.equals(status);
    }

}
