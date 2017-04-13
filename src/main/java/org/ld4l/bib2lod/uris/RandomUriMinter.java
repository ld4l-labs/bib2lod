package org.ld4l.bib2lod.uris;

import java.util.Iterator;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;

/**
 * A UriService that constructs a URI from the local namespace and a randomly-
 * generated and hashed local name. An alpha character is prefixed to support
 * serializations (e.g., RDF/XML) and APIs (e.g., Jena) that do not allow or
 * understand local names beginning with non-alphabetic characters.
 */
public class RandomUriMinter extends BaseUriService {
    
    private static final Logger LOGGER = LogManager.getLogger(); 

    private String LOCAL_NAME_ALPHA_PREFIX = "n";
    
    @Override
    public String getUri(Entity entity, Iterator<UriService> it) {
        String localName = getLocalName(entity);
        return buildUri(localName);
    }

    /**
     * Generates a local name from a random, hashed UUID. An alphabetic 
     * character is prefixed to simplify interactions with serializations and
     * APIs that do not allow or understand local names beginning with 
     * non-alphabetic characters.
     */
    protected String getLocalName(Entity entity) {
        String uuid = UUID.randomUUID().toString();
        // NB A digit is not a legal initial character of a local name in 
        // RDF/XML; see http://www.w3.org/TR/xml11/#NT-NameStartChar, so 
        // prefix a character to the hashed UUID.
        String localName = LOCAL_NAME_ALPHA_PREFIX + hash(uuid);
        return localName;   
    }

    /**
     * Returns a hashed string using the MurmurHash algorithm.
     */
    protected String hash(String s) {
        long hash64 = MurmurHash.hash64(s);
        return Long.toHexString(hash64);        
    }

}
