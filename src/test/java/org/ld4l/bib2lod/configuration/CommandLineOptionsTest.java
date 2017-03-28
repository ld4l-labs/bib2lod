/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;
import org.ld4l.bib2lod.configuration.ConfigurationOptions.AttributeOverride;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * TODO
 */
public class CommandLineOptionsTest extends AbstractTestClass {

    private CommandLineOptions commandLine;
    private String configFile;
    private List<AttributeOverride> overrides;

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------

    @Test
    public void invalidOption_throwsException() {
        expectException(ConfigurationException.class, "only valid options");
        parseArgs("Bogus");
    }

    @Test
    public void optionAtEndOfArgs_throwsException() {
        expectException(ConfigurationException.class, "must provide a value");
        parseArgs("-c", "configFile", "-o");
    }

    @Test
    public void twoOptionsWithoutAValue_throwsException() {
        expectException(ConfigurationException.class, "a value between");
        parseArgs("-o", "-c", "configFile");
    }

    @Test
    public void optionsWithSpacesBeforeTheEquals_throwsException() {
        expectException(ConfigurationException.class, "spaces to the left");
        parseArgs("-o", "bad space=ok space");
    }

    @Test
    public void noArguments_worksFine() {
        parseArgs();
        assertNull(configFile);
        assertEquals(0, overrides.size());
    }

    @Test
    public void configFileOption_worksFine() {
        parseArgs("-c", "myConfigFile");
        assertEquals("myConfigFile", configFile);
        assertEquals(0, overrides.size());
    }

    @Test
    public void overrideWithEquals_isParsedCorrectly() {
        parseArgs("-o", "replace=newValue");
        assertNull(configFile);
        assertEquals(1, overrides.size());
        assertExpectedOverride(keys("replace"), "newValue", overrides.get(0));
    }

    @Test
    public void overrideWithoutEquals_isParsedCorrectly() {
        parseArgs("-o", "replace");
        assertNull(configFile);
        assertEquals(1, overrides.size());
        assertExpectedOverride(keys("replace"), null, overrides.get(0));
    }

    @Test
    public void colonsInTheNameSpec_areInterpretedCorrectly() {
        parseArgs("-o", "replace:me:with:this=excellent value");
        assertNull(configFile);
        assertEquals(1, overrides.size());
        assertExpectedOverride(keys("replace", "me", "with", "this"),
                "excellent value", overrides.get(0));
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------

    private void parseArgs(String... args) {
        commandLine = new CommandLineOptions(args);
        configFile = commandLine.getConfigFile();
        overrides = commandLine.getOverrides();
    }

    private List<String> keys(String... keys) {
        return Arrays.asList(keys);
    }

    private void assertExpectedOverride(List<String> keys, String value,
            AttributeOverride override) {
        assertEquals(keys, override.keys);
        assertEquals(value, override.value);
    }
}
