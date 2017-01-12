package org.ld4l.bib2lod.uri;

import java.util.List;

public interface UriMinter {
    
    // TODO Should this go here or in BaseUriMinter? Read about static methods
    // in interfaces.
    static List<UriMinter> minters = null;

    // TODO Should this go here or in BaseUriMinter? Read about static methods
    // in interfaces.
    static List<UriMinter> getMinters() {
        return null;
    }
    

    public String getLocalNamespace();
    
    public String mint();
  
}
