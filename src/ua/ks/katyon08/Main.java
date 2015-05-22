package ua.ks.katyon08;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        HashTable<Integer, Integer> table = new HashTable<Integer, Integer>();
        Random rnd = new Random();
        int x;
        for (int i = 0; i < 20; i++) {
            table.put(x = rnd.nextInt(30), x);
        }
        for (int i = 0; i < 10; i++) {
            table.remove(rnd.nextInt(30));
        }
        System.out.println(table.toStringDevelopMode() + "/n______________");
        java.util.TreeMap treemap;


    }
}
