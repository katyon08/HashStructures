//import junit.framework.Assert;

import org.junit.Test;
import ua.ks.katyon08.HashTable;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HashTableTest {

    @Test
    public void testPut() {
        HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
        String s = "ten";
        hashTable.put(10, s);
        assertEquals(hashTable.get(10), s);
    }

  /* @Test
    public void testPutAll() {
        HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
        Map<Integer, String> map1 = new TreeMap<Integer, String>(), map2 = new TreeMap<Integer, String>();
        Random rnd = new Random();
        Integer count = rnd.nextInt(100), x;
        System.out.println("count = " + count);
        for (int i = 0; i < count; i++) {
            if (!map1.containsKey(x = rnd.nextInt(100))) {
                map1.put(x, String.valueOf(x));
                hashTable.put(x, String.valueOf(x));
                System.out.println("i = " + i + " || putting " + x);
            }
        }
        for (Integer y : map1.keySet()) {
            map2.put(y, hashTable.get(y));
        }
        //System.out.println("size = " + hashTable.map1.put(x = rnd.nextInt(100).size() + "\n" + hashTable.toStringDevelopMode() + "\n ____________ \n" + map1.toString() + "\n _____________ \n" + map2.toString());
        assertEquals(map1, map2);
    }
    /**/

    @Test
    public void testPutAllAnother() {
        Random rnd = new Random();
        Map<Integer, String> map = new java.util.TreeMap<Integer, String>();
        HashTable<Integer, String> hashTable = new ua.ks.katyon08.HashTable<Integer, String>();
        int x, count = rnd.nextInt(99) + 1;
        for (int i = 0; i < count; i++) {
            if (!map.containsKey(x = rnd.nextInt(100))) {
                System.out.println(x + " = " + String.valueOf(x));
                map.put(x, String.valueOf(x));
                }
        }
        hashTable.putAll(map);
        boolean right = true;
        for (Integer key : map.keySet()) {
            right = right && hashTable.containsKey(key);
        }
        assertTrue(right);
    }

    @Test
    public void testClear() {
        Random rnd = new Random();
        HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
        int x;
        for (int i = 0; i < rnd.nextInt(50); i++) {
            x = rnd.nextInt(100);
            hashTable.put(x, String.valueOf(x));
            //System.out.println(x + " = " + String.valueOf(x));
        }
        hashTable.clear();
        assertEquals(hashTable.size(), 0);
    }

    @Test
    public void cloneTest() {
        Random rnd = new Random();
        HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
        int x;
        for (int i = 0; i < rnd.nextInt(50); i++) {
            x = rnd.nextInt(100);
            hashTable.put(x, String.valueOf(x));
        }
        assertEquals(hashTable, hashTable.clone());
    }

    /*@Test
    public void getTest() {

    }*/


}
