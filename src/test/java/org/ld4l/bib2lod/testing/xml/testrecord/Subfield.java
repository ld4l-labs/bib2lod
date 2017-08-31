/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.testing.xml.testrecord;

import java.util.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Subfield {
    final String code;
    final String value;

    Subfield(Element element) {
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
    // builder
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
