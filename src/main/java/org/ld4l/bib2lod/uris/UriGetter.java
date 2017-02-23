package org.ld4l.bib2lod.uris;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entities.Entity;

/**
 * Provides URIs for org.ld4l.bib2lod.entities built by the converter.
 */
// TODO Change to UriGetter, UriGenerator etc - since doesn't always mint a 
// new one
public interface UriGetter {
    
    /**
     * Signals that the content of a configuration value is invalid.  Differs
     * from empty, null, or invalid types, which are handled by JsonUtils
     * exceptions, which are content-neutral. The ConfigurationFromJson object 
     * evaluates the contents of the value.
     */
    public static class NullUriException extends RuntimeException {         
        private static final long serialVersionUID = 1L;
        
        protected NullUriException(Entity entity) {
            // TODO Need to reference the Entity somehow
            super("No URI generated for ??");                 
        }
    }
    
    /**
     * Stores the list of UriMinters that will be used to mint URIs for 
     * Resources.
     */
    static List<UriGetter> minters = new ArrayList<UriGetter>();
    
    /**
     * Factory method
     * @param minter 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    static UriGetter instance(String minterClass, Configuration configuration) 
            throws ClassNotFoundException, FileNotFoundException, IOException, 
                ParseException, InstantiationException, IllegalAccessException, 
                    IllegalArgumentException, InvocationTargetException, 
                        NoSuchMethodException, SecurityException {      
        
        return Bib2LodObjectFactory.instance().createUriMinter(
                minterClass, configuration);
    }
    
    public static void createMinters(String[] minterClasses,  
            Configuration configuration) throws ClassNotFoundException, 
                FileNotFoundException, IOException, ParseException, 
                    InstantiationException, IllegalAccessException, 
                        IllegalArgumentException, InvocationTargetException, 
                            NoSuchMethodException, SecurityException {
        
        for (String minterClass : minterClasses) {
            minters.add(instance(minterClass, configuration));
        }
    }
    
    /**
     * 
     * @param entity - the Entity to build the URI for
     * @return
     */
    // TODO Since we are using the Entity to get the URI, we might just as well 
    // do this when d building the Entity rather than the Resource/Model.
    public static String getUri(Entity entity) {
        
        String uri = null;
        
        Iterator<UriGetter> it = minters.iterator();
        
        if (it.hasNext()) {
            uri = it.next().getUri(entity, it);
        }
     
        if (uri == null) {
            throw new NullUriException(entity);
        }
        
        return uri;
    }
    
    /**
     * 
     * @param entity
     * @param it
     * @return
     */
    String getUri(Entity entity, Iterator<UriGetter> it);
  
}
