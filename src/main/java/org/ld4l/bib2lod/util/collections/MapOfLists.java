/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.util.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A type of map whose values are lists.
 * 
 * getValue() will return the first item in the list, or null if there are none.
 * 
 * getValues() will return the items in the list, or an empty list if there are
 * none. Never returns null.
 */
public class MapOfLists<K, V> {

    protected final Map<K, List<V>> map = new HashMap<>();
    
    protected List<V> createList() {
        return new ArrayList<V>();
    }

    public Set<K> keys() {
        return new HashSet<>(map.keySet());
    }

    public Set<List<V>> values() {
        return new HashSet<List<V>>(map.values());
    }
    
    public Set<Map.Entry<K, List<V>>> entries() {
        return new HashSet<Map.Entry<K, List<V>>>(map.entrySet());
    }

    /**
     * Add a value to a list. If the list does not exist, it will be created.
     */
    public void addValue(K key, V value) {
        getValues(key).add(value);
    }
    
    /**
     * Add values to a list. If the list does not exist, it will be created.
     */
    public void addValues(K key, List<V> values) {
        getValues(key).addAll(values);
    }

    /**
     * Return the first item in the list, or null if there are none.
     */
    public V getValue(K key) {
        List<V> values = getValues(key);
        if (values.isEmpty()) {
            return null;
        } else {
            return values.get(0);
        }
    }

    /**
     * Return the items in the list, or an empty list if there are none. Never
     * returns null.
     */
    public List<V> getValues(K key) {
        if (!map.containsKey(key)) {
            map.put(key, createList());
        }
        return map.get(key);
    }
    
    /**
     * Returns true iff the specified value is contained in the list associated 
     * with this key.
     */
    public boolean hasValue(K key, V value) {
        List<V> values = getValues(key);
        return (values.contains(value));
    }
    
    /**
     * Returns the submap of this.map in which the keys are contained in the
     * specified list of keys.
     */
    @SuppressWarnings("unchecked")
    public MapOfLists<K, V> getSubmap(List<K> keys) {
        
        MapOfLists<K, V> submap = new MapOfLists<>();
        for (Entry<K, List<V>> entry : map.entrySet()) {
            if (keys.contains(entry.getKey())) {
                submap.addValues(entry.getKey(), (List<V>) entry.getValue());
            }
        }
        return submap;
    }
    
    /**
     * Convenience method to pass array rather than List to getSlice().
     */
    public MapOfLists<K, V> getSubmap(K[] keys) {
        return getSubmap(Arrays.asList(keys));
    }

    /**
     * Remove all of the values associated with this key.
     */
    public void removeValues(K key) {
        map.remove(key);
    }

    /**
     * Return a map that duplicates the structure of this one.
     */
    public MapOfLists<K, V> duplicate() {
        MapOfLists<K, V> clone = new MapOfLists<>();
        for (K k : map.keySet()) {
            for (V v : map.get(k)) {
                clone.addValue(k, v);
            }
        }
        return clone;
    }
    
    /**
     * Returns true iff the map is empty.
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            MapOfLists<?, ?> that = (MapOfLists<?, ?>) obj;
            return this.map.equals(that.map);
        }
    }

    @Override
    public String toString() {
        return "MapOfLists[" + map + "]";
    }

}
