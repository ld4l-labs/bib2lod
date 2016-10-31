package org.ld4l.bib2lod.manager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.JsonConfigConfiguration;
import org.ld4l.bib2lod.conversion.RecordConverter;
import org.xml.sax.SAXException;

// TODO Put common methods (like getInputFiles() into a base class or a 
// utility class?
public class SimpleManager {

    private static final Logger LOGGER = 
            LogManager.getLogger(SimpleManager.class);
    
    private static Configuration configuration;
    
    /** 
     * Read in program options and call appropriate conversion functionality.
     * @param args
     */
    public static void main(String[] args) {

        LOGGER.info("START CONVERSION.");

        try {
            configuration = buildConfiguration(args);
            convertFiles();
            LOGGER.info("END CONVERSION.");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } 
    }
    
    
    /**
     * Convert a list of input files
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     */
    private static void convertFiles() {
          
        RecordConverter recordConverter = new RecordConverter(configuration);
        List<File> inputFiles = configuration.getInput();
        for (File file : inputFiles) {
            String converted = recordConverter.convertFile(file);
            // TODO get back output and write it (use configuration writer)
        }       
    }


    /**
     * 
     */
    private static Configuration buildConfiguration(String[] args) 
            throws IOException, ParseException, ReflectiveOperationException {

        Configuration configuration = new JsonConfigConfiguration(args);
        return configuration;
    }
    
    public Configuration getConfiguration() {
        return configuration;
    }
    

        
}
