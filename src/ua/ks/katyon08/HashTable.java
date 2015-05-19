package ua.ks.katyon08;

import java.util.Dictionary;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashTable<K,V> {
    private float loadFactor;
    private int capacity, busyness = 0;
    private V[] table;
    private K[] keyTable;
    private boolean[] tableValidity;

    @SuppressWarnings("unchecked")
    private V[] genericArray(int sz) {
        V[] newTable = (V[])new Object[sz];
        return newTable;
    }
    private K[] genericKeyArray(int sz) {
        K[] newTable = (K[])new Object[sz];
        return newTable;
    }

    private void putToTable(int index, V value, K key) {
        table[index] = value;
        if (!tableValidity[index]) busyness++;
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
        table = genericArray(capacity);
        tableValidity = new boolean[capacity];

    }

    public HashTable(float loadFactor) {
        this(loadFactor, 128);
    }

    public HashTable(int initialCapacity) {
        this((float) 0.75, initialCapacity);
    }

    public HashTable() {
        this((float) 0.75, 128);
    }

    private Integer localHash(Integer hash) {
        Integer localHash = hash % capacity;
        return localHash;
    }

    private Integer modifyHashCode(Integer hashCode) {
        Integer modifiedHashCode = hashCode.hashCode() + 23;
        return localHash(modifiedHashCode);
    }

    public V put(K key, V value) {
        Integer hashCode = key.hashCode();
        V previousValue = null;
        if (tableValidity[hashCode]) {
            previousValue = get(key);
        }
        while (tableValidity[localHash(hashCode)]) {
            hashCode = modifyHashCode(hashCode);
        }
        putToTable(localHash(hashCode), value, key);
        checkLoadFactory();
        return previousValue;
    }

    public void putAll(Map<K,V> t) {
        Set<K> setOfKeys = t.keySet();
        for (K key : setOfKeys) {
            put(key, get(key));
        }

    }

    private void checkLoadFactory() {
        float duringLoad = (float) busyness / capacity;
        if (duringLoad >= loadFactor) {
            int oldCapacity = capacity;
            capacity = 3*oldCapacity/2 + 1;
            V[] newTable = genericArray(capacity);
            boolean[] newTableValidity = new boolean[capacity];
            for (int i = 0; i < oldCapacity; i++) {
                newTable[i] = table[i];
                newTableValidity[i] = tableValidity[i];
            }
            table = newTable;
            tableValidity = newTableValidity;
        }
    }

    public V get(K key) { //check if I can remove tableValidity[hashCode] from "if"
        Integer hashCode = key.hashCode();
        for (int i = 0; i < capacity; i++) {
            if (keyTable[hashCode].equals(key) && tableValidity[hashCode]){
                return table[hashCode];
            }
            hashCode = modifyHashCode(hashCode);
        }
        return null;
    }

    public void clear() {
        for (boolean value : tableValidity) {
            value = false;
        }
    }

    public Object copy() {
        HashTable<K, V> copiedTable = new HashTable<K,V>(loadFactor, capacity);
        for (int i = 0; i < capacity; i++) {
            copiedTable.put(keyTable[i], table[i]);
        }
        return copiedTable;
    }

    public int size() {
        return busyness;
    }

    public boolean isEmpty() {
        boolean empties = false;
        for (int i = 0; i < capacity; i++) {
            empties = empties || tableValidity[i];
        }
        return !empties;
    }

    public boolean conteins(V value) {
        return conteinsValue(value);
    }

    public boolean conteinsValue(V value) {
        for (int i = 0; i < capacity; i++) {
            if (tableValidity[i] && value.equals(table[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean containsKey(K key) {
        for (int i = 0; i < capacity; i++) {
            if (tableValidity[i] && key.equals(keyTable[i])) {
                return true;
            }
        }
        return false;
    }

    public V remove(K key) {
        for (int i = 0; i < capacity; i++) {
            if (tableValidity[i] && key.equals(keyTable[i])) {
                V previousValue = table[i];
                busyness--;
                tableValidity[i] = false;
                return previousValue;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof HashTable)) return false;
        if (!(busyness == ((HashTable<K,V>) obj).size())) return false;
        boolean notEquals = false;
        for (int i = 0; i < capacity; i++) {
            if (tableValidity[i]) {
                if (!(table[i].equals(((HashTable<K, V>) obj).get(keyTable[i])))) return false;
            }
        }
        return true;
    }

    public String toString() {
        String toString = "";
        for (int i = 0; i <capacity; i++) {
            if (tableValidity[i]) {
                toString += "Key = " + keyTable[i].toString() + " || Value = " + table[i].toString() + "\n";
            }
        }
        return toString;
    }
}

class InvalidIndexExpression extends Exception {

    public InvalidIndexExpression(int index, boolean[] tableValidity) {
        super();
        System.out.println("Invalid index. Fatal error");
    }
}