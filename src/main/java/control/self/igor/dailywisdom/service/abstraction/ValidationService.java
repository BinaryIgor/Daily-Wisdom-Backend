package control.self.igor.dailywisdom.service.abstraction;

public interface ValidationService {
    boolean validatePageRequest(Integer page, Integer size);

    boolean validateIds(long... ids);
}
