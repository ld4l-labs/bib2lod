package org.ld4l.bib2lod.entity;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.datatypes.Datatype;
import org.ld4l.bib2lod.datatypes.XsdDatatype;

/**
 * Represents a literal value (object of a datatype property).
 */
public class Attribute {
    
    private final String value;
    private final String lang;
    private Datatype datatype;

    /**
     * Constructor
     */
    // Only called from this class's other public constructors, since an 
    // Attribute shouldn't have both a language and a datatype.
    private Attribute(String value, String lang, Datatype datatype) {
        this.value = value;
        this.lang = lang;
        this.datatype = datatype;
    }
    
    /**
     * Constructor
     */
    public Attribute(String value, String lang) {
        this(value, lang, null);
    }
    
    /**
     * Constructor
     */
    public Attribute(String value) {
        this(value, null, null);
    }
    
    /**
     * Constructor
     */
    public Attribute(String value, Datatype type) {
        this(value, null, type);
    }
    
    /**
     * Constructor
     */
    public Attribute(int i) {
        this(Integer.toString(i), null, XsdDatatype.INT);
    }
    
    public String getValue() {
        return value;
    }
       
    public String getLang() {
        return lang;
    }
    
    public Datatype getDatatype() {
        return datatype;
    }
    
    public Literal toLiteral() {
        if (datatype != null) {
            return ResourceFactory.createTypedLiteral(
                    value, datatype.rdfType());
        }
        if (lang != null) {
            return ResourceFactory.createLangLiteral(value, lang);
        }
        return ResourceFactory.createStringLiteral(value);
    }
}
