package ua.ks.katyon08;

import java.util.ArrayList;

public class HashTable<K,V>{
    private float loadFactor;
    private int capacity;
    private V[] table;
    private boolean[] tableValidity;

    @SuppressWarnings("unchecked")
    private void GenericArray(int sz) {
        table = (V[])new Object[sz];
    }

    private void putToTable(int index, V value) {
        table[index] = value;
        tableValidity[index] = true;
    }

    private V getFromTable (int index) throws InvalidIndexExpression{
        if (tableValidity[index]) {
            return table[index];
            }
        else {
            throw new InvalidIndexExpression(index, tableValidity);
            }
    }

    public HashTable(float loadFactor, int initialCapacity) {
        this.loadFactor = loadFactor;
        this.capacity = initialCapacity;
        GenericArray(capacity);
        tableValidity = new boolean[capacity];

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

class InvalidIndexExpression extends Exception {

    public InvalidIndexExpression(int index, boolean[] tableValidity) {
        super();
        System.out.println("Invalid index. Fatal error");
    }
}