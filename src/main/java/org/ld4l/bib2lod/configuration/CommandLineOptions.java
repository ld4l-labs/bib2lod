/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;

/**
 * Parse the arguments from the command line.
 * 
 * <pre>
 * Valid arguments are:
 * 
 * option           example value                   description
 * ------------------------------------------------------------------------
 * -c or --config   ./ConfigurationFile             path to the config file
 * -a or --add      InputService:source=some.file   add a config value
 * -s or --set      Cleaner:class=some.Class        replace config values
 *                  Cleaner:class                   remove config values
 * -d or --drop     OutputService:ext               remove config values
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
            String keyString = it.next();
            String parameterString = it.hasNext() ? it.next() : null;

            Key key = Key.parse(keyString);
            Parameter parameter = Parameter.parse(parameterString);

            if (parameter == null) {
                throw new ConfigurationException(
                        "You must provide a parameter after '" + keyString
                                + "'");
            }

            if (key == Key.CONFIG) {
                if (parameter.value != null) {
                    throw new ConfigurationException(
                            "Command line parameter may not include an "
                                    + "equals sign: '" + parameterString + "'");
                }
                configFile = parameter.name;
            } else {
                overrides.addAll(key.process(parameter));
            }
        }
    }

    @Override
    public String getConfigFile() {
        return configFile;
    }

    @Override
    public List<AttributeOverride> getOverrides() {
        return overrides;
    }

    private enum Key {
        CONFIG("-c", "--config") {
            @Override
            List<AttributeOverride> process(Parameter p) {
                return null;
            }
        },
        ADD("-a", "--add") {
            @Override
            List<AttributeOverride> process(Parameter p) {
                if (p.value == null) {
                    throw new ConfigurationException(
                            "The parameter for 'add' must include a "
                                    + "replacement value.");
                }
                return Collections.singletonList(
                        new AttributeOverride(p.value, p.name.split(":")));
            }
        },
        DROP("-d", "--drop") {
            @Override
            List<AttributeOverride> process(Parameter p) {
                if (p.value != null) {
                    throw new ConfigurationException(
                            "The parameter for 'drop' must not include a "
                                    + "replacement value.");
                }
                return Collections.singletonList(
                        new AttributeOverride(null, p.name.split(":")));
            }
        },
        SET("-s", "--set") {
            @Override
            List<AttributeOverride> process(Parameter p) {
                String[] path = p.name.split(":");
                List<AttributeOverride> list = new ArrayList<>();

                list.add(new AttributeOverride(null, path));
                if (p.value != null) {
                    list.add(new AttributeOverride(p.value, path));
                }
                
                return list;
            }
        };

        private String[] strings;

        Key(String... strings) {
            this.strings = strings;
        }

        abstract List<AttributeOverride> process(Parameter v);

        static Key parse(String s) {
            for (Key k : Key.values()) {
                for (String keyString : k.strings) {
                    if (keyString.equals(s)) {
                        return k;
                    }
                }
            }
            throw new ConfigurationException(
                    "Valid options are -c, -a, -s, -d, --config, "
                            + "--add, --set, --delete: '" + s + "'");
        }
    }

    private static class Parameter {
        static Parameter parse(String s) {
            if (s == null || s.startsWith("-")) {
                return null;
            }

            int firstEquals = s.indexOf('=');
            if (firstEquals == -1) {
                return new Parameter(s, null);
            } else {
                return new Parameter(s.substring(0, firstEquals),
                        s.substring(firstEquals + 1));
            }

        }

        private final String name;
        private final String value;

        public Parameter(String name, String value) {
            this.name = name;
            this.value = value;

            if (name.contains(" ")) {
                throw new ConfigurationException(
                        "A parameter may not contain spaces to the left of "
                                + "the equals sign: '" + name + "'");
            }
        }

    }

}
