import org.junit.Test;
import ua.ks.katyon08.TreeMap;

public class TreeMapTest {

	private final Integer[] simpleArray =
			{ 80, 54, 139, 96, 130, 95, 104, 67, 106, 144, 17, 28, 35, 67, 73, 81, 95, 111, 127 };

	@Test
	public void putTest() {
		TreeMap<Integer, Integer> tree = new TreeMap<Integer, Integer>();
		for (int i = 0; i < simpleArray.length; i++) {
			tree.put(simpleArray[i], simpleArray[i]);
		}
		String s = tree.toString();
		System.out.println(s);
		System.out.println();
	}
}
