/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.testing.xml.testrecord;

import java.util.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Represents the leader in the MockMarxml structure.
 */
public class Leader implements Field {
    final String value;

    Leader(Element element) {
        this.value = element.getTextContent();
    }

    private Leader(Builder builder) {
        this.value = builder.value;
    }

    @Override
    public Element element(Document doc) {
        Element element = doc.createElement("leader");
        element.insertBefore(doc.createTextNode(value), element.getLastChild());
        return element;
    }

    @Override
    public Builder builder(MockMarcxml.Builder mmb) {
        return new Builder(mmb, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Leader) {
            return Objects.equals(value, ((Leader) obj).value);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Leader[\"" + value + "\"]";
    }

    // ----------------------------------------------------------------------
    // builder
    // ----------------------------------------------------------------------

    public static class Builder extends FieldFinder.Wrapper implements Field.Builder {
        private String value;

        public Builder(MockMarcxml.Builder mmb, String value) {
            super(mmb);
            this.value = value;
        }

        public Leader.Builder setValue(String newValue) {
            this.value = newValue;
            return this;
        }

        @Override
        public boolean isLeader() {
            return true;
        }

        @Override
        public Leader buildField() {
            return new Leader(this);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Leader.Builder) {
                return Objects.equals(value, ((Leader.Builder) obj).value);
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return "Leader.Builder[\"" + value + "\"]";
        }

    }
}
