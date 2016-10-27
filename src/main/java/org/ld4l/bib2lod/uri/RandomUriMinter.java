package org.ld4l.bib2lod.uri;

import java.util.UUID;

import org.ld4l.bib2lod.util.MurmurHash;

public class RandomUriMinter extends BaseUriMinter {

    private String LOCAL_NAME_ALPHA_PREFIX = "n";
    
    public RandomUriMinter(String localNamespace) {
        super(localNamespace);
    }
    
    protected String mintLocalName() {
        String uuid = UUID.randomUUID().toString();
        // NB A digit is not a legal initial character of a local name in 
        // RDF/XML; see http://www.w3.org/TR/xml11/#NT-NameStartChar, so 
        // prefix a character to the hashed UUID.
        return LOCAL_NAME_ALPHA_PREFIX + hash(uuid);
    }
    
    private String hash(String s) {
        long hash64 = MurmurHash.hash64(s);
        return Long.toHexString(hash64);        
    }

}
