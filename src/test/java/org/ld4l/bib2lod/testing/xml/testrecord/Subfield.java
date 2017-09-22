/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.testing.xml.testrecord;

import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Subfield {
    final String code;
    final String value;

    public Subfield(Element element) {
        this.code = element.getAttribute("code");
        this.value = element.getTextContent();
    }

    Subfield(Builder builder) {
        this.code = builder.code;
        this.value = builder.value;
    }

    Node element(Document doc) {
        Element element = doc.createElement("subfield");
        element.setAttribute("code", code);
        element.insertBefore(doc.createTextNode(value), element.getLastChild());
        return element;
    }

    Builder builder(MockMarcxml.Builder mmb, Datafield.Builder df) {
        return new Builder(mmb, df, this.code, this.value);
    }
    
    /**
     * Create an XML structure from this instance.
     */
    public Element toElement() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element element = doc.createElement("subfield");
            element.setAttribute("code", code);
            element.setTextContent(value);
            return element;
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Can't convert to XML", e);
        }
    }
    
    
    MarcxmlSubfield toSubfield() 
            throws RecordFieldException {
        return new MarcxmlSubfield(this.toElement());         
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Subfield) {
            Subfield that = (Subfield) obj;
            return Objects.equals(code, that.code)
                    && Objects.equals(value, that.value);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Subfield[code=\"" + code + "\", \"" + value + "\"]";
    }

    // ----------------------------------------------------------------------
    // Builder
    // ----------------------------------------------------------------------

    public static class Builder extends SubfieldFinder.Wrapper {
        private String code;
        private String value;

        Builder(MockMarcxml.Builder mmb, Datafield.Builder df, String code,
                String value) {
            super(mmb, df);
            this.code = code;
            this.value = value;
        }

        public Subfield.Builder setValue(String newValue) {
            this.value = newValue;
            return this;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        Subfield buildSubfield() {
            return new Subfield(this);
        }

        @Override
        public int hashCode() {
            return Objects.hash(code, value);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Subfield.Builder) {
                Subfield.Builder that = (Subfield.Builder) obj;
                return Objects.equals(code, that.code)
                        && Objects.equals(value, that.value);
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return "Subfield.Builder[code=\"" + code + "\", \"" + value + "\"]";
        }
    }
}
