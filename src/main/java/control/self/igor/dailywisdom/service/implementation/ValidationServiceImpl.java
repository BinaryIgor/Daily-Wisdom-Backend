package control.self.igor.dailywisdom.service.implementation;

import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.service.abstraction.ValidationService;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean validatePageRequest(Integer page, Integer size) {
	return (page == null && size == null) || (page != null && page < 1 || size == null)
		|| (size != null && size < 1);
    }

    @Override
    public boolean validateIds(long... ids) {
	if (ids == null || ids.length < 1) {
	    return true;
	}

	for (long id : ids) {
	    if (id < 1) {
		return false;
	    }
	}
	return true;
    }

}
