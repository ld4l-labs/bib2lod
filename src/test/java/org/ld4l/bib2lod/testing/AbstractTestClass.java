package org.ld4l.bib2lod.testing;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public abstract class AbstractTestClass {


    // ----------------------------------------------------------------------
    // JSON utility methods
    // ----------------------------------------------------------------------

    protected TextNode jsonText(String text) {
        return JsonNodeFactory.instance.textNode(text);
    }
    
    protected TextNode emptyJsonText() {
        return JsonNodeFactory.instance.textNode("");
    }
    
    protected ArrayNode jsonArray() {
        return JsonNodeFactory.instance.arrayNode();
    }

    protected ObjectNode jsonObject() {
        return JsonNodeFactory.instance.objectNode();
    }

    
    private enum SpecialJsonValue {
        REMOVE
    }

    protected static final SpecialJsonValue JSON_REMOVE = 
            SpecialJsonValue.REMOVE;

    protected void setFieldValue(ObjectNode node, String fieldName,
            Object newValue) {
        if (newValue == null) {
            node.putNull(fieldName);
        } else if (newValue == SpecialJsonValue.REMOVE) {
            node.remove(fieldName);
        } else if (newValue instanceof JsonNode) {
            node.set(fieldName, (JsonNode) newValue);
        } else if (newValue instanceof Integer) {
            node.put(fieldName, (Integer) newValue);
        } else {
            node.put(fieldName, String.valueOf(newValue));
        }
    }  
    
    // ----------------------------------------------------------------------
    // Control standard output or error output.
    // ----------------------------------------------------------------------

    private static final PrintStream originalSysout = System.out;
    private static final PrintStream originalSyserr = System.err;
    private final ByteArrayOutputStream capturedSysout = new ByteArrayOutputStream();
    private final ByteArrayOutputStream capturedSyserr = new ByteArrayOutputStream();

    @Before
    @After
    public void restoreOutputStreams() {
        System.setOut(originalSysout);
        System.setErr(originalSyserr);
        capturedSysout.reset();
        capturedSyserr.reset();
    }

    protected void suppressSysout() {
        System.setOut(new PrintStream(capturedSysout, true));
    }

    protected void suppressSyserr() {
        System.setErr(new PrintStream(capturedSyserr, true));
    }

    protected String getSysoutForTest() {
        return capturedSysout.toString();
    }

    protected String getSyserrForTest() {
        return capturedSyserr.toString();
    }
      
}
