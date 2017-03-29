/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;
import static org.ld4l.bib2lod.configuration.ConfigurationNode.Builder;
import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;
import org.ld4l.bib2lod.configuration.JsonConfigurationFileParser.FieldPath;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * TODO
 */
public class JsonConfigurationFileParserTest extends AbstractTestClass {
    private Configuration expected;
    private Configuration configuration;

    // ----------------------------------------------------------------------
    // Parsing tests
    // ----------------------------------------------------------------------

    @Test
    public void invalidJsonSyntax_throwsException() {
        expectConfigurationException("invalid JSON");
        runParser("[");
    }

    @Test
    public void topLevelJsonArray_throwsException() {
        expectConfigurationException("single JSON object");
        runParser("[true, false]");
    }

    @Test
    public void topLevelJsonConstant_throwsException() {
        expectConfigurationException("single JSON object");
        runParser("true");
    }

    // ----------------------------------------------------------------------
    // Tests on FieldPath
    // ----------------------------------------------------------------------

    @Test
    public void printAnEmptyFieldPath() {
        assertEquals("[]", new FieldPath().toString());
    }

    @Test
    public void printAPopulatedFieldPath() {
        FieldPath path = new FieldPath("one", "two").add("three");
        assertEquals("[one, two, three]", path.toString());
    }

    // ----------------------------------------------------------------------
    // Tests on Conversion
    // ----------------------------------------------------------------------

    @Test
    public void childNodeIsNotObjectArrayOrString_throwsException() {
        expectConfigurationException("Not a valid node type");
        runParser("{\"this\": true}");
    }

    @Test
    public void arrayContainsAMixtureOfTypes_throwsException() {
        expectConfigurationException("attributes or child nodes");
        runParser("{\"this\": [\"string\", {}]}");
    }

    @Test
    public void arrayContainsOtherThanObjectsOrStrings_throwsException() {
        expectConfigurationException("attributes or child nodes");
        runParser("{\"this\": [true, false]}");
    }

    @Test
    public void arrayIsEmpty_throwsException() {
        expectConfigurationException("may not be empty");
        runParser("{ \"this\": { \"that\": [] } }");
    }

    @Test
    public void multipleClassNames_throwsException() {
        expectConfigurationException("only one value for class");
        runParser(
                "{ \"this\": { \"class\": [\"oneClass\", \"anotherClass\"] } }");
    }

    @Test
    public void attributeIsAssignedProperly() {
        expected = new Builder().addAttribute("this", "value").build();
        assertParsedAsExpected("{\"this\": \"value\"}");
    }

    @Test
    public void classNameIsAssignedProperly() {
        expected = new Builder().setClassName("className").build();
        assertParsedAsExpected("{\"class\": \"className\"}");
    }

    @Test
    public void childNodeIsAssignedProperly() {
        ConfigurationNode child = new Builder().build();
        expected = new Builder().addChild("child", child).build();
        assertParsedAsExpected("{\"child\": {} }");
    }

    @Test
    public void attributeArrayIsAssignedProperly() {
        expected = new Builder().addAttribute("this", "value1")
                .addAttribute("this", "value2").build();
        assertParsedAsExpected("{\"this\": [\"value1\", \"value2\"]}");
    }

    @Test
    public void childArrayIsAssignedProperly() {
        ConfigurationNode child1 = new Builder().setClassName("child1Class")
                .build();
        ConfigurationNode child2 = new Builder().setClassName("child2Class")
                .build();
        expected = new Builder().addChild("child1", child1)
                .addChild("child2", child2).build();
        assertParsedAsExpected("" //
                + "{" //
                + "  \"child1\": {" //
                + "    \"class\": \"child1Class\"" //
                + "  }, " //
                + "  \"child2\": {" //
                + "    \"class\": \"child2Class\"" //
                + " }" //
                + "}");
    }

    @Test
    public void emptyConfigWorksFine() {
        expected = new Builder().build();
        assertParsedAsExpected("{}");
    }

    @Test
    public void omnibusSuccess() {
        String jsonString = "" //
                + "{ \n " //
                + "    \"local_namespace\": \"http://data.ld4l.org/cornell/\", \n " //
                + "    \"InputService\": { \n " //
                + "        \"class\": \"org.ld4l.bib2lod.io.FileInputService\", \n " //
                + "        \"source\": \"/Users/rjy7/Workspace/bib2lod/src/test/resources/input/102063.min.xml\", \n " //
                + "        \"extension\": [ \n " //
                + "           \"xml\",  \n " //
                + "           \"rdf\" \n " //
                + "        ] \n " //
                + "    },  \n " //
                + "    \"OutputService\": { \n " //
                + "        \"class\": \"org.ld4l.bib2lod.io.FileOutputService\", \n " //
                + "        \"destination\": \"/Users/rjy7/Workspace/bib2lod/src/test/resources/output/\", \n " //
                + "        \"format\": \"N-TRIPLE\" \n " //
                + "    }, \n " //
                + "    \"UriService\": [ \n " //
                + "        {  \n " //
                + "            \"class\": \"org.ld4l.bib2lod.uri.RandomUriMinter\" \n " //
                + "        } , \n " //
                + "        {  \n " //
                + "            \"class\": \"org.ld4l.bib2lod.uri.AnotherUriMinter\" \n " //
                + "        }  \n " //
                + "    ], \n " //
                + "    \"Cleaner\": { \n " //
                + "        \"class\": \"org.ld4l.bib2lod.cleaning.MarcxmlCleaner\" \n " //
                + "    }, \n " //
                + "    \"Converter\": { \n " //
                + "        \"class\": \"org.ld4l.bib2lod.conversion.to_rdf.ld4l.MarcxmlConverter\" \n " //
                + "    } \n " //
                + "}";

        expected = new Builder()
                .addAttribute("local_namespace",
                        "http://data.ld4l.org/cornell/")
                .addChild("InputService", new Builder()
                        .setClassName("org.ld4l.bib2lod.io.FileInputService")
                        .addAttribute("source",
                                "/Users/rjy7/Workspace/bib2lod/src/test/resources/input/102063.min.xml")
                        .addAttribute("extension", "xml")
                        .addAttribute("extension", "rdf").build())
                .addChild("OutputService", new Builder()
                        .setClassName("org.ld4l.bib2lod.io.FileOutputService")
                        .addAttribute("destination",
                                "/Users/rjy7/Workspace/bib2lod/src/test/resources/output/")
                        .addAttribute("format", "N-TRIPLE").build())
                .addChild("UriService",
                        new Builder()
                                .setClassName(
                                        "org.ld4l.bib2lod.uri.RandomUriMinter")
                                .build())
                .addChild("UriService",
                        new Builder()
                                .setClassName(
                                        "org.ld4l.bib2lod.uri.AnotherUriMinter")
                                .build())
                .addChild("Cleaner",
                        new Builder()
                                .setClassName(
                                        "org.ld4l.bib2lod.cleaning.MarcxmlCleaner")
                                .build())
                .addChild("Converter",
                        new Builder()
                                .setClassName(
                                        "org.ld4l.bib2lod.conversion.to_rdf.ld4l.MarcxmlConverter")
                                .build())
                .build();

        assertParsedAsExpected(jsonString);
    }
    // ----------------------------------------------------------------------
    // helper methods
    // ----------------------------------------------------------------------

    private void runParser(String input) {
        configuration = new JsonConfigurationFileParser(stream(input))
                .getTopLevelConfiguration();
    }

    private InputStream stream(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    private void expectConfigurationException(String matcher) {
        expectException(ConfigurationException.class, matcher);
    }

    private void assertParsedAsExpected(String jsonString) {
        runParser(jsonString);
        assertEquals(expected, configuration);
    }
}
