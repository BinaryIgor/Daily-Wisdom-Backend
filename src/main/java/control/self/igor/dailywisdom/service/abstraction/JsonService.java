package control.self.igor.dailywisdom.service.abstraction;

public interface JsonService {
    <T> T deserialize(String json, Class<T> clazz);

    String serialize(Object object);
}
