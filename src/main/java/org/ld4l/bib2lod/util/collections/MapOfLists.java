/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.util.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    /**
     * Add a value to a list. If the list does not exist, it will be created.
     */
    public void addValue(K key, V value) {
        getValues(key).add(value);
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

}
