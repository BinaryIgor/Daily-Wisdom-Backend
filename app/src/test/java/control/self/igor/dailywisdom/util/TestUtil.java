package control.self.igor.dailywisdom.util;

public class TestUtil {

    public static int getRandomNumber(int min, int max) {
	return min + ((int) (Math.random() * max));
    }

    public static String createPageUrl(String baseUrl, int page, int size) {
	return baseUrl + "?page=" + page + "&size=" + size;
    }
}
