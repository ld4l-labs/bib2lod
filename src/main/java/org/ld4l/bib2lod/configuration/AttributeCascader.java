/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import org.ld4l.bib2lod.configuration.ConfigurationNode.Builder;
import org.ld4l.bib2lod.util.collections.MapOfLists;

/**
 * Re-assemble the Configuration tree, propagating attributes from parent to
 * child unless the child already has attributtes for that key.
 */
public class AttributeCascader {
    private static final MapOfLists<String, String> EMPTY_ATTRIBUTES = new MapOfLists<String, String>();

    public Configuration cascade(Configuration config) {
        return cascadeNode(config, EMPTY_ATTRIBUTES);
    }

    private Configuration cascadeNode(Configuration node,
            MapOfLists<String, String> parentAttributes) {
        // Cascade the attributes from parent to child
        MapOfLists<String, String> childAttributes = node.getAttributesMap();
        for (String key : parentAttributes.keys()) {
            if (!childAttributes.keys().contains(key)) {
                for (String value : parentAttributes.getValues(key)) {
                    childAttributes.addValue(key, value);
                }
            }
        }

        // Cascade to all of the children in turn.
        MapOfLists<String, Configuration> oldChildNodes = node
                .getChildNodesMap();
        MapOfLists<String, Configuration> newChildNodes = new MapOfLists<>();
        for (String key : oldChildNodes.keys()) {
            for (Configuration child : oldChildNodes.getValues(key)) {
                newChildNodes.addValue(key,
                        cascadeNode(child, childAttributes));
            }
        }

        return new Builder(node.getClassName(), childAttributes, newChildNodes)
                .build();
    }

}
