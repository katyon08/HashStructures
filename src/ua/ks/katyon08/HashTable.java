package ua.ks.katyon08;

import java.util.Map;
import java.util.Set;

/**
 * This class implements hash table, like java.util.Hashtable.java,
 * but some lighter.
 * To successfully store and retrieve objects from a hashtable, the
 * objects used as keys must implement the hashCode method and the
 * equals method.
 * @author  Volodymyr Rozsokhovatskyi.
 * @see     java.util.Hashtable
 */

public class HashTable<K, V> {

    /**
     * The table containing values.
     */
    private V[] table;

    /**
     * The table containing keys.
     */
    private K[] keyTable;

    /**
     * The table containing flags  indicating the validity of the
     * information in the table of keys and values. If the flag
     * value is true - then the value is really in the hash table,
     * and if false - the value has not been initialized or has
     * been remote.
     */
    private boolean[] tableValidity;

    /**
     * The number that indicates how much of the table can take
     * recording before the table is modified.
     */
    private float loadFactor;

    /**
     * The number that indicates the maximum number of records
     * can fit into the table.
     */
    private int capacity;

    /**
     * The number that indicates the maximum number of records
     * are valid.
     */
    private int count = 0;

    /**
     * Constructs a new, empty hashtable with the specified initial
     * capacity and the specified load factor.
     *
     * @param      initialCapacity - the initial capacity of the hashtable.
     * @param      loadFactor      - the load factor of the hashtable.
     * @exception  IllegalArgumentException  if the initial capacity is less
     *             than zero, or if the load factor is nonpositive.
     */
    public HashTable(float loadFactor, int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal Load: "+loadFactor);
        if (initialCapacity==0)
            initialCapacity = 1;
        this.loadFactor = loadFactor;
        this.capacity = initialCapacity;
        table = genericValueArray(capacity);
        tableValidity = new boolean[capacity];
        keyTable = genericKeyArray(capacity);

    }

    /**
     * Constructs a new, empty hashtable with the specified load factor
     * and default initial capacity (11).
     *
     * @param     loadFactor - the initial loadFactor of the hashtable.
     * @exception IllegalArgumentException if the initial loadFactor is less
     *              than zero.
     */
    public HashTable(float loadFactor) {
        this(loadFactor, 11);
    }

    /**
     * Constructs a new, empty hashtable with the specified initial capacity
     * and default load factor (0.75).
     *
     * @param     initialCapacity - the initial capacity of the hashtable.
     * @exception IllegalArgumentException if the initial capacity is less
     *              than zero.
     */
    public HashTable(int initialCapacity) {
        this(0.75f, initialCapacity);
    }

    /**
     * Constructs a new, empty hashtable with a default initial capacity (11)
     * and load factor (0.75).
     */
    public HashTable() {
        this(0.75f, 11);
    }

    /**
     * Constructs a new hashtable with the same mappings as the given
     * Map.  The hashtable is created with an initial capacity sufficient to
     * hold the mappings in the given Map and a default load factor (0.75).
     *
     * @param income - the map whose mappings are to be placed in this map.
     * @throws NullPointerException if the specified map is null.
     * @since   1.1
     */
    public HashTable(Map<K, V> income) {
        this(0.75f, Math.max(2*income.size(), 11));
        putAll(income);
    }

    /**
     * Generates the array of value type.
     * @param sz - size of generating array.
     * @return Generated array of value type.
     */
    @SuppressWarnings("unchecked")
    private V[] genericValueArray(int sz) {
        V[] newTable = (V[])new Object[sz];
        return newTable;
    }

    /**
     * Generates the array of key type.
     * @param sz - size of generating array.
     * @return Generated array of key type.
     */
    @SuppressWarnings("unchecked")
    private K[] genericKeyArray(int sz) {
        K[] newTable = (K[])new Object[sz];
        return newTable;
    }

    private void putToTable(int index, V value, K key) {
        table[index] = value;
        keyTable[index] = key;
        if (!tableValidity[index]) count++;
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


    private Integer localHash(Integer hash) {
        Integer localHash = hash % capacity;
        return localHash;
    }

    /**
     * Implements rehashing.
     * @param hashCode - the hash code coming to rehash.
     * @return the rehashed hash come
     */
    private Integer modifyHashCode(Integer hashCode) {
        Integer modifiedHashCode = hashCode.hashCode() + 23;
        return localHash(modifiedHashCode);
    }

    /**
     * Maps the specified key to the specified
     * value in this hashtable. Neither the key nor the
     * value can be null.
     *
     * The value can be retrieved by calling the get method
     * with a key that is equal to the original key.
     *
     * @param      key - the hashtable key
     * @param      value - the value
     * @return     the previous value of the specified key in this hashtable,
     *             or null if it did not have one
     * @exception  NullPointerException  if the key or value is null
     */
    public V put(K key, V value) {
        // Make sure the value is not null
        if (value == null) {
            throw new NullPointerException();
        }

        Integer hashCode = localHash(key.hashCode());
        V previousValue = null;
        if (tableValidity[hashCode]) {
            previousValue = get(key);
        }
        while (tableValidity[localHash(hashCode)]) {
            hashCode = localHash(modifyHashCode(hashCode));
        }
        putToTable(localHash(hashCode), value, key);
        checkLoadFactory();
        return previousValue;
    }

    /**
     * Puts all of pairs key, value into hash table
     * @param t - income Map consists of pairs key, value.
     * @exception  NullPointerException  if one the keys or values is null
     */
    public void putAll(Map<K, V> t) {
        Set<K> setOfKeys = t.keySet();
        if (setOfKeys == null) throw new NullPointerException();
        for (K key : setOfKeys) {
            put(key, get(key));
        }

    }

    /**
     * Checks on loaded the table (of keys, or values). If the table is
     * more loaded that means load factor, it creates a new table one and
     * a half the size of previous.
     */
    private void checkLoadFactory() {
        float duringLoad = (float) count / capacity;
        if (duringLoad >= loadFactor) {
            int oldCapacity = capacity;
            capacity = 3*oldCapacity/2 + 1;
            System.out.println("reloading " + oldCapacity + " to " + capacity + " by loadfactor " + duringLoad);
            V[] newTable = genericValueArray(capacity);
            K[] newKeyTable = genericKeyArray(capacity);
            boolean[] newTableValidity = new boolean[capacity];
            for (int i = 0; i < oldCapacity; i++) {
                newTable[i] = table[i];
                newTableValidity[i] = tableValidity[i];
                newKeyTable[i] = keyTable[i];
            }
            table = newTable;
            tableValidity = newTableValidity;
            keyTable = newKeyTable;

        }
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or null if this map contains no mapping for the key.
     *
     * @param key - the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     *         null if this map contains no mapping for the key
     * @throws NullPointerException if the specified key is null
     */
    public V get(K key) { //check if I can remove tableValidity[hashCode] from "if"
        Integer hashCode = localHash(key.hashCode());
        if (key == null)
            throw new NullPointerException();
        for (int i = 0; i < capacity; i++) {
            if (tableValidity[hashCode]
                    && keyTable[hashCode].equals(key)){
                return table[hashCode];
            }
            hashCode = localHash(modifyHashCode(hashCode));
        }
        return null;
    }

    /**
     * Clears this hashtable so that it contains no keys.
     */
    public void clear() {
        for (boolean value : tableValidity) {
            if (value) {
                value = false;
                count--;
            }
        }
    }

    /**
     * Creates a shallow copy of this hashtable. All the structure of the
     * hashtable itself is copied, but the keys and values are not cloned.
     * This is a relatively expensive operation.
     *
     * @return  a clone of the hashtable
     */
    public Object clone() {
        HashTable<K, V> copiedTable = new HashTable<K, V>(loadFactor, capacity);
        for (int i = 0; i < capacity; i++) {
            if (tableValidity[i]) {
                copiedTable.put(keyTable[i], table[i]);
            }
        }
        return copiedTable;
    }

    /**
     * Returns the number of keys in this hashtable.
     *
     * @return  the number of keys in this hashtable.
     */
    public int size() {
        return count;
    }
    /**
     * Tests if this hashtable maps no keys to values.
     *
     * @return  true if this hashtable maps no keys to values;
     *          false otherwise.
     */
    public boolean isEmpty() {
        return count==0;
    }

    /**
     * Tests if some key maps into the specified value in this hashtable.
     * This operation is more expensive than the {@link #containsKey
     * containsKey} method.
     *
     * Note that this method is identical in functionality to
     * {@link #containsValue containsValue}, (which is part of the
     * {@link Map} interface in the collections framework).
     *
     * @param      value - a value to search for
     * @return     true if and only if some key maps to the
     *             value argument in this hashtable as
     *             determined by the equals method;
     *             false otherwise.
     * @exception  NullPointerException  if the value is null
     */
    public boolean contains(V value) {
        return containsValue(value);
    }

    /**
     * Returns true if this hashtable maps one or more keys to this value.
     *
     * @param value - value whose presence in this hashtable is to be tested
     * @return true if this map maps one or more keys to the
     *         specified value
     * @throws NullPointerException  if the value is null
     */
    public boolean containsValue(V value) {
        if (value == null) throw new NullPointerException();
        for (int i = 0; i < capacity; i++) {
            if (tableValidity[i] && value.equals(table[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests if the specified object is a key in this hashtable.
     *
     * @param   key - possible key
     * @return  true if and only if the specified object
     *          is a key in this hashtable, as determined by the
     *          equals method; false otherwise.
     * @throws  NullPointerException  if the key is null
     */
    public boolean containsKey(K key) {
        if (key == null) throw new NullPointerException();
        for (int i = 0; i < capacity; i++) {
            if (tableValidity[i] && key.equals(keyTable[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the key (and its corresponding value) from this
     * hashtable. This method does nothing if the key is not in the hashtable.
     *
     * @param   key - the key that needs to be removed
     * @return  the value to which the key had been mapped in this hashtable,
     *          or null if the key did not have a mapping
     * @throws  NullPointerException  if the key is null
     */
    public V remove(K key) {
        if (key == null) throw new NullPointerException();
        for (int i = 0; i < capacity; i++) {
            if (tableValidity[i] && key.equals(keyTable[i])) {
                V previousValue = table[i];
                count--;
                tableValidity[i] = false;
                return previousValue;
            }
        }
        return null;
    }

    /**
     * Compares the specified Object with this Map for equality,
     * as per the definition in the Map interface.
     *
     * @param  obj - object to be compared for equality with this hashtable
     * @return true if the specified Object is equal to this Map
     */
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof HashTable)) return false;
        if (!(count == ((HashTable<K, V>) obj).size())) return false;
        boolean notEquals = false;
        for (int i = 0; i < capacity; i++) {
            if (tableValidity[i]) {
                if (!(table[i].equals(((HashTable<K, V>) obj).get(keyTable[i])))) return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int keyHash = 0;
        int valueHash = 0;
        for (int i = 0; i < capacity; i++) {
            keyHash += (tableValidity[i] ? keyTable[i].hashCode() : 0);
            valueHash += (tableValidity[i] ? table[i].hashCode() : 0);
        }
        return keyHash ^ valueHash;
    }

    /**
     * Returns a string representation of this Hashtable object in the form of a set of entries,
     * enclosed by || symbol.
     * @return
     */
    public String toString() {
        String toString = "";
        for (int i = 0; i <capacity; i++) {
            if (tableValidity[i]) {
                toString += "Key = " + keyTable[i].toString() + 
                        " || Value = " + table[i].toString() + "\n";
            }
        }
        return toString;
    }

    public String toStringDevelopMode() {
        String toString = "";
        for (int i = 0; i <capacity; i++) {
            if (tableValidity[i]) {
                toString += "Key = " + keyTable[i].toString() +
                        " || Value = " + table[i].toString() + " || Index = " + i +"\n";
            }
            else {
                toString += " *** non-valible *** " + "Key = ";
                if (keyTable[i] != null) toString += keyTable[i].toString() + " || Value = ";
                else toString += " /null || Value = ";
                if (table[i] != null) toString += table[i].toString() + " || Index = ";
                else toString += " /null || Index = ";
                toString += i +"\n";
            }
        }
        return toString;
    }
}

class InvalidIndexExpression extends Exception {

    public InvalidIndexExpression(int index, boolean[] tableValidity) {
        super();
        System.out.println("Invalid index. Fatal error. Index = " + index);
    }
}