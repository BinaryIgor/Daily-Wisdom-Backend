package control.self.igor.dailywisdom.model.api;

public class Response {

    public static final String OK = "OK";
    public static final String NOT_EXIST = "Entity of given id does not exist.";

    private String status;

    public Response(String status) {
	this.status = status;
    }

    public String getStatus() {
	return status;
    }

}
