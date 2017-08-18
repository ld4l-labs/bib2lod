/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.ld4l.bib2lod.util.collections.MapOfLists;
import org.w3c.dom.Element;


/**
 * Tests class MarcxmlDataField.
 */
public class MarcxmlDataFieldTest extends AbstractTestClass {
 
    private static final String NO_TAG = 
            "<datafield ind1='0' ind2='0'>" +
                "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String EMPTY_TAG = 
            "<datafield tag='' ind1='0' ind2='0'>" +
                "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String BLANK_TAG = 
            "<datafield tag=' ' ind1='0' ind2='0'>" +
                "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String INVALID_TAG = 
            "<datafield tag='1234' ind1='0' ind2='0'>" +
                "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String NO_VALUE = 
            "<datafield tag='245' ind1='0' ind2='0'></datafield>";
    
    private static final String NON_SUBFIELD_CHILD = 
            "<datafield tag='245' ind1='0' ind2='0'>" +
                 "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
                 "<child>test</child>" +
             "</datafield>";

    private static final String INVALID_SUBFIELD = 
            "<datafield tag='245' ind1='0' ind2='0'>" +
                "<subfield>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String INVALID_FIELD_245 = 
            "<datafield tag='245' ind1='0' ind2='0'>" +
                "<subfield code='c'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String VALID_DATAFIELD = 
            "<datafield tag='245' ind1='0' ind2='0'>" +
                "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String REPEATED_SUBFIELDS = 
            "<datafield tag='260' ind1=' ' ind2=' '>" +
                "<subfield code='a'>One </subfield>" +
                "<subfield code='a'> Two</subfield>" +
                "<subfield code='a'>Three : </subfield>" +
                "<subfield code='a'>Three : </subfield>" +
                "<subfield code='a'>Three ; </subfield>" +
                "<subfield code='b'>B1</subfield>" +
                "<subfield code='b'>B2</subfield>" +
                "<subfield code='c'>C</subfield>" +
            "</datafield>";    
    
    private static final String MULTIPLE_SUBFIELDS = 
            "<datafield tag='260' ind1=' ' ind2=' '>" +
                "<subfield code='a'>A1</subfield>" +
                "<subfield code='a'>A2</subfield>" +
                "<subfield code='a'>A3</subfield>" +
                "<subfield code='b'>B1</subfield>" +
                "<subfield code='b'>B2</subfield>" +
                "<subfield code='c'>C1</subfield>" +
            "</datafield>";        
    
    private MarcxmlDataField datafield;
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------

    @Test
    public void noTag_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "not an integer");
        buildFromString(NO_TAG);
    }

    @Test
    public void emptyTag_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "not an integer");
        buildFromString(EMPTY_TAG);
    }
    
    @Test
    public void blankTag_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "not an integer");
        buildFromString(BLANK_TAG);
    }
    
    @Test
    public void invalidTag_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, 
                "value is not between 1 and 999");
        buildFromString(INVALID_TAG);
    }
    
    @Test
    public void noValue_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "no subfields");
        buildFromString(NO_VALUE);
    }

    @Test
    public void nonSubfieldChild_Succeeds() throws Exception {
        // No exception
        buildFromString(NON_SUBFIELD_CHILD);
    }

    @Test
    public void invalidSubfield_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "code cannot be empty");
        buildFromString(INVALID_SUBFIELD);
    }
    
    @Test
    public void invalidField245_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "required for field 245");
        buildFromString(INVALID_FIELD_245);
    }
    
    @Test
    public void validDataField_Valid() throws Exception {
        // No exception
        buildFromString(VALID_DATAFIELD);
    }
    
    @Test
    public void testGetSubfieldValues() throws Exception {
        datafield = buildFromString(REPEATED_SUBFIELDS);
        List<String> values = datafield.getSubfieldValues('a', false);
        Assert.assertEquals(5, values.size());
    }
    
    @Test
    public void testGetTrimmedSubfieldValues() throws Exception {
        datafield = buildFromString(REPEATED_SUBFIELDS);
        List<String> values = datafield.getTrimmedSubfieldValues('a');
        Assert.assertEquals("Three", values.get(3));   
    }
    
    @Test 
    public void testGetUniqueSubfieldValues() throws Exception {
        datafield = buildFromString(REPEATED_SUBFIELDS);
        Set<String> values = datafield.getUniqueSubfieldValues('a', false);
        Assert.assertEquals(4, values.size());           
    }
    
    @Test 
    public void testGetUniqueTrimmedSubfieldValues() throws Exception {
        datafield = buildFromString(REPEATED_SUBFIELDS);
        Set<String> values = datafield.getUniqueTrimmedSubfieldValues('a');
        Assert.assertEquals(3, values.size());         
    }
    
    @Test 
    public void testSubfieldMap() throws Exception {
        datafield = buildFromString(MULTIPLE_SUBFIELDS);
        MapOfLists<Character, String> map = new MapOfLists<>();
        map.addValue('a', "A1");
        map.addValue('a', "A2");
        map.addValue('a', "A3");
        map.addValue('b', "B1");
        map.addValue('b', "B2");
        map.addValue('c', "C1");
        Assert.assertEquals(map, datafield.getSubfieldMap());
    }
    
    @Test
    public void testSubfieldSubmap() throws Exception {
        datafield = buildFromString(MULTIPLE_SUBFIELDS);
        MapOfLists<Character, String> map = new MapOfLists<>();
        map.addValue('a', "A1");
        map.addValue('a', "A2");
        map.addValue('a', "A3");
        map.addValue('c', "C1");
        Character[] codes = {'a', 'c'};
        Assert.assertEquals(map, datafield.getSubfieldSubmap(codes));        
    }
    
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private MarcxmlDataField buildFromString(String s)
            throws RecordException {
        Element element = XmlTestUtils.buildElementFromString(s);
        return new MarcxmlDataField(element);
    }
}
