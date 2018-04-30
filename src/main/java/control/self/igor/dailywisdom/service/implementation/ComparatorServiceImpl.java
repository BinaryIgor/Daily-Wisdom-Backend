package control.self.igor.dailywisdom.service.implementation;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import control.self.igor.dailywisdom.service.abstraction.ComparatorService;

@Service
public class ComparatorServiceImpl implements ComparatorService {

    @Override
    public <T extends Comparable<T>> boolean compareLists(List<T> firstList, List<T> secondList) {
	if ((firstList == null || firstList.isEmpty()) && (secondList == null || secondList.isEmpty())) {
	    return true;
	}
	if (firstList.size() != secondList.size()) {
	    return false;
	}
	Collections.sort(firstList);
	Collections.sort(secondList);
	T firstElement, secondElement;
	for (int i = 0; i < firstList.size(); i++) {
	    firstElement = firstList.get(i);
	    secondElement = secondList.get(i);
	    if (!firstElement.equals(secondElement)) {
		return false;
	    }
	}
	return true;
    }

}
