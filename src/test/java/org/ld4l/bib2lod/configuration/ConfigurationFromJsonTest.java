/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.test.AbstractTestClass;

import com.fasterxml.jackson.databind.node.ObjectNode;

/*
 * Test plan
 * 
 * Which go here, which in BaseConfigurationTest?
 * 
 * Missing/empty/null required node/value - Exception
 * Missing/empty/null optional node/value - succeeds
 * 
 * Input
 * Input location
 *    - doesn't exist - Exception
 *    - not readable - Exception
 *    - 
 * Input serialization
 *    - doesn't exist: succeed
 *    - invalid value (i.e not Ntriples etc) - InvalidValueException
 *    - doesn't match file format (can this be tested)? - exception
 *    - serialization doesn't match file extension - OK?
 *    
 * Valid input - succeed
 *    - 
 * 
 * Output location
 *    - doesn't exist and can't create - Exception
 *    - not readable (move from OptionsReader) - Exception
 *    - exists - success
 *    - doesn't exist but can create - success
 * Format - invalid - InvalidNodeTypeException
 * Valid output - succeed
 * 
 * Classes not found or can't instantiate - not done here, do in tests for 
 * those classes (testing instantiation methods)



 * 
 * No format in output object - ok - use default
 * Format null - ok - use default
 * format empty - ok - use default
 * format not a string - ok - use default
 * format not a valid rdf serialization value - ok - use default
 * 
 * No log in config - exception
 * Log value empty - exception
 * Log value null - exception
 * Log value not a string - exception
 * ?? Test all possible data types: number, boolean, array, object?
 * Log value doesn't exist - exception
 * Log value not a directory - exception
 * Log value not writable - exception
 * Any IO exception - exception

 * 
 * No UriMinter in config - exception
 * UriMinter empty - exception
 * UriMinter null - exception
 * UriMinter not a string - exception
 * ?? Test all possible data types: number, boolean, array, object?
 * URI minter class doesn't exist - exception
 * Uri minter class doesn't extend AbstractUriMinter - exception
 * Can't create Uri minter instance - exception
 * 
 * No writer in config - exception
 * writer empty - exception
 * writer null - exception
 * writer not a string - exception
 * ?? Test all possible data types: number, boolean, array, object?
 * writer class doesn't exist - exception
 * writer class doesn't extend AbstractWriter - exception
 * Can't create writer instance - exception
 * 
 * TODO later: add same for other services
 * 
 * No converters in config - succeed and do nothing (may only to cleaning, for example)
 * Converters empty - succeed and do nothing (may only to cleaning, for example)
 * converters null - succeed and do nothing (may only to cleaning, for example)
 * Converters not an array - exception
 * ?? Test all possible data types: number, boolean, string, object?
 * converter class doesn't exist - exception
 * converter doesn't extend AbstractConverter - exception
 * Convert can't be instantiated - exception
 * 
 * No reconcilers in config - succeed
 * reconcilers empty - succeed
 * reconcilers null - succeed
 * reconciler not an array - exception
 * ?? Test all possible data types: number, boolean, array, object?
 * reconciler an empty array - succeed and do nothing 
 * 
 */

/**
 * Tests of org.ld4l.bib2lod.configuration.ConfigurationFromJson
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
                        .put("location",
                                "/Users/rjy7/Workspace/bib2lod/src/test/resources/input/102063.min.xml")
                        .put("serialization", "RDF/XML"));
        optionsNode.set("output",
                jsonObject()
                        .put("location",
                                "/Users/rjy7/Workspace/bib2lod/src/test/resources/output/")
                        .put("format", "ntriples"));
        optionsNode.put("local_namespace", "http://ld4l.org/cornell/");
        optionsNode.set("services",
                jsonObject()
                        .put("uri_minter",
                                "org.ld4l.bib2lod.uri.RandomUriMinter")
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
