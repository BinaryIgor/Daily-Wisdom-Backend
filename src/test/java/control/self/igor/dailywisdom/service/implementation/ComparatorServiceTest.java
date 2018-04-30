package control.self.igor.dailywisdom.service.implementation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.service.abstraction.ComparatorService;
import control.self.igor.dailywisdom.util.MockUtil;
import control.self.igor.dailywisdom.util.TestComparable;

@RunWith(SpringRunner.class)
public class ComparatorServiceTest {

    @TestConfiguration
    static class ComparatorServiceTestConfiguration {

	@Bean
	public ComparatorService comparatorService() {
	    return new ComparatorServiceImpl();
	}
    }

    @Autowired
    private ComparatorService comparatorService;

    private List<TestComparable> firstList;
    private List<TestComparable> secondList;

    @Before
    public void setup() {
	firstList = MockUtil.createComparableList();
	secondList = MockUtil.createComparableList();
    }

    @Test
    public void compareEqualLists() {
	assertTrue(comparatorService.compareLists(firstList, secondList));
	assertTrue(comparatorService.compareLists(null, null));
	firstList.clear();
	secondList.clear();
	assertTrue(comparatorService.compareLists(firstList, secondList));
    }

    @Test
    public void compareUnequalLists() {
	secondList.remove(1);
	assertFalse(comparatorService.compareLists(firstList, secondList));
	firstList.clear();
	assertFalse(comparatorService.compareLists(firstList, secondList));
    }

}
