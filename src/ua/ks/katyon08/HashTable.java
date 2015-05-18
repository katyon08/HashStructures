package ua.ks.katyon08;

public class HashTable<K,V>{
    private float loadFactor;
    private int capacity, busyness = 0;
    private V[] table;
    private boolean[] tableValidity;

    @SuppressWarnings("unchecked")
    private V[] genericArray(int sz) {
        V[] newTable = (V[])new Object[sz];
        return newTable;
    }

    private void putToTable(int index, V value) {
        table[index] = value;
        tableValidity[index] = true;
        busyness++;
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
        this((float)0.75, initialCapacity);
    }

    public HashTable() {
        this((float) 0.75, 128);
    }

    private int localHash(int hash) {
        return hash % capacity;
    }


    public void put(K key, V value) {
        Integer hasCode = key.hashCode();
        while (tableValidity[localHash(hasCode)]) {
            hasCode = hasCode.hashCode() + 23;
        }
        putToTable(localHash(hasCode), value);
        checkLoadFactory();
    }

    private void checkLoadFactory() {
        float duringLoad = (float) busyness / capacity;
        if (busyness > loadFactor) {
            int oldCapacity = capacity;
            capacity = 3/2*capacity + 1;
            V[] newTable = genericArray(capacity);
            boolean[] newTableValidity = new boolean[capacity];
            for (int i = 0; i < oldCapacity; i++) {
                newTable[i] = table[i];
                newTableValidity[i] = tableValidity[i];
            }
        }
    }
}

class InvalidIndexExpression extends Exception {

    public InvalidIndexExpression(int index, boolean[] tableValidity) {
        super();
        System.out.println("Invalid index. Fatal error");
    }
}