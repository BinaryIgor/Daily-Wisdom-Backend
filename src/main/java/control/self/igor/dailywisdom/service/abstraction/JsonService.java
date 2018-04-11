package control.self.igor.dailywisdom.service.abstraction;

import java.util.List;

public interface JsonService {
    <T> T deserialize(String json, Class<T> clazz);

    <T> List<T> deserializeList(String json, Class<T> clazz);

    String serialize(Object object);
}
