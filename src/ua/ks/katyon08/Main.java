package ua.ks.katyon08;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        HashTable<Integer, Integer> table = new HashTable<Integer, Integer>();
        HashSet
        Random rnd = new Random();
        int x;
        for (int i = 0; i < 20; i++) {
            table.put(x = rnd.nextInt(30), x);
        }
        for (int i = 0; i < 10; i++) {
            table.remove(rnd.nextInt(30));
        }
        System.out.println(table.toStringDevelopMode() + "/n______________");


    }
}
