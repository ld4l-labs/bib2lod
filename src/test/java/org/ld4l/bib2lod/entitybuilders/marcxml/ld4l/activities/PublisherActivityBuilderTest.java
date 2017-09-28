package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.BibDatatype;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.EdtfType;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlTaggedField;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;


/** 
 * Tests class PublisherActivityBuilder.
 */
public class PublisherActivityBuilderTest extends AbstractTestClass {

    private static final MockMarcxml _008_NO_LOCATION = MINIMAL_RECORD.openCopy()
            .replaceControlfield("008", "860506s1957       a     b    000 0 eng  ")
            .lock();

    private static final MockMarcxml _008_NO_DATE = MINIMAL_RECORD.openCopy()
            .replaceControlfield("008", "860506s        nyua     b    000 0 eng  ")
            .lock();
    
    private static final MockMarcxml _008_TWO_DATES = MINIMAL_RECORD.openCopy()
            .replaceControlfield("008", "860506s19571960   a     b    000 0 eng  ")
            .lock();

    public static final MockMarcxml _008_TWO_CHAR_PUB_LOCATION = MINIMAL_RECORD.openCopy()
            .findControlfield("008").setValue("750226c18529999ne bx p       0   b0eng  ")
            .findDatafield("245").setInd1("3")
            .addDatafield("260", " ", " ").addSubfield("c", "1957.")
            .lock();

    public static final MockMarcxml _260_PUBLISHER = MINIMAL_RECORD.openCopy()
            .findDatafield("245").findSubfield("a").setValue("full title")
            .addDatafield("260", " ", " ").addSubfield("a", "New York")
            .addSubfield("b", "Grune & Stratton").addSubfield("c", "1957.")
            .lock();

    public static final MockMarcxml _260_PUBLISHER_NO_DATE = _260_PUBLISHER.openCopy()
            .findDatafield("260").deleteSubfield("c").lock();

    public static final MockMarcxml _260_PUBLISHER_NO_LOCATION = _260_PUBLISHER.openCopy()
            .findDatafield("260").deleteSubfield("a").lock();

    public static final MockMarcxml _260_CURRENT_PUBLISHER = _260_PUBLISHER.openCopy()
            .findDatafield("260").setInd1("3").lock();
    
    public static final MockMarcxml TWO_260 = MINIMAL_RECORD.openCopy()
            .addControlfield("001", "102063")
            .addDatafield("260", "3", " ")
            .addSubfield("a", "Lugduni Batavorum :")
            .addSubfield("b", "E.J. Brill")
            .addDatafield("260", "3", " ")
            .addSubfield("a", "Leiden :")
            .addSubfield("b", "E.J. Brill")
            .lock();

    public static final String _001 = 
            "<controlfield tag='001'>102063</controlfield>";  
    
    public static final String _245 = 
            "<datafield tag='245'><subfield code='a'>text</subfield></datafield>";


    private PublisherActivityBuilder builder;
    
    @Before
    public void setUp() {       
        this.builder = new PublisherActivityBuilder();         
    }
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    public void testPublisherStatus_008() throws Exception {
        Entity activity = buildActivity("008");
        Assert.assertEquals(Ld4lNamedIndividual.CURRENT.uri(), 
                activity.getExternal(Ld4lObjectProp.HAS_STATUS));        
    }
    
    @Test
    public void testPublisherDate_008() throws Exception {
        Entity activity = buildActivity("008");
        Attribute date = activity.getAttribute(Ld4lDatatypeProp.DATE);
        Literal literal = ResourceFactory.createTypedLiteral("1957",
                EdtfType.getRdfDatatype());
        Assert.assertEquals(literal, date.toLiteral());  
    }
    
    @Test
    public void testTwoCharPubLocation_008() throws Exception {
        Entity activity = buildActivity(_008_TWO_CHAR_PUB_LOCATION, "008"); 
        Assert.assertEquals("http://id.loc.gov/vocabulary/countries/ne", 
                activity.getExternal(Ld4lObjectProp.HAS_LOCATION));
    }
    
    @Test
    public void testPublisherLocation_008() throws Exception {
        Entity activity = buildActivity("008");
        Assert.assertEquals("http://id.loc.gov/vocabulary/countries/nyu", 
                activity.getExternal(Ld4lObjectProp.HAS_LOCATION));
    }

    @Test
    public void testLocation_008() throws Exception {       
        Entity activity = buildActivity("008"); 
        String locationUri = 
                activity.getExternal(Ld4lObjectProp.HAS_LOCATION);
        Assert.assertEquals(
                Ld4lNamespace.LC_COUNTRIES.uri() + "nyu", locationUri);
     }

    @Test
    public void noLocation_008_Succeeds() throws Exception {
        buildActivity(_008_NO_LOCATION, "008"); 
    }
    
    @Test
    public void testActivityDate1_008() throws Exception {
        Entity activity = buildActivity("008"); 
        Attribute attribute = activity.getAttribute(Ld4lDatatypeProp.DATE);
        Literal literal = ResourceFactory.createTypedLiteral(
                "1957", BibDatatype.EDTF.rdfType());
        Assert.assertEquals(literal, attribute.toLiteral());  
    }
    
    @Test
    public void testActivityTwoDates_008() throws Exception {
        Entity activity = buildActivity(_008_TWO_DATES, "008"); 
        Assert.assertEquals(2,  
                activity.getAttributes(Ld4lDatatypeProp.DATE).size());
    }
    
    @Test
    public void testActivityDate2_008() throws Exception {
        Entity activity = buildActivity(_008_TWO_DATES, "008"); 
        Attribute attribute = activity.getAttributes(Ld4lDatatypeProp.DATE).get(1);
        Literal literal = ResourceFactory.createTypedLiteral(
                "1960", BibDatatype.EDTF.rdfType());
        Assert.assertEquals(literal, attribute.toLiteral());  
    }
    
    @Test
    public void blankDate_008_Succeeds() throws Exception {
        buildActivity(_008_NO_DATE, "008"); 
    }
    
    @Test
    public void testCurrentPublisher_008() throws Exception {
       Entity activity = buildActivity("008");
       Assert.assertEquals(Ld4lNamedIndividual.CURRENT.uri(), 
               activity.getExternal(Ld4lObjectProp.HAS_STATUS));
    }
    
    public void testCurrentPublisher_260_ind1Value3() 
            throws Exception {
        Entity activity = buildActivity(_260_CURRENT_PUBLISHER, "260");
        Assert.assertEquals(Ld4lNamedIndividual.CURRENT.uri(), 
                activity.getExternal(Ld4lObjectProp.HAS_STATUS));
    }
    
    @Test
    public void testCurrentPublisherStatus_ind1ValueEmpty() 
            throws Exception {
        Entity activity = buildActivity(_260_CURRENT_PUBLISHER, "260");     
        Assert.assertEquals(Ld4lNamedIndividual.CURRENT.uri(), 
                activity.getExternal(Ld4lObjectProp.HAS_STATUS));
    }
    

    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------

    private Entity buildActivity(String tag) 
            throws Exception {
        return buildActivity(MINIMAL_RECORD, tag, null);
    }
    
    private Entity buildActivity(MockMarcxml input, String tag) 
            throws Exception {
        return buildActivity(input, tag, null);
    }
    
    private Entity buildActivity(MockMarcxml input, String tag, 
            List<Character> codes) throws Exception {
        MarcxmlRecord record = input.toRecord();
        MarcxmlTaggedField field = record.getTaggedField(tag);  

        BuildParams params = new BuildParams()
                .setParent(new Entity())
                .setRecord(record)
                .setField(field);
        
        if (field instanceof MarcxmlDataField && codes != null) {
            List<RecordField> subfields = new ArrayList<>();
            for (char code : codes) {
                subfields.add(((MarcxmlDataField)field).getSubfield(code));
            }
            params.setSubfields(subfields);
        }
        
        return builder.build(params);
    }

    
}
