package org.ld4l.bib2lod.conversion;

import java.io.File;

public interface Converter {

    public String convertFile(String text);
    
    public String convertFile(File file);
    
    // public String convertRecord(Record record);
}
