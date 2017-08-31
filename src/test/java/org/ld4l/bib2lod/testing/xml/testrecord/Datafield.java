/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.testing.xml.testrecord;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.iterableElements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Datafield implements Field {
    final String tag;
    final String ind1;
    final String ind2;
    final List<Subfield> subfields;

    public Datafield(Element element) {
        List<Subfield> list = new ArrayList<>();
        for (Element child : iterableElements(element.getChildNodes())) {
            String tagName = child.getTagName();
            if (tagName.equals("subfield")) {
                list.add(new Subfield(child));
            } else {
                throw new RuntimeException("Unrecognized element: " + tagName);
            }
        }
        this.tag = element.getAttribute("tag");
        this.ind1 = element.getAttribute("ind1");
        this.ind2 = element.getAttribute("ind2");
        this.subfields = Collections.unmodifiableList(list);
    }

    public Datafield(Builder builder) {
        this.tag = builder.tag;
        this.ind1 = builder.ind1;
        this.ind2 = builder.ind2;
        List<Subfield> list = new ArrayList<>();
        for (Subfield.Builder sfb : builder.subfields) {
            list.add(sfb.buildSubfield());
        }
        this.subfields = Collections.unmodifiableList(list);
    }

    @Override
    public Element element(Document doc) {
        Element element = doc.createElement("datafield");
        element.setAttribute("tag", tag);
        element.setAttribute("ind1", ind1);
        element.setAttribute("ind2", ind2);
        for (Subfield sf : subfields) {
            element.appendChild(sf.element(doc));
        }
        return element;
    }

    @Override
    public Builder builder(MockMarcxml.Builder mmb) {
        return new Builder(mmb, this.tag, this.ind1, this.ind2, this.subfields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, ind1, ind2, subfields);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Datafield) {
            Datafield that = (Datafield) obj;
            return Objects.equals(tag, that.tag)
                    && Objects.equals(ind1, that.ind1)
                    && Objects.equals(ind2, that.ind2)
                    && Objects.equals(subfields, that.subfields);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Datafield[tag=\"" + tag + "\", ind1=\"" + ind1 + "\", ind2=\""
                + ind2 + "\", subfields=" + subfields + "]";
    }

    // ----------------------------------------------------------------------
    // builder
    // ----------------------------------------------------------------------

   public static class Builder extends SubfieldFinder.Impl implements Field.Builder {
        private String tag;
        private String ind1;
        private String ind2;
        private List<Subfield.Builder> subfields;

        Builder(MockMarcxml.Builder mmb, String tag, String ind1, String ind2,
                List<Subfield> subfields) {
            this.mmb = mmb;
            this.dfb = this;
            this.tag = tag;
            this.ind1 = ind1;
            this.ind2 = ind2;

            this.subfields = new ArrayList<>();
            for (Subfield sf : subfields) {
                this.subfields.add(sf.builder(mmb, this));
            }
        }

        @Override
        public Datafield buildField() {
            return new Datafield(this);
        }

        public Datafield.Builder setInd1(String newInd1) {
            this.ind1 = newInd1;
            return this;
        }

        public Datafield.Builder setInd2(String newInd2) {
            this.ind2 = newInd2;
            return this;
        }

        @Override
        public boolean isDatafield() {
            return true;
        }

        public String getTag() {
            return tag;
        }

        public String getInd1() {
            return ind1;
        }

        public String getInd2() {
            return ind2;
        }

        public List<Subfield.Builder> getSubfields() {
            return subfields;
        }

        @Override
        public int hashCode() {
            return Objects.hash(tag, ind1, ind2, subfields);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Datafield.Builder) {
                Datafield.Builder that = (Datafield.Builder) obj;
                return Objects.equals(tag, that.tag)
                        && Objects.equals(ind1, that.ind1)
                        && Objects.equals(ind2, that.ind2)
                        && Objects.equals(subfields, that.subfields);
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return "Datafield.Builder[tag=\"" + tag + "\", ind1=\"" + ind1
                    + "\", ind2=\"" + ind2 + "\", subfields=" + subfields + "]";
        }

    }
}
