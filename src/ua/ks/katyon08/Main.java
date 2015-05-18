package ua.ks.katyon08;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        HashTable<Integer, String> table = new HashTable<Integer, String>(5);
        table.put(10, "Ten");
        table.put(8, "Eight");
        table.put(1, "One");
        table.put(4, "Four");
        table.put(5, "Five");
    }
}
