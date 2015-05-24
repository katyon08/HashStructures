//import junit.framework.Assert;
import org.junit.Test;
import ua.ks.katyon08.HashTable;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

public class HashTableTest {

    @Test
    public void testPut() {
        HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
        String s = "ten";
        hashTable.put(10, s);
        assertEquals(hashTable.get(10), s);
    }

    @Test
    public void testPutAll() {
        HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
        Map<Integer, String> map1 = new TreeMap<>(), map2 = new TreeMap<>();
        Random rnd = new Random();
        Integer count = rnd.nextInt(50), x;
        for (int i = 0; i < count; i++) {
            map1.put(x = rnd.nextInt(100), String.valueOf(x));
            hashTable.put(x, String.valueOf(x));
        }
        for (Integer y : map1.keySet()) {
            map2.put(y, hashTable.get(y));
        }
        assertEquals(map1, map2);
    }

    @Test
    public void testClear() {
        Random rnd = new Random();
        HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
        int x;
        for (int i = 0; i < rnd.nextInt(50); i++) {
            x = rnd.nextInt(100);
            hashTable.put(x, String.valueOf(x));
        }
        hashTable.clear();
        assertEquals(hashTable.size(), 0);
    }




}
