package control.self.igor.dailywisdom.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EntityCounter {

    private long size;

    @JsonCreator
    public EntityCounter(@JsonProperty("size") long size) {
	this.size = size;
    }

    public long getSize() {
	return size;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (size ^ (size >>> 32));
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
	EntityCounter other = (EntityCounter) obj;
	if (size != other.size) {
	    return false;
	}
	;
	return true;
    }

}
