package org.ld4l.bib2lod.uri;

public class RandomUriMinter extends BaseUriMinter {

    public RandomUriMinter(String localNamespace) {
        super(localNamespace);
    }
    
    public String mintUri() {
        return "http://ld4l.org/cornell/xyz";
    }

}
