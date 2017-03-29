/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;

/**
 * Parse the arguments from the command line.
 * 
 * <pre>
 * Valid arguments are:
 * -c ConfigurationFile
 * -o InputService:source=some.file
 * -o OutputService:ext
 * 
 * If no -c is specified, we will accept an environment variable instead.
 * </pre>
 */
public class CommandLineOptions implements ConfigurationOptions {
    private static final String ENV_CONFIG_FILE = "BIB2LOD_CONFIG_FILE";
    private String configFile;
    private List<AttributeOverride> overrides = new ArrayList<>();

    public CommandLineOptions(String... args) {
        getDefaultsFromEnvironment();
        parseArguments(args);
    }

    private void getDefaultsFromEnvironment() {
        try {
            String cf = System.getenv(ENV_CONFIG_FILE);
            if (cf != null) {
                configFile = cf;
            }
        } catch (SecurityException e) {
            // Somebody must have put us into a controlled environment.
        }
    }
    
    private void parseArguments(String... args) {
        for (Iterator<String> it = Arrays.asList(args).iterator(); it
                .hasNext();) {
            String key = it.next();
            if (key.equals("-c") || key.equals("-o")) {
                if (!it.hasNext()) {
                    throw new ConfigurationException(
                            "You must provide a value after '" + key + "'");
                }
                String value = it.next();
                if (value.startsWith("-")) {
                    throw new ConfigurationException(
                            "You must provide a value between keys '" + key
                                    + "' and '" + value + "'");
                }
                if (key.equals("-c")) {
                    configFile = value;
                } else {
                    parseOverride(value);
                }
            } else {
                throw new ConfigurationException(
                        "The only valid options are -c for the config file, "
                                + "and -o for an attribute override: '" + key
                                + "'");
            }
        }
    }

    private void parseOverride(String spec) {
        int firstEquals = spec.indexOf('=');
        String namestring;
        String value;
        if (firstEquals == -1) {
            namestring = spec;
            value = null;
        } else {
            namestring = spec.substring(0, firstEquals);
            value = spec.substring(firstEquals + 1);
        }

        if (namestring.contains(" ")) {
            throw new ConfigurationException(
                    "An override may not contain spaces to the left of "
                            + "the equals sign: '" + spec + "'");
        }

        List<String> names = Arrays.asList(namestring.split(":"));
        overrides.add(new AttributeOverride(names, value));
    }

    @Override
    public String getConfigFile() {
        return configFile;
    }

    @Override
    public List<AttributeOverride> getOverrides() {
        return overrides;
    }

}
