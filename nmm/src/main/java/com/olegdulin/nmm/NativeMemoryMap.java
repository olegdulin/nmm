package com.olegdulin.nmm;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class NativeMemoryMap<K, V extends Serializable> implements Map<K, V> {

	private ConcurrentHashMap<K, NativeMemoryBlock<V>> pointerMap=new ConcurrentHashMap<K,NativeMemoryBlock<V>>();
	
	public int size() {
		return pointerMap.size();
	}

	public boolean isEmpty() {
		return pointerMap.isEmpty();
	}

	public boolean containsKey(Object key) {
		return pointerMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException("We don't do scans like this");
	}

	public V get(Object key) {
		if (this.pointerMap.containsKey(key))
		{
			try {
				return this.pointerMap.get(key).get();
			} catch (Exception exp) {
				throw new RuntimeException("Couldn't get an object for key="+key.toString());
			}
		}
		
		return null;
	}

	public V put(K key, V value) {
		V current=get(key);
		try {
			this.pointerMap.put(key, new NativeMemoryBlock<V>(value));
		} catch (IOException e) {
			throw new RuntimeException("Couldn't put an object for key="+key.toString());
		}
		return current;
	}

	public V remove(Object key) {
		V current=get(key);
		this.pointerMap.remove(key);		
		return current;
	}
	
	public void putAll(Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException("putAll is Unsupported!");
		
	}

	public void clear() {
		this.pointerMap.clear();
	}

	public Set<K> keySet() {
		return this.pointerMap.keySet();
	}

	public Collection<V> values() {
		throw new UnsupportedOperationException("values() is unsupported");
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException("entrySet() is unsupported");
	}

}
