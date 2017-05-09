/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;
import org.ld4l.bib2lod.configuration.ConfigurationOptions.AttributeOverride;
import org.ld4l.bib2lod.configuration.ConfigurationOptions.ConfigurationFieldPath;
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
        expectException(ConfigurationException.class, "Valid options are");
        parseArgs("Bogus");
    }

    @Test
    public void optionAtEndOfArgs_throwsException() {
        expectException(ConfigurationException.class,
                "must provide a parameter");
        parseArgs("-c", "configFile", "--config");
    }

    @Test
    public void twoOptionsWithoutAValue_throwsException() {
        expectException(ConfigurationException.class,
                "must provide a parameter");
        parseArgs("-s", "--config", "configFile");
    }

    @Test
    public void optionsWithSpacesBeforeTheEquals_throwsException() {
        expectException(ConfigurationException.class, "spaces to the left");
        parseArgs("--set", "bad space=ok space");
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
    public void longConfigFileOption_worksFine() {
        parseArgs("--config", "myConfigFile");
        assertEquals("myConfigFile", configFile);
        assertEquals(0, overrides.size());
    }

    @Test
    public void addRequiresEquals() {
        expectException(ConfigurationException.class,
                "must include a replacement");
        parseArgs("-a", "addThis");
    }

    @Test
    public void add_worksFine() {
        parseArgs("-a", "addThis=newValue");
        assertNull(configFile);
        assertEquals(1, overrides.size());
        assertExpectedOverride(path("addThis"), "newValue", overrides.get(0));
    }

    @Test
    public void longAdd_worksFine() {
        parseArgs("--add", "addThis=newValue");
        assertNull(configFile);
        assertEquals(1, overrides.size());
        assertExpectedOverride(path("addThis"), "newValue", overrides.get(0));
    }

    @Test
    public void dropDoesntPermitEquals() {
        expectException(ConfigurationException.class,
                "must not include a replacement");
        parseArgs("-d", "dropThis=newValue");
    }

    @Test
    public void drop_worksFine() {
        parseArgs("-d", "dropThis");
        assertNull(configFile);
        assertEquals(1, overrides.size());
        assertExpectedOverride(path("dropThis"), null, overrides.get(0));
    }

    @Test
    public void longDrop_worksFine() {
        parseArgs("--drop", "dropThis");
        assertNull(configFile);
        assertEquals(1, overrides.size());
        assertExpectedOverride(path("dropThis"), null, overrides.get(0));
    }

    @Test
    public void setWithEquals_worksLikeDropAndAdd() {
        parseArgs("-s", "setThis=newValue");
        assertNull(configFile);
        assertEquals(2, overrides.size());
        assertExpectedOverride(path("setThis"), null, overrides.get(0));
        assertExpectedOverride(path("setThis"), "newValue", overrides.get(1));
    }

    @Test
    public void setWithoutEquals_worksLikeDrop() {
        parseArgs("-s", "setThis");
        assertNull(configFile);
        assertEquals(1, overrides.size());
        assertExpectedOverride(path("setThis"), null, overrides.get(0));
    }

    @Test
    public void longSet_worksToo() {
        parseArgs("--set", "setThis=newValue");
        assertNull(configFile);
        assertEquals(2, overrides.size());
        assertExpectedOverride(path("setThis"), null, overrides.get(0));
        assertExpectedOverride(path("setThis"), "newValue", overrides.get(1));
    }

    @Test
    public void colonsInTheNameSpec_areInterpretedCorrectly() {
        parseArgs("--set", "replace:me:with:this=excellent value");
        assertNull(configFile);
        assertEquals(2, overrides.size());
        assertExpectedOverride(path("replace", "me", "with", "this"), null,
                overrides.get(0));
        assertExpectedOverride(path("replace", "me", "with", "this"),
                "excellent value", overrides.get(1));
    }

    // ----------------------------------------------------------------------
    // Tests on ConfigurationFieldPath
    // ----------------------------------------------------------------------

    @Test
    public void printAnEmptyFieldPath() {
        assertEquals("[]", new ConfigurationFieldPath().toString());
    }

    @Test
    public void printAPopulatedFieldPath() {
        ConfigurationFieldPath path = new ConfigurationFieldPath("one", "two")
                .add("three");
        assertEquals("[one, two, three]", path.toString());
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------

    private void parseArgs(String... args) {
        commandLine = new CommandLineOptions(args);
        configFile = commandLine.getConfigFile();
        overrides = commandLine.getOverrides();
    }

    private ConfigurationFieldPath path(String... keys) {
        return new ConfigurationFieldPath(keys);
    }

    private void assertExpectedOverride(ConfigurationFieldPath path,
            String value, AttributeOverride override) {
        assertEquals(path, override.keys);
        assertEquals(value, override.value);
    }
}
