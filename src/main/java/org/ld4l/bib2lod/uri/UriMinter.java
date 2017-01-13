package org.ld4l.bib2lod.uri;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configuration;

/**
 * Provides URIs for entities built by the converter.
 */
public interface UriMinter {
    
    // TODO Should this go here or in BaseUriMinter? Read about static methods
    // in interfaces.
    static List<UriMinter> minters = null;
    
    /**
     * Factory method
     */
    static UriMinter instance(Configuration configuration) 
            throws ClassNotFoundException, FileNotFoundException, IOException, 
            ParseException {         
        return Bib2LodObjectFactory.instance().createUriMinter(configuration);
    }

    // TODO Should this go here or in BaseUriMinter? Read about static methods
    // in interfaces.
    static List<UriMinter> getMinters() {
        return null;
    }
    

    
    public String getLocalNamespace();
    
    public String mint();
  
}
