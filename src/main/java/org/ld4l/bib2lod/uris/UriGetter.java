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
     * Stores the list of UriGetters that will be used to mint URIs for 
     * Resources.
     */
    static List<UriGetter> minters = new ArrayList<UriGetter>();
    
    /**
     * Factory method
     * 
     * @param uriGetterClass - the class name of the UriGetter to instantiate
     */
    static UriGetter instance(String uriGetterClass, Configuration configuration) {

        return Bib2LodObjectFactory.instance().createUriGetter(uriGetterClass,
                configuration);
    }
    
    public static void createUriGetters(String[] minterClasses,
            Configuration configuration) {

        for (String minterClass : minterClasses) {
            minters.add(instance(minterClass, configuration));
        }
    }
    
    /**
     * Returns a URI for an Entity
     * @return - a URI String 
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
     * Iterates through the specified URIs to return a URI for an Entity
     * @return - a URI String 
     */
    String getUri(Entity entity, Iterator<UriGetter> it);
  
}
