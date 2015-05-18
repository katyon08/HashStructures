package ua.ks.katyon08;

import java.util.ArrayList;

public class HashTable<K,V>{
    float loadFactor;
    int capacity;

    ArrayList<V> table;
    int[] tableIndex;

    public HashTable(float loadFactor, int initialCapacity) {
        this.loadFactor = loadFactor;
        this.capacity = initialCapacity;
        tableIndex = new int[capacity];
        table = new ArrayList<V>(capacity);
        initTableIndex();

    }

    private void initTableIndex() {
        for (int i = 0; i < capacity; i++) {
            tableIndex[i] = -1;
        }
    }

    public HashTable(float loadFactor) {
        this(loadFactor, 128);
    }

    public HashTable(int initialCapacity) {
        this((float)0.75, initialCapacity);
    }

    public HashTable() {
        this((float) 0.75, 128);
    }

    private int localHash(int hash) {
        return hash % capacity;
    }


    public void put(K key, V value) {

    }
}