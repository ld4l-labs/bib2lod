package org.ld4l.bib2lod.entity;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.datatypes.Datatype;
import org.ld4l.bib2lod.datatypes.XsdDatatype;

/**
 * Represents a literal value (object of a datatype property).
 */
public class Attribute {
    
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogManager.getLogger(); 
    
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
    
    /**
     * Returns the Attribute value, which is always non-null.
     */
    public String getValue() {
        return value;
    }
      
    /**
     * Returns the Attribute's xml:lang value, or null if none exists.
     */
    public String getLang() {
        return lang;
    }
    
    /**
     * Returns the Attribute's datatype, or null if none exists.
     */
    public Datatype getDatatype() {
        return datatype;
    }
    
    /**
     * Returns the literal built from this Attribute.
     */
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
    
    /**
     * Returns true iff this Attribute has the same value, datatype, and
     * language as the other Attribute. Returns false if the other Attribute
     * is null.
     */
    public boolean isDuplicate(Attribute other) {
        if (other == null) {
            return false;
        }
        return toLiteral().equals(other.toLiteral());
    }
}
