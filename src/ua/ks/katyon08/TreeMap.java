package ua.ks.katyon08;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class TreeMap<K, V> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	Entry<K, V> root;
	private Comparator<? super K> comparator;
	private int size = 0;

	public TreeMap() {
		comparator = null;
	}

	public TreeMap(Comparator<? super K> comparator) {
		this.comparator = comparator;
	}

	public TreeMap(Map<? extends K, ? extends V> m) {
		comparator = null;
		putAll(m);
	}

	public void putAll(Map<? extends K, ? extends V> m) throws NotImplementedException {
	}

	public V put(K key, V value) {
		if (root == null) {
			root = new Entry<>(key, value, null);
			size = 1;
			return null;
		}
		Entry<K, V> parent, during = root;
		int localCompare;
		if (comparator != null) do {
			parent = during;
			localCompare = comparator.compare(key, during.key);
			if (localCompare < 0) {
				during = during.left;
			} else if (localCompare > 0) {
				during = during.right;
			} else {
				return during.setValue(value);
			}

		} while (parent != null);
		else {
			if (key == null) throw new NullPointerException();

			Comparable<? super K> defaultComparator = (Comparable<? super K>) key;
			do {
				parent = during;
				localCompare = defaultComparator.compareTo(during.key);
				if (localCompare < 0) {
					during = during.left;
				} else if (localCompare > 0) {
					during = during.right;
				} else {
					return during.setValue(value);
				}
			} while (during != null);
		}
		Entry<K, V> newEntry = new Entry<K, V>(key, value, parent);
		if (localCompare < 0)
			parent.left = newEntry;
		else
			parent.right = newEntry;
		modifyAfterInsert(newEntry);
		size++;
		return null;
	}

	public V get(Object key) {
		Entry<K, V> gettingEntry = (Entry<K, V>) getEntry((K) key);
		return (gettingEntry == null ? null : gettingEntry.value);
	}

	public V remove(Object key) {
		Entry<K, V> removeingEntry = (Entry<K, V>) getEntry(key);
		if (removeingEntry == null) {
			return null;
		}
		V oldValue = removeingEntry.value;
		deleteEntry(removeingEntry);
		return oldValue;
	}

	public void clear() {
		size = 0;
		root = null;
	}

	private void deleteEntry(Entry<K, V> removeingEntry) throws NotImplementedException {
		size--;
		if (removeingEntry != null && removeingEntry.right != null) {
			Entry<K, V> successor = successor(removeingEntry);
			removeingEntry.key = successor.key;
			removeingEntry.value = successor.value;
			removeingEntry = successor;
		}
		Entry<K, V> replacement = (removeingEntry != null ? removeingEntry.left : removeingEntry.right);

		if (replacement != null) {
			replacement.parent = removeingEntry.parent;
			if (removeingEntry.parent == null) {
				root = replacement;
			}
			else if(removeingEntry == removeingEntry.parent.left) {
				removeingEntry.parent.left = replacement;
			}
			else removeingEntry.parent.right = replacement;
			removeingEntry.left = removeingEntry.right = removeingEntry.parent = null;
			if (removeingEntry.color == BLACK)
				modifyAfterDeletion(replacement);
			else if (removeingEntry.parent == null)
				root = null;
			else {
				if (removeingEntry.color == BLACK) modifyAfterDeletion(removeingEntry);
				if (removeingEntry.parent != null) {
					if (removeingEntry.parent.left == removeingEntry)
						removeingEntry.parent.left = null;
					else if (removeingEntry.parent.right == removeingEntry) {
						removeingEntry.parent.right = null;
					}
					removeingEntry.parent = null;
				}
			}
		}

		}

	private void modifyAfterDeletion(Entry<K, V> replacement) {
		Entry<K,V> brotherNode;
		while (replacement != root &&
				getColor(replacement) == BLACK) {
			if (isLeftSon(replacement)) {
				brotherNode = brother(replacement);
				if (getColor(brotherNode) == RED) {
					setColor(brotherNode, BLACK);
					setColor(replacement.parent, RED);
					rotateLeft(replacement.parent);
					brotherNode = brother(replacement); // brotherNode = right bro
				}
				if (getColor(brotherNode.left)  == BLACK &&
						getColor(brotherNode.right) == BLACK) {
					setColor(brotherNode, RED);
					replacement = replacement.parent;
				} else {
					if (getColor(brotherNode.right) == BLACK) {
						setColor(brotherNode.left, BLACK);
						setColor(brotherNode, RED);
						rotateRight(brotherNode);
						brotherNode = brother(replacement); // brotherNode = right bro
					}
					setColor(brotherNode, getColor(replacement.parent));
					setColor(replacement.parent, BLACK);
					setColor(brotherNode.right, BLACK);
					rotateLeft(replacement.parent);
					replacement = root;
				}
			} else { // symmetric
				brotherNode = brother(replacement);
				if (getColor(brotherNode) == RED) {
					setColor(brotherNode, BLACK);
					setColor(replacement.parent, RED);
					rotateRight(replacement.parent);
					brotherNode = brother(replacement); // brotherNode = left bro
				}
				if (getColor(brotherNode.right)  == BLACK &&
						getColor(brotherNode.left) == BLACK) {
					setColor(brotherNode, RED);
					replacement = replacement.parent;
				} else {
					if (getColor(brotherNode.left) == BLACK) {
						setColor(brotherNode.right, BLACK);
						setColor(brotherNode, RED);
						rotateLeft(brotherNode);
						brotherNode = brother(replacement); // brotherNode = left bro
					}
					setColor(brotherNode, getColor(replacement.parent));
					setColor(replacement.parent, BLACK);
					setColor(brotherNode.left, BLACK);
					rotateRight(replacement.parent);
					replacement = root;
				}
			}
		}
		setColor(replacement, BLACK);
	}

	private void setColor(Entry<K, V> replacement, boolean color) {
		if (replacement != null) {
			replacement.color = color;
		}
		else if (color != BLACK) {
			throw new NullPointerException();
		}
	}

	private boolean getColor(Entry<K, V> node) {
		if (node != null) {
			return node.color;
		}
		else return BLACK;
	}

	private void modifyAfterInsert(Entry<K, V> insertment) {
		setColor(insertment, RED);
		Entry<K, V> brother = brother(insertment);
		while (insertment != null &&
				insertment != root &&
				getColor(insertment.parent) == RED) {
			if (isLeftSon(insertment.parent)) {
				if (getColor(brother) == RED) {
					setColor(insertment.parent, BLACK);
					setColor(brother, BLACK);
					setColor(insertment.parent.parent, RED);
					insertment = insertment.parent.parent;
				}
				else {
					if (isRightSon(insertment)) {
						insertment = insertment.parent;
						rotateLeft(insertment);
					}
					setColor(insertment.parent, BLACK);
					setColor(insertment.parent.parent, RED);
					rotateRight(insertment.parent.parent);
				}
			}
			else {
				if (getColor(brother) == RED) {
					setColor(insertment.parent, BLACK);
					setColor(brother, BLACK);
					setColor(insertment.parent.parent, RED);
					rotateRight(insertment.parent.parent);
				}
				else {
					if (isLeftSon(insertment)) {
						insertment = insertment.parent;
						rotateRight(insertment);
					}
					setColor(insertment.parent, BLACK);
					setColor(insertment.parent.parent, RED);
					rotateLeft(insertment.parent.parent);
				}
			}
		}
		setColor(root, BLACK);
	}

	public int size() {
		return size;
	}

	public boolean containsKey(Object key) {
		return getEntry(key) != null;
	}

	public Entry<K, V> getEntryPublic(Object key) {
		return (Entry<K, V>) getEntry(key);
	}

	private Object getEntry(Object key) {
		if (comparator != null)
			return getEntryUsingComparator(key);
		if (key == null)
			throw new NullPointerException();
		Comparable<? super K> comparableKey = (Comparable<? super K>) key;
		Entry<K, V> node = root;
		int comparison;
		while (node != null) {
			comparison = comparableKey.compareTo(node.key);
			if (comparison < 0)
					node = node.left;
			else if (comparison > 0)
				node = node.right;
			else
				return node;
		}
		return null;
	}

	private Object getEntryUsingComparator(Object keyObject) {
		K key = (K) keyObject;
		if (comparator != null) {
			Entry<K, V> node = root;
			int comparison;
			while (node != null) {
				comparison = comparator.compare(key, node.key);
				if (comparison < 0)
					node = node.left;
				else if (comparison > 0)
					node = node.right;
				else return node;
			}
		}
		return null;
	}

	/**
	 * Returns the successor of the specified Entry, or null if no such.
	 */
	private Entry<K, V> successor(Entry<K, V> node) {
		if (node == null)
			return null;
		else if (node.right != null) {
			Entry<K, V> p = node.right;
			while (p.left != null)
				p = p.left;
			return p;
		} else {
			Entry<K, V> p = node.parent;
			Entry<K, V> ch = node;
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
	private Entry<K, V> predecessor(Entry<K, V> node) {
		if (node == null)
			return null;
		else if (node.left != null) {
			Entry<K, V> p = node.left;
			while (p.right != null)
				p = p.right;
			return p;
		} else {
			Entry<K, V> p = node.parent;
			Entry<K, V> ch = node;
			while (p != null && ch == p.left) {
				ch = p;
				p = p.parent;
			}
			return p;
		}
	}

	private Entry<K, V> brother(Entry<K, V> node) {
		return (node == null ? null :
				(isLeftSon(node) ? node.parent.right : node.parent.left));
	}

	private boolean isRightSon(Entry<K, V> node) {
		return (node.parent.right == node);
	}

	private boolean isLeftSon(Entry<K, V> node) {
		return (node.parent.left == node);
	}

	private boolean colorOf(Entry<K, V> node) {
		return (node == null ? BLACK : node.color);
	}

	private Entry<K, V> parentOf(Entry<K, V> node) {
		return (node == null ? null : node.parent);
	}

	private Entry<K, V> leftOf(Entry<K, V> p) {
		return (p == null ? null : p.left);
	}

	private Entry<K, V> rightOf(Entry<K, V> node) {
		return (node == null ? null : node.right);
	}

	/**
	 * From CLR
	 */
	private void rotateLeft(Entry<K, V> node) {
		if (node != null) {
			Entry<K, V> r = node.right;
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

	/**
	 * From CLR
	 */
	private void rotateRight(Entry<K, V> node) {
		if (node != null) {
			Entry<K, V> l = node.left;
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

	public String toString() {
		String s = "";
		EntryIterator iterator = new EntryIterator();
		while (iterator.hasNext()) {
			/*s.concat(((Entry<K, V>) iterator.next()).toString()).concat("\n");*/
			s += ((Entry<K, V>) iterator.next()).toString() + "\n";
		}
		return s;
	}

	private Entry<K, V> getFirstEntry() {
		Entry<K, V> node = root;
		while (node.left != null) node = node.left;
		return node;
	}

	private Entry<K, V> getFirstEntryOfSubTree(Entry<K, V> localRoot) {
		Entry<K, V> node = localRoot;
		while (node.left != null) node = node.left;
		return node;
	}


	public class Entry<K, V> {
		Entry<K, V> left;
		Entry<K, V> right;
		Entry<K, V> parent;
		K key;
		V value;
		boolean color = BLACK;

		/**
		 * Make a new cell with given key, value, and parent, and with
		 * {@code null} child links, and BLACK color.
		 */
		Entry(K key, V value, Entry<K, V> parent) {
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
		 * called
		 */
		public V setValue(V value) {
			V oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public boolean equals(Object o) {
			if (!(o instanceof Entry))
				return false;
			Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
			return (key == null ? e.getKey() == null : e.getKey().equals(key)) &&
					(value == null ? e.getValue() == null : e.getValue().equals(value));
		}

		public int hashCode() {
			int keyHash = (key == null ? 0 : key.hashCode());
			int valueHash = (value == null ? 0 : value.hashCode());
			return keyHash ^ valueHash;
		}

		public Entry parent() {
			return parent;
		}

		public Entry left() {
			return left;
		}

		public Entry right() {
			return right;
		}

		public String toString() {
			return (this != null ? key.toString() : "null") + " = " +
					(this != null ? value.toString() : "null") + " is " +
					(color != BLACK ? "RED " : "BLACK ") + "; left = " +
					(left != null ? left.key.toString() : "null") + "; right = " +
					(right != null ? right.key.toString() : "null") + "; paret = " +
					(parent != null ? parent.key.toString() : "null");
		}
	}

	public Iterator entryIterator() {
		return new EntryIterator();
	}

	public Iterator entryIterator(Entry<K, V> node) {
		return new EntryIterator(node);
	}

	private class EntryIterator implements Iterator {
		Entry<K, V> next, previous;

		public EntryIterator() {
			next = getFirstEntry();
		}

		public EntryIterator(Entry<K, V> localRoot) {
			next = getFirstEntryOfSubTree(localRoot);
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public Object next() {
			if (next == null)
				throw new NoSuchElementException();
			previous = next;
			next = successor(next);
			return previous;
		}

		public Entry<K,V> previous() {
			previous = next;
			next = predecessor(next);
			return next;
		}
	}

	public String[] print(Entry<K, V> localRoot) throws NotImplementedException{
		if (localRoot == null) {
			return null;
		}
		int count = (int) (Math.log(size) / Math.log(2)) + 1;

		return null;
	}

}
