package control.self.igor.dailywisdom.service.comparator;

import java.util.List;

public interface ComparatorService {
    <T extends Comparable<T>> boolean compareLists(List<T> firstList, List<T> secondList);
}
