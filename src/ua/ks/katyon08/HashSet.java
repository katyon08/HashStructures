package ua.ks.katyon08;

import java.util.*;
import java.util.HashMap;

public class HashSet<E> {
	private static final Object dummyValue = new Object();

	private java.util.HashMap<E, Object> map;

	public HashSet() {
		map = new java.util.HashMap<>();
	}

	public HashSet(int initialCapacity) {
		map = new HashMap<>(initialCapacity);
	}

	public HashSet(int initialCapacity, float loadFactor) {
		map = new HashMap<>(initialCapacity, loadFactor);
	}

	public HashSet(Collection<? extends E> c) {
		map = new java.util.HashMap<>(Math.max((int) (c.size()/.75f) + 1, 1 << 4));
		addAll(c);
	}

	public boolean add(E value) {
		return map.put(value, dummyValue) == null;
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean modified = false;
		for (E element : c) {
			if (add(element)) {
				modified = modified && true;
			}
		}
		return modified;
	}

	public void clear() {
		map.clear();
	}

	public Object clone() {
		try {
			HashSet<E> newSet = new HashSet<>();
			newSet.map = (HashMap<E, Object>) map.clone();
			return newSet;
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	public boolean contains(Object object) {
		return map.containsKey(object);
	}

	public boolean containsAll(Collection<?> collection) {
		for (Object element : collection) {
			if (!contains(element)) {
				return false;
			}
		}
		return true;
	}

	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Set)) {
			return false;
		}
		Collection<?> collection = (Collection<?>) object;
		if (collection.size() != size())
			return false;
		try {
			return containsAll(collection);
		} catch (ClassCastException unused)   {
			return false;
		} catch (NullPointerException unused) {
			return false;
		}
	}

	public int hashCode() {
		int hashCode = 0;
		Iterator<E> iterator = iterator();
		while (iterator.hasNext()) {
			E obj = iterator.next();
			if (obj != null)
				hashCode += obj.hashCode();
		}
		return hashCode;
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Iterator<E> iterator() {
		return map.keySet().iterator();
	}

	public int size() {
		return map.size();
	}

	public boolean remove(Object object) {
		return map.remove(object) == dummyValue;
	}

	public boolean removeAll(Collection<?> collection) {
		Objects.requireNonNull(collection);
		boolean modified = false;
		if (size() > collection.size()) {
			for (Iterator<?> i = collection.iterator(); i.hasNext(); )
				modified |= remove(i.next());
		} else {
			for (Iterator<?> i = iterator(); i.hasNext(); ) {
				if (collection.contains(i.next())) {
					i.remove();
					modified = true;
				}
			}
		}
		return modified;
	}


}
