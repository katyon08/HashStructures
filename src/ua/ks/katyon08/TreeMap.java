package ua.ks.katyon08;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Comparator;
import java.util.Map;
import java.util.Vector;

public class TreeMap<K, V> {
    private static final boolean RED   = false;
    private static final boolean BLACK = true;
    Entry<K, V> root;
    private Comparator<? super K> comparator;
    private int size = 0;

    TreeMap() {
        comparator = null;
    }

    public TreeMap(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    public TreeMap(Map<? extends K, ? extends V> m) {
        comparator = null;
        putAll(m);
    }

    private void putAll(Map<? extends K, ? extends V> m) throws NotImplementedException{
    }

    public int size() {
        return size;
    }

    public boolean containsKey(Object key) {
        return getEntry(key) != null;
    }

    private Object getEntry(Object key) throws NotImplementedException {
        return null;
    }
    /**
     * Returns the successor of the specified Entry, or null if no such.
     */
     private Entry<K,V> successor(Entry<K,V> node) {
        if (node == null)
            return null;
        else if (node.right != null) {
            Entry<K,V> p = node.right;
            while (p.left != null)
                p = p.left;
            return p;
        } else {
            Entry<K,V> p = node.parent;
            Entry<K,V> ch = node;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    /**
     * Returns the predecessor of the specified Entry, or null if no such.
     */
    private Entry<K,V> predecessor(Entry<K,V> node) {
        if (node == null)
            return null;
        else if (node.left != null) {
            Entry<K,V> p = node.left;
            while (p.right != null)
                p = p.right;
            return p;
        } else {
            Entry<K,V> p = node.parent;
            Entry<K,V> ch = node;
            while (p != null && ch == p.left) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    private boolean colorOf(Entry<K,V> node) {
        return (node == null ? BLACK : node.color);
    }

    private Entry<K,V> parentOf(Entry<K,V> node) {
        return (node == null ? null: node.parent);
    }

    private Entry<K,V> leftOf(Entry<K,V> p) {
        return (p == null) ? null: p.left;
    }

    private Entry<K,V> rightOf(Entry<K,V> node) {
        return (node == null) ? null: node.right;
    }
    private void setColor(Entry<K,V> node, boolean c) {
        if (node != null)
            node.color = c;
    }

    /** From CLR */
    private void rotateLeft(Entry<K,V> node) {
        if (node != null) {
            Entry<K,V> r = node.right;
            node.right = r.left;
            if (r.left != null)
                r.left.parent = node;
            r.parent = node.parent;
            if (node.parent == null)
                root = r;
            else if (node.parent.left == node)
                node.parent.left = r;
            else
                node.parent.right = r;
            r.left = node;
            node.parent = r;
        }
    }

    /** From CLR */
    private void rotateRight(Entry<K,V> node) {
        if (node != null) {
            Entry<K,V> l = node.left;
            node.left = l.right;
            if (l.right != null) l.right.parent = node;
            l.parent = node.parent;
            if (node.parent == null)
                root = l;
            else if (node.parent.right == node)
                node.parent.right = l;
            else node.parent.left = l;
            l.right = node;
            node.parent = l;
        }
    }




    private class Entry<K, V> {
        Entry<K,V> left;
        Entry<K,V> right;
        Entry<K,V> parent;
        K key;
        V value;
        boolean color = BLACK;

        /**
         * Make a new cell with given key, value, and parent, and with
         * {@code null} child links, and BLACK color.
         */
        Entry(K key, V value, Entry<K,V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * Returns the key.
         *
         * @return the key
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value associated with the key.
         *
         * @return the value associated with the key
         */
        public V getValue() {
            return value;
        }

        /**
         * Replaces the value currently associated with the key with the given
         * value.
         *
         * @return the value associated with the key before this method was
         *         called
         */
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
            return (key == null ? e.getKey() == null : e.getKey().equals(key)) &&
                    (value == null ? e.getValue() == null : e.getValue().equals(value));
        }

        public int hashCode() {
            int keyHash = (key==null ? 0 : key.hashCode());
            int valueHash = (value==null ? 0 : value.hashCode());
            return keyHash ^ valueHash;
        }

        public String toString() {
            return key + " = " + value;
        }


    }

}
