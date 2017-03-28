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
 * </pre>
 */
public class ArgumentsParser implements Arguments {
    private String configFile;
    private List<AttributeOverride> overrides = new ArrayList<>();

    public ArgumentsParser(String[] args) {
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
            }
        }
    }

    private void parseOverride(String value) {
        throw new RuntimeException(
                "ArgumentsParser.parseOverride not implemented.");
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
