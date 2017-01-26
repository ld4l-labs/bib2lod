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
    
    public String getUriFor(Entity entity);
    
    // TODO Since we are using the Entity to get the URI, we might just as well 
    // do this in building the Entity rather than the Model.
    public static String getUri(Entity entity) {
        
        String uri = null;
        Iterator<UriGetter> it = minters.iterator();
        while (it.hasNext()) {
            UriGetter minter = it.next();
            uri = minter.getUriFor(entity);
        }
        return uri;
    }
  
}
