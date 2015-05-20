package ua.ks.katyon08;

import java.util.ArrayList;
import java.util.Hashtable;

public class Main {

    public static void main(String[] args) {
        HashTable<Integer, Integer> table = new HashTable<Integer, Integer>();
        for (int i = 0; i < 20; i++) {
            table.put((2*i+1), (2*i+1));
        }
        System.out.println(table);
    }
}
