package org.ld4l.bib2lod.uris;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configurable;
import org.ld4l.bib2lod.entity.Entity;

/**
 * Provides URIs for org.ld4l.bib2lod.entities built by the converter.
 */
public interface UriService extends Configurable {
    
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
    static List<UriService> instances() {
        return Bib2LodObjectFactory.getFactory()
                .instancesForInterface(UriService.class);
    }
    
    /**
     * Returns a URI for an Entity
     * @return - a URI String 
     */
    public static String getUri(Entity entity) {
        return getUri(entity, (String) null);
    }

    /**
     * Returns a URI for an Entity
     * @return - a URI String 
     */
    public static String getUri(Entity entity, String string) {
        String uri = null;
        
        // Use an iterator so each service can call the next service if needed.
        Iterator<UriService> it = instances().iterator();
        
        if (it.hasNext()) {
            uri = it.next().getUri(entity, it, string);
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
    String getUri(Entity entity, Iterator<UriService> it, String string);

}
