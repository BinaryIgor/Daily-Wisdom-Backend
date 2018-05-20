package control.self.igor.dailywisdom.service.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import control.self.igor.dailywisdom.service.abstraction.JsonService;

@Service
public class JsonServiceImpl implements JsonService {

    public static final Logger LOGGER = Logger.getLogger(JsonServiceImpl.class.getSimpleName());

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public <T> T deserialize(String json, Class<T> clazz) {
	try {
	    return objectMapper.readValue(json, clazz);
	} catch (IOException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    return null;
	}
    }

    @Override
    public <T> T deserialize(InputStream inputStream, Class<T> clazz) {
	try {
	    return objectMapper.readValue(inputStream, clazz);
	} catch (IOException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    return null;
	}
    }

    @Override
    public <T> List<T> deserializeList(String json, Class<T> clazz) {
	try {
	    return objectMapper.readValue(json, new TypeReference<List<T>>() {
	    });
	} catch (IOException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    return null;
	}
    }

    @Override
    public String serialize(Object object) {
	try {
	    return objectMapper.writeValueAsString(object);
	} catch (IOException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    return null;
	}
    }
}
