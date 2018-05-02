package control.self.igor.dailywisdom.util;

public class TestComparable implements Comparable<TestComparable> {

    private long id;

    public TestComparable(long id) {
	this.id = id;
    }

    public void setId(long id) {
	this.id = id;
    }

    @Override
    public int compareTo(TestComparable testComparable) {
	long idsDifference = id - testComparable.id;
	if (idsDifference > 0) {
	    return 1;
	} else if (idsDifference < 0) {
	    return -1;
	} else {
	    return 0;
	}
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	TestComparable other = (TestComparable) obj;
	if (id != other.id)
	    return false;
	return true;
    }

}
