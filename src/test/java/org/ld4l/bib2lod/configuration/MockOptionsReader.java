/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.IOException;

import org.apache.commons.cli.ParseException;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Provide a JSonNode to be returned as the configuration.
 */
public class MockOptionsReader implements OptionsReader {
    private final JsonNode node;
    // ----------------------------------------------------------------------
    // Stub infrastructure
    // ----------------------------------------------------------------------

    /**
     * NOTE: Because we don't do a deep copy on input, changes to the original
     * node will take effect even after the constructor has run.
     */
    public MockOptionsReader(JsonNode node) {
        this.node = node;
    }

    // ----------------------------------------------------------------------
    // Stub methods
    // ----------------------------------------------------------------------

    @Override
    public JsonNode configure() throws IOException, ParseException {
        return node.deepCopy();
    }

    // ----------------------------------------------------------------------
    // Un-implemented methods
    // ----------------------------------------------------------------------

}
