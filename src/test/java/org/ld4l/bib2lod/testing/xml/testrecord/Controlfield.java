/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.testing.xml.testrecord;

import java.util.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

class Controlfield implements Field {
    final String tag;
    final String value;

    Controlfield(Element element) {
        this.tag = element.getAttribute("tag");
        this.value = element.getTextContent();
    }

    private Controlfield(Builder builder) {
        this.tag = builder.tag;
        this.value = builder.value;
    }

    @Override
    public Element element(Document doc) {
        Element element = doc.createElement("controlfield");
        element.setAttribute("tag", tag);
        element.insertBefore(doc.createTextNode(value), element.getLastChild());
        return element;
    }

    @Override
    public Builder builder(MockMarcxml.Builder mmb) {
        return new Builder(mmb, this.tag, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Controlfield) {
            return Objects.equals(tag, ((Controlfield) obj).tag)
                    && Objects.equals(value, ((Controlfield) obj).value);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Controlfield[tag=\"" + tag + "\", \"" + value + "\"]";
    }

    // ----------------------------------------------------------------------
    // builder
    // ----------------------------------------------------------------------

    public static class Builder extends FieldFinder.Wrapper implements Field.Builder {
        private String tag;
        private String value;

        public Builder(MockMarcxml.Builder mmb, String tag, String value) {
            super(mmb);
            this.tag = tag;
            this.value = value;
        }

        public Controlfield.Builder setValue(String newValue) {
            this.value = newValue;
            return this;
        }

        String getTag() {
            return tag;
        }

        String getValue() {
            return value;
        }

        @Override
        public boolean isControlfield() {
            return true;
        }

        @Override
        public Controlfield buildField() {
            return new Controlfield(this);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tag, value);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Controlfield.Builder) {
                return Objects.equals(tag, ((Controlfield.Builder) obj).tag)
                        && Objects.equals(value,
                                ((Controlfield.Builder) obj).value);
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return "Controlfield.Builder[tag=\"" + tag + "\", \"" + value
                    + "\"]";
        }
    }
}
