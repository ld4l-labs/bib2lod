package org.ld4l.bib2lod.entity;

import org.apache.jena.datatypes.xsd.XSDDatatype;

/**
 * Represents the XSD datatypes of Attributes.
 */
public enum Datatype {

    DATE(XSDDatatype.XSDdate),
    INT(XSDDatatype.XSDint),
    STRING(XSDDatatype.XSDstring);
    
    private XSDDatatype xsdDatatype;
    
    private Datatype(XSDDatatype type) {
        this.xsdDatatype = type;
    }
    
    public XSDDatatype xsdDatatype() {
        return xsdDatatype;
    }
}
