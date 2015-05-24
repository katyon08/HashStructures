import org.junit.Test;
import ua.ks.katyon08.HashTable;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import static junit.framework.TestCase.assertEquals;

public class HashTableTest {
    @Test
    public void testPutAll() {
        HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
        Map<Integer, String> map1 = new TreeMap<>(), map2 = new TreeMap<>();
        Random rnd = new Random();
        Integer count = rnd.nextInt(100), x;
        for (int i = 0; i < count; i++) {
            map1.put(x = rnd.nextInt(1000), String.valueOf(x));
            hashTable.put(x, String.valueOf(x));
        }
        for (Integer y : map1.keySet()) {
            map2.put(y, hashTable.get(y));
        }
        assertEquals(map1, map2);
    }
}
