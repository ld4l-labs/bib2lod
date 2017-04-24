/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.util.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

    private final Map<K, List<V>> map = new HashMap<>();

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
            map.put(key, new ArrayList<>());
        }
        return map.get(key);
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
