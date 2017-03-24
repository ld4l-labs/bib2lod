/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ld4l.bib2lod.MockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.AbstractTestClass;

import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * Tests class ConfigurationFromJson.
 */
public class ConfigurationFromJsonTest extends AbstractTestClass {
    
    private MockBib2LodObjectFactory factory;
    private Configuration config;
    private ObjectNode optionsNode;

    @Before
    public void setupForSuccess() {
        optionsNode = jsonObject();
        optionsNode.set("input",
                jsonObject()
                        .put("source",
                                "/Users/rjy7/Workspace/bib2lod/src/test/resources/input/102063.min.xml")
                        .put("serialization", "RDF/XML"));
        optionsNode.set("output",
                jsonObject()
                        .put("destination",
                                "/Users/rjy7/Workspace/bib2lod/src/test/resources/output/")
                        .put("format", "ntriples"));
        optionsNode.put("local_namespace", "http://ld4l.org/cornell/");
        optionsNode.set("services",
                jsonObject()
                        .put("uri_service",
                                "org.ld4l.bib2lod.uris.RandomUriMinter")
                        .put("writer", "org.ld4l.bib2lod.io.SimpleRdfWriter"));
        optionsNode.set("converters", jsonArray().add(
                "org.ld4l.bib2lod.conversion.to_rdf.ld4l.MarcxmlToLd4lRdf"));
        optionsNode.set("reconcilers", jsonArray());

        factory = new MockBib2LodObjectFactory();
        factory.setOptionsReader(new MockOptionsReader(optionsNode));
    }
    
    /*
     * Required node missing
     */

    @Test 
    @Ignore
    public void requiredNodeMissing_ThrowsException() throws Exception {
        fail("requiredNodeMissing_ThrowsException not yet implemented");
//        setNamespace(JSON_REMOVE);
//        instantiateAndExpectException(RequiredNodeMissingException.class);
    }

    /*
     * Helper methods
     */

    private void setNamespace(Object newValue) {
        setFieldValue(optionsNode, "local_namespace", newValue);
    }

    private void instantiateAndExpectException(
            Class<? extends Exception> expected) {
        try {
            config = Configuration.instance(new String[0]);
            fail("Expected exception '" + expected.getSimpleName());
        } catch (Exception e) {
            if (!e.getClass().equals(expected)) {
                fail("Expected exception '" + expected.getSimpleName()
                        + ", but exception was '" + e.getClass().getSimpleName()
                        + "'");
            }
        }
    }

}
