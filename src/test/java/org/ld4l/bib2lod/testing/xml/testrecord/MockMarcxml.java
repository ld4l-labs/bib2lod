/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.testing.xml.testrecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * An immutable structured class that represents a Marcxml record.
 * 
 * Because it is immutable, any modifier method creates a new MockMarcxml
 * instance, populated with new fields and subfield instances.
 */
public class MockMarcxml {
    /** A useful starting place for test values. */
    public static final MockMarcxml MINIMAL_RECORD = parse(
            MarcxmlTestUtils.MINIMAL_RECORD);

    /**
     * The entry point. Since constructors are restricted, use this to create an
     * instance.
     */
    public static MockMarcxml parse(String string) {
        return new MockMarcxml(string);
    }

    // ----------------------------------------------------------------------
    // The instance
    // ----------------------------------------------------------------------

    /** An unmodifiable list. */
    final List<Field> fields;

    /**
     * Called when parsing from an XML string.
     */
    MockMarcxml(String string) {
        List<Field> list = new ArrayList<>();
        try {
            Element element = XmlTestUtils.buildElementFromString(string);
            for (Element child : iterableElements(element.getChildNodes())) {
                String tagName = child.getTagName();
                if (tagName.equals("leader")) {
                    list.add(new Leader(child));
                } else if (tagName.equals("controlfield")) {
                    list.add(new Controlfield(child));
                } else if (tagName.equals("datafield")) {
                    list.add(new Datafield(child));
                } else {
                    throw new RuntimeException(
                            "Unrecognized element: " + tagName);
                }
            }
            this.fields = Collections.unmodifiableList(new ArrayList<>(list));
        } catch (RecordFieldException e) {
            throw new RuntimeException("Failed to parse MockMarcxml", e);
        }
    }

    MockMarcxml(MockMarcxml.Builder builder) {
        List<Field> list = new ArrayList<>();
        for (Field.Builder fb : builder.fieldBuilders) {
            list.add(fb.buildField());
        }
        this.fields = Collections.unmodifiableList(new ArrayList<>(list));
    }

    /**
     * Create an XML structure from this instance and its children.
     */
    public Element toElement() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element element = doc.createElement("record");
            for (Field f : fields) {
                element.appendChild(f.element(doc));
            }
            return element;
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Can't convert to XML", e);
        }
    }
    
    /**
     * Convenience method to create a MarcxmlRecord object.
     */
    public MarcxmlRecord toRecord() throws RecordException {
        return new MarcxmlRecord(this.toElement());
    }

    public Builder openCopy() {
        return new Builder(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fields);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MockMarcxml) {
            return Objects.equals(fields, ((MockMarcxml) obj).fields);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "MockMarcxml[fields=" + fields + "]";
    }

    // ----------------------------------------------------------------------
    // builder
    // ----------------------------------------------------------------------

    public static class Builder extends FieldFinder.Impl {
        private List<Field.Builder> fieldBuilders = new ArrayList<>();

        public Builder(MockMarcxml mockMarcxml) {
            mmb = this;
            for (Field f : mockMarcxml.fields) {
                fieldBuilders.add(f.builder(this));
            }
        }

        public List<Field.Builder> getFieldBuilders() {
            return fieldBuilders;
        }

    }

    // ----------------------------------------------------------------------
    // Utility methods.
    // ----------------------------------------------------------------------

    static List<Element> iterableElements(NodeList nodes) {
        List<Element> list = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node instanceof Element) {
                list.add((Element) node);
            }
        }
        return list;
    }
}
