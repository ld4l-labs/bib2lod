package org.ld4l.bib2lod.uri;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.util.MurmurHash;

/**
 * A UriMinter that constructs a URI from the local namespace and a randomly-
 * generated and hashed local name. An alpha character is prefixed to support
 * serializations (e.g., RDF/XML) and APIs (e.g., Jena) that do not allow or
 * understand local names beginning with non-alphabetic characters.
 */
public class RandomUriMinter extends BaseUriMinter {
    
    private static final Logger LOGGER = LogManager.getLogger(); 

    private String LOCAL_NAME_ALPHA_PREFIX = "n";
    
    /**
     * Constructor.
     * @param configuration
     */
    public RandomUriMinter(Configuration configuration) {
        super(configuration);
    }

    /**
     * Generates a local name from a random, hashed UUID. An alphabetic 
     * character is prefixed to simplify interactions with serializations and
     * APIs that do not allow or understand local names beginning with 
     * non-alphabetic characters.
     */
    protected String mintLocalName() {
        String uuid = UUID.randomUUID().toString();
        // NB A digit is not a legal initial character of a local name in 
        // RDF/XML; see http://www.w3.org/TR/xml11/#NT-NameStartChar, so 
        // prefix a character to the hashed UUID.
        String localName = LOCAL_NAME_ALPHA_PREFIX + hash(uuid);
        LOGGER.debug("Local name = " + localName);
        return localName;   
    }

    /**
     * Hashes a string using the MurmurHash algorithm.
     * @param s - the input string
     * @return the hashed string
     */
    protected String hash(String s) {
        long hash64 = MurmurHash.hash64(s);
        return Long.toHexString(hash64);        
    }

}
