package org.ld4l.bib2lod.util.collections;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.list.SetUniqueList;

/**
 * A Map of Lists in which the Lists contains no duplicates.
 */
public class MapOfUniqueLists<K, V> extends MapOfLists<K, V> {
    
    @Override
    protected List<V> createList() {
        return SetUniqueList.setUniqueList(new ArrayList<V>());
    }
    
    @Override
    public MapOfUniqueLists<K, V> duplicate() {
        MapOfUniqueLists<K, V> clone = new MapOfUniqueLists<>();
        for (K k : map.keySet()) {
            for (V v : map.get(k)) {
                clone.addValue(k, v);
            }
        }
        return clone;
    }
    
    /**
     * Remove the specified value associated with this key. Does nothing if
     * the value is not found. Value may be null.
     */
    @Override
    public void removeValue(K key, V value) {
        map.get(key).remove(value);
    }

}
