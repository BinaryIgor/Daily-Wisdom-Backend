package control.self.igor.dailywisdom.service.implementation;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import control.self.igor.dailywisdom.service.abstraction.JsonService;

@Service
public class JsonServiceImpl implements JsonService {

    public static final Logger LOGGER = Logger.getLogger(JsonServiceImpl.class.getSimpleName());

    @Autowired
    private ObjectMapper objectMapper;

    public <T> T deserialize(String json, Class<T> clazz) {
	try {
	    return objectMapper.readValue(json, clazz);
	} catch (IOException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    return null;
	}
    }

    public String serialize(Object object) {
	try {
	    return objectMapper.writeValueAsString(object);
	} catch (IOException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    return null;
	}
    }
}
