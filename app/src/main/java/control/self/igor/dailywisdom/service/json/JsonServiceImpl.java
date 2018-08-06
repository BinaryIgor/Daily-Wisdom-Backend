package control.self.igor.dailywisdom.service.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonServiceImpl implements JsonService {

    private ObjectMapper objectMapper;

    public JsonServiceImpl(ObjectMapper objectMapper) {
	this.objectMapper = objectMapper;
    }

    @Override
    public <T> T deserialize(String json, Class<T> clazz) {
	try {
	    return objectMapper.readValue(json, clazz);
	} catch (IOException exception) {
	    exception.printStackTrace();
	    return null;
	}
    }

    @Override
    public <T> T deserialize(InputStream inputStream, Class<T> clazz) {
	try {
	    return objectMapper.readValue(inputStream, clazz);
	} catch (IOException exception) {
	    exception.printStackTrace();
	    return null;
	}
    }

    @Override
    public <T> List<T> deserializeList(String json, Class<T> clazz) {
	try {
	    return objectMapper.readValue(json, new TypeReference<List<T>>() {
	    });
	} catch (IOException exception) {
	    exception.printStackTrace();
	    return null;
	}
    }

    @Override
    public String serialize(Object object) {
	try {
	    return objectMapper.writeValueAsString(object);
	} catch (IOException exception) {
	    exception.printStackTrace();
	    return null;
	}
    }
}
