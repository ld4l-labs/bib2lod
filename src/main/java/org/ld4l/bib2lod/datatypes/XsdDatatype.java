package org.ld4l.bib2lod.datatypes;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.xsd.XSDDatatype;

/**
 * Represents the XSD datatypes of Attributes.
 */
public enum XsdDatatype implements Datatype {

    DATE(XSDDatatype.XSDdate),
    DATETIME(XSDDatatype.XSDdateTime),
    INT(XSDDatatype.XSDint),
    STRING(XSDDatatype.XSDstring);
    
    private XSDDatatype xsdDatatype;
    
    private XsdDatatype(XSDDatatype type) {
        this.xsdDatatype = type;
    }
    
    @Override
    public RDFDatatype rdfType() {
        return xsdDatatype;
    }

}
