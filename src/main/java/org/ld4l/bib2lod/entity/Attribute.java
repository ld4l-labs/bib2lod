package org.ld4l.bib2lod.entity;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;

/**
 * Represents a literal value (object of a datatype property).
 */
public class Attribute {
    
    private final String value;
    private final String lang;
    private final Datatype datatype;
    
    /**
     * Constructors
     */
    public Attribute(String value, String lang, Datatype datatype) {
        this.value = value;
        this.lang = lang;
        this.datatype = datatype;
    }
    
    public Attribute(String value, String lang) {
        this(value, lang, null);
    }
    
    public Attribute(String value) {
        this(value, null, null);
    }
    
    public Attribute(String value, Datatype type) {
        this(value, null, type);
    }
    
    public Attribute(int i) {
        this(Integer.toString(i), null, Datatype.INT);
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
                    value, datatype.xsdDatatype());
        }
        if (lang != null) {
            return ResourceFactory.createLangLiteral(value, lang);
        }
        return ResourceFactory.createStringLiteral(value);
    }
}
