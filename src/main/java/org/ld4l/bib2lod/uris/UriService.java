package org.ld4l.bib2lod.uris;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entities.Entity;

/**
 * Provides URIs for org.ld4l.bib2lod.entities built by the converter.
 */
public interface UriService {
    
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
     * Stores the list of UriServices that will be used to provide URIs for 
     * Resources.
     */
    static List<UriService> uriServices = new ArrayList<UriService>();
    
    /**
     * Factory method
     * @param uriServiceClass - the class name of the UriService to instantiate
     */
    static UriService instance(String uriServiceClass, Configuration configuration) {

        return Bib2LodObjectFactory.instance().createUriService(uriServiceClass,
                configuration);
    }
    
    public static void createUriServices(String[] uriServiceClasses,
            Configuration configuration) {

        for (String uriServiceClass : uriServiceClasses) {
            uriServices.add(instance(uriServiceClass, configuration));
        }
    }
    
    /**
     * Returns a URI for an Entity
     * @return - a URI String 
     */
    public static String getUri(Entity entity) {
        
        String uri = null;
        
        // Use an iterator so each service can call the next service if needed.
        Iterator<UriService> it = uriServices.iterator();
        
        if (it.hasNext()) {
            uri = it.next().getUri(entity, it);
        }
     
        if (uri == null) {
            throw new NullUriException(entity);
        }
        
        return uri;
    }
    
    /**
     * Iterates through the specified URIs to return a URI for an Entity
     * @return - a URI String 
     */
    String getUri(Entity entity, Iterator<UriService> it);

}
