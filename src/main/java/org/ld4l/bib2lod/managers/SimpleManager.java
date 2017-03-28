/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.managers;

import java.io.IOException;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.ConfigurationNode;
import org.ld4l.bib2lod.configuration.Configurator;
import org.ld4l.bib2lod.configuration.DefaultBib2LodObjectFactory;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.conversion.Converter.ConverterException;
import org.ld4l.bib2lod.io.InputService;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.InputService.InputServiceException;
import org.ld4l.bib2lod.io.OutputService;
import org.ld4l.bib2lod.io.OutputService.OutputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputServiceException;

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
//            CommandLineOptions commandLine = new CommandLineOptions(args);
//            Configuration configuration = new JsonConfigurator(commandLine).getTopLevelConfiguration();
            Configuration configuration = new StubConfigurator().getTopLevelConfiguration();
            Bib2LodObjectFactory.setFactoryInstance(
                    new DefaultBib2LodObjectFactory(configuration));
            
            convert();
            LOGGER.info("END CONVERSION.");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            LOGGER.error("CONVERSION FAILED TO COMPLETE");
        }
    }

    /**
     * Converts all of the inputs from the InputService
     * 
     * @param configuration - the program Configuration 
     * @throws ConverterException
     */
    protected static void convert() {

        try {
            Converter converter = Converter.instance();
            InputService inputService = InputService.instance();
            OutputService outputService = OutputService.instance();

            Iterator<InputDescriptor> inputs = inputService.getDescriptors()
                    .iterator();
            while (inputs.hasNext()) {
                try (
                    InputDescriptor input = inputs.next();
                    OutputDescriptor output = outputService
                            .openSink(input.getMetadata())
                ) {
                    converter.convert(input, output);
                } catch (InputServiceException | OutputServiceException
                        | IOException | ConverterException e) {
                    // Log the error and continue to the next input.
                    // TODO We may want a more sophisticated reporting mechanism 
                    // for this type of error.
                    e.printStackTrace();
                }
            }
        } finally {
            // TODO write the report.
        }
    }
    
    /** Implements this Json.
     * <pre>
    {
        "local_namespace": "http://data.ld4l.org/cornell/",
        "InputService": {
         "class": "org.ld4l.bib2lod.io.FileInputService",
         "source": "/Users/rjy7/Workspace/bib2lod/src/test/resources/input/102063.min.xml",
         "extension": "xml"
        }, 
        "OutputService": {
         "class": "org.ld4l.bib2lod.io.FileOutputService",
         "destination": "/Users/rjy7/Workspace/bib2lod/src/test/resources/output/",
         "format": "N-TRIPLE"
        },
        "UriService": [
         { 
             "class": "org.ld4l.bib2lod.uri.RandomUriMinter"
         } 
        ],
        "Cleaner": {
         "class": "org.ld4l.bib2lod.cleaning.MarcxmlCleaner"
        },
        "Converter": {
         "class": "org.ld4l.bib2lod.conversion.to_rdf.ld4l.MarcxmlConverter"
        }
    }                                                                                             
      * </pre>
     */
    private static class StubConfigurator implements Configurator {
        @Override
        public Configuration getTopLevelConfiguration() {
            return new ConfigurationNode.Builder()
                    .addAttribute("local_namespace",
                            "http://data.ld4l.org/cornell/")
                    .addChild("InputService",
                            new ConfigurationNode.Builder()
                                    .setClassName(
                                            "org.ld4l.bib2lod.io.FileInputService")
                                    .addAttribute("source",
                                            "/Users/rjy7/Workspace/bib2lod/src/test/resources/input/102063.min.xml")
                                    .addAttribute("extension", "xml").build())
                    .addChild("OutputService",
                            new ConfigurationNode.Builder()
                                    .setClassName(
                                            "org.ld4l.bib2lod.io.FileOutputService")
                                    .addAttribute("destination",
                                            "/Users/rjy7/Workspace/bib2lod/src/test/resources/output/")
                                    .addAttribute("format", "N-TRIPLE").build())
                    .addChild("UriService",
                            new ConfigurationNode.Builder()
                                    .setClassName(
                                            "org.ld4l.bib2lod.uri.RandomUriMinter")
                                    .build())
                    .addChild("Cleaner",
                            new ConfigurationNode.Builder()
                                    .setClassName(
                                            "org.ld4l.bib2lod.cleaning.MarcxmlCleaner")
                                    .build())
                    .addChild("Converter",
                            new ConfigurationNode.Builder()
                                    .setClassName(
                                            "org.ld4l.bib2lod.conversion.to_rdf.ld4l.MarcxmlConverter")
                                    .build())
                    .build();
        }
    }
}
