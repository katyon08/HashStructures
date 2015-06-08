package ua.ks.katyon08;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Comparator;
import java.util.Map;

public class TreeMap<K, V> {
	private static final boolean RED = false;
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
				replacement.color == BLACK) {
			if (isLeftSon(replacement)) {
				brotherNode = brother(replacement);
				if (brotherNode.color == RED) {
					brotherNode.color = BLACK;
					replacement.parent.color = RED;
					rotateLeft(replacement.parent);
					brotherNode = brother(replacement); // brotherNode = right bro
				}
				if (brotherNode.left.color  == BLACK &&
						brotherNode.right.color == BLACK) {
					brotherNode.color = RED;
					replacement = replacement.parent;
				} else {
					if (brotherNode.right.color == BLACK) {
						brotherNode.left.color = BLACK;
						brotherNode.color = RED;
						rotateRight(brotherNode);
						brotherNode = brother(replacement); // brotherNode = right bro
					}
					brotherNode.color = replacement.parent.color;
					replacement.parent.color = BLACK;
					brotherNode.right.color = BLACK;
					rotateLeft(replacement.parent);
					replacement = root;
				}
			} else { // symmetric
				brotherNode = brother(replacement);
				if (brotherNode.color == RED) {
					brotherNode.color = BLACK;
					replacement.parent.color = RED;
					rotateRight(replacement.parent);
					brotherNode = brother(replacement); // brotherNode = left bro
				}
				if (brotherNode.right.color  == BLACK &&
						brotherNode.left.color == BLACK) {
					brotherNode.color = RED;
					replacement = replacement.parent;
				} else {
					if (brotherNode.left.color == BLACK) {
						brotherNode.right.color = BLACK;
						brotherNode.color = RED;
						rotateLeft(brotherNode);
						brotherNode = brother(replacement); // brotherNode = left bro
					}
					brotherNode.color = replacement.parent.color;
					replacement.parent.color = BLACK;
					brotherNode.left.color = BLACK;
					rotateRight(replacement.parent);
					replacement = root;
				}
			}
		}
		replacement.color = BLACK;
	}

	private void modifyAfterInsert(Entry<K, V> insertment) throws NotImplementedException {
		insertment.color = RED;
		Entry<K, V> brother = brother(insertment);
		while (insertment != null &&
				insertment != root &&
					insertment.parent.color == RED) {
			if (isLeftSon(insertment.parent)) {
				if (brother.color == RED) {
					insertment.parent.color = BLACK;
					brother.color = BLACK;
					insertment.parent.parent.color = RED;
					insertment = insertment.parent.parent;
				}
				else {
					if (isRightSon(insertment)) {
						insertment = insertment.parent;
						rotateLeft(insertment);
					}
					insertment.parent.color = BLACK;
					insertment.parent.parent.color = RED;
					rotateRight(insertment.parent.parent);
				}
			}
			else {
				if (brother.color == RED) {
					insertment.parent.color = BLACK;
					brother.color = BLACK;
					insertment.parent.parent.color = RED;
					rotateRight(insertment.parent.parent);
				}
				else {
					if (isLeftSon(insertment)) {
						insertment = insertment.parent;
						rotateRight(insertment);
					}
					insertment.parent.color = BLACK;
					insertment.parent.parent.color = RED;
					rotateLeft(insertment.parent.parent);
				}
			}
		}
		root.color = BLACK;
	}

	public int size() {
		return size;
	}

	public boolean containsKey(Object key) {
		return getEntry(key) != null;
	}

	private Object getEntry(Object key) throws NotImplementedException {
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

	private Object getEntryUsingComparator(Object keyObject) throws NotImplementedException{
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

	private void setColor(Entry<K, V> node, boolean c) {
		if (node != null)
			node.color = c;
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


	private class Entry<K, V> {
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

		public String toString() {
			return key + " = " + value;
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


	}

}
