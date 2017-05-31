/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.AttributeCascader;
import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.CommandLineOptions;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;
import org.ld4l.bib2lod.configuration.ConfigurationOverrider;
import org.ld4l.bib2lod.configuration.DefaultBib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.JsonConfigurationFileParser;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.conversion.Converter.ConverterException;
import org.ld4l.bib2lod.io.InputService;
import org.ld4l.bib2lod.io.OutputService;

/**
 * Orchestrates conversion of a directory of files or a single file.
 */
public final class SimpleManager {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Main method: gets a Configuration object and calls the conversion method.
     * 
     * @param args - commandline arguments          
     */
    public static void main(String[] args) {

        LOGGER.info("START CONVERSION.");

        try {
        	SimpleManager manager = new SimpleManager(args);
        	manager.convert();
            LOGGER.info("END CONVERSION.");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            LOGGER.error("CONVERSION FAILED TO COMPLETE");
        }
    }
    
    /**
     * Parses the command line options, reads the config file, and adjusts as
     * necessary.
     * 
     * @param args - Command line arguments most likely passed from main().
     */
    public SimpleManager(String[] args) {
        CommandLineOptions commandLine = new CommandLineOptions(args);

        Configuration configuration;
        configuration = new JsonConfigurationFileParser(commandLine)
                .getTopLevelConfiguration();
        configuration = new ConfigurationOverrider(commandLine)
                .override(configuration);
        configuration = new AttributeCascader().cascade(configuration);
        setUpObjectFactory(configuration);
    }
    
    /**
     * Reads the config file to set up application without any (command line) adjustments. 
     * 
     * @param jsonConfigFilePath - Path to the JSON configuration file.
     */
    public SimpleManager(String jsonConfigFilePath) {
    	
    	InputStream jsonInput = null;
    	try {
    		jsonInput = readConfigFile(jsonConfigFilePath);
    		Configuration configuration = new JsonConfigurationFileParser(jsonInput)
    				.getTopLevelConfiguration();
    		setUpObjectFactory(configuration);
    	} finally {
			if (jsonInput != null) {
				try {
					jsonInput.close();
				} catch (IOException e) {}
			}
		}
    }
    
    /**
     * Reads the config file to set up application without any (command line) adjustments. 
     * 
     * @param jsonInput - InputStream of the JSON configuration file.
     */
    public SimpleManager(FileInputStream jsonInput) {
        Configuration configuration = new JsonConfigurationFileParser(jsonInput)
    			.getTopLevelConfiguration();
        setUpObjectFactory(configuration);
    }

    /**
     * Converts all of the inputs from the InputService.
     */
    void convert() {

        Converter converter = Converter.instance();
        InputService inputService = InputService.instance();
        OutputService outputService = OutputService.instance();
        try {
            converter.convertAll(inputService, outputService);
        } catch (ConverterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // TODO Write report
    }
    
    /**
     * Sets up the object factory.
     */
    private void setUpObjectFactory(Configuration configuration) {
    	configuration = new AttributeCascader().cascade(configuration);
        Bib2LodObjectFactory.setFactoryInstance(
                new DefaultBib2LodObjectFactory(configuration));
    }
    
    /**
     * Reads in JSON configuration file.
     * 
     * @param jsonConfigFilePath - Path to JSON configuration file.
     * @return The InputStream of the configuration file.
     */
    private InputStream readConfigFile(String jsonConfigFilePath) {
        try {
            if (jsonConfigFilePath == null) {
                throw new ConfigurationException(
                        "No configuration file specified.");
            }

            File file = new File(jsonConfigFilePath).getAbsoluteFile();
            if (!file.isFile()) {
                throw new ConfigurationException(
                        "Configuration file does not exist: " + file);
            }
            if (!file.canRead()) {
                throw new ConfigurationException(
                        "No permission to read configuration file: " + file);
            }

            return new FileInputStream(file);
        } catch (IOException e) {
            throw new ConfigurationException(
                    "Failed to open the configuration file: " + jsonConfigFilePath, e);
        }
    }
}
