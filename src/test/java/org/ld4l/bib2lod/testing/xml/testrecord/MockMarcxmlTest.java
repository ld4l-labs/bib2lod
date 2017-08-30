/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.testing.xml.testrecord;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;

/**
 * TODO
 */
public class MockMarcxmlTest extends AbstractTestClass {

    public static final String BASE_RECORD = "" //
            + "<record>" //
            + "  <leader>leader_value</leader>"
            + "  <controlfield tag='cf_tag'>control_field_value</controlfield>"
            + "  <datafield tag='df_tag' ind1='df_ind1' ind2='df_ind2'>"
            + "    <subfield code='sf_code'>subfield_value</subfield>"
            + "  </datafield>" //
            + "</record>";

    public static final String BASE_TO_STRING = ("MockMarcxml[fields=["
            + "Leader['leader_value'], "
            + "Controlfield[tag='cf_tag', 'control_field_value'], "
            + "Datafield[tag='df_tag', ind1='df_ind1', ind2='df_ind2', subfields=["
            + "Subfield[code='sf_code', 'subfield_value']]]]]") //
                    .replace("'", "\"");

    public static final String BIGGER_RECORD = "" //
            + "<record>" //
            + "  <leader>leader_value</leader>"
            + "  <controlfield tag='cf_tag_1'>control_field_value_1</controlfield>"
            + "  <controlfield tag='cf_tag_2'>control_field_value_2</controlfield>"
            + "  <datafield tag='df_tag_1' ind1='df_ind1_1' ind2='df_ind2_1'>"
            + "    <subfield code='sf_code_1'>subfield_value_1</subfield>"
            + "    <subfield code='sf_code_2'>subfield_value_2</subfield>"
            + "  </datafield>" //
            + "  <datafield tag='df_tag_2' ind1='df_ind1_2' ind2='df_ind2_2' />"
            + "</record>";

    private MockMarcxml base;
    private MockMarcxml bigger;
    private MockMarcxml modified1;
    private MockMarcxml modified2;
    private MockMarcxml modified3;

    @Before
    public void setup() {
        base = MockMarcxml.parse(BASE_RECORD);
        bigger = MockMarcxml.parse(BIGGER_RECORD);
    }

    // ----------------------------------------------------------------------
    // Tests of transformation methods
    // ----------------------------------------------------------------------

    @Test
    public void baseToString() {
        assertEquals(BASE_TO_STRING, base.toString());
    }

    @Test
    public void baseToElementToXmlToMockToString() {
        assertEquals(BASE_TO_STRING,
                MockMarcxml.parse(xmlToString(base.toElement())).toString());
    }

    // ----------------------------------------------------------------------
    // Tests of navigation methods
    // ----------------------------------------------------------------------

    @Test
    public void findLeader_yieldsSameResultFromAnywhere() {
        Leader.Builder leader = base.openCopy().findLeader();
        assertEquals(leader,
                base.openCopy().findControlfield("cf_tag").findLeader());
        assertEquals(leader, base.openCopy().findDatafield("df_tag")
                .findSubfield("sf_code").findLeader());
    }

    @Test
    public void findControlfield_yieldsSameResultFromAnywhere() {
        Controlfield.Builder controlfield = bigger.openCopy()
                .findControlfield("cf_tag_2");
        assertEquals(controlfield, bigger.openCopy().findControlfield("cf_tag_1")
                .findControlfield("cf_tag_2"));
        assertEquals(controlfield, bigger.openCopy().findDatafield("df_tag_1")
                .findSubfield("sf_code_2").findControlfield("cf_tag_2"));
    }

    @Test
    public void findDatafield_yieldsSameResultFromAnywhere() {
        Datafield.Builder datafield = bigger.openCopy()
                .findDatafield("df_tag_2");
        assertEquals(datafield,
                bigger.openCopy().findLeader().findDatafield("df_tag_2"));
        assertEquals(datafield, bigger.openCopy().findDatafield("df_tag_1")
                .findSubfield("sf_code_2").findDatafield("df_tag_2"));
    }

    @Test
    public void findSubfield_yieldsSameResultFromParentDatafieldOrSiblingSubfield() {
        Subfield.Builder subfield = bigger.openCopy().findDatafield("df_tag_1")
                .findSubfield("sf_code_2");
        assertEquals(subfield, bigger.openCopy().findDatafield("df_tag_1")
                .findSubfield("sf_code_1").findSubfield("sf_code_2"));
    }

    // ----------------------------------------------------------------------
    // Tests of structure modifying methods
    // ----------------------------------------------------------------------

    @Test
    public void addLeader_fromAllLevels() {
        modified1 = base.openCopy().addLeader("new_leader_value").lock();
        modified2 = base.openCopy().findControlfield("cf_tag")
                .addLeader("new_leader_value").lock();
        modified3 = base.openCopy().findDatafield("df_tag")
                .findSubfield("sf_code").addLeader("new_leader_value").lock();
        assertExpectedMarcxml(2, 1, 1, 1, modified1, modified2, modified3);
    }

    @Test
    public void replaceLeader_fromAllLevels() {
        modified1 = base.openCopy().replaceLeader("new_leader_value").lock();
        modified2 = base.openCopy().findControlfield("cf_tag")
                .replaceLeader("new_leader_value").lock();
        modified3 = base.openCopy().findDatafield("df_tag")
                .findSubfield("sf_code").replaceLeader("new_leader_value")
                .lock();
        assertExpectedMarcxml(1, 1, 1, 1, modified1, modified2, modified3);
    }

    @Test
    public void deleteLeader_fromAllLevels() {
        modified1 = base.openCopy().deleteLeader().lock();
        modified2 = base.openCopy().findControlfield("cf_tag").deleteLeader()
                .lock();
        modified3 = base.openCopy().findDatafield("df_tag")
                .findSubfield("sf_code").deleteLeader().lock();
        assertExpectedMarcxml(0, 1, 1, 1, modified1, modified2, modified3);
    }

    @Test
    public void addControlfield_fromAllLevels() {
        modified1 = base.openCopy()
                .addControlfield("cf_new", "new_controlfield_value").lock();
        modified2 = base.openCopy().findControlfield("cf_tag")
                .addControlfield("cf_new", "new_controlfield_value").lock();
        modified3 = base.openCopy().findDatafield("df_tag")
                .findSubfield("sf_code")
                .addControlfield("cf_new", "new_controlfield_value").lock();
        assertExpectedMarcxml(1, 2, 1, 1, modified1, modified2, modified3);
    }

    @Test
    public void replaceControlfield_fromAllLevels() {
        modified1 = base.openCopy()
                .replaceControlfield("cf_tag", "new_controlfield_value")
                .lock();
        modified2 = base.openCopy().findControlfield("cf_tag")
                .replaceControlfield("cf_tag", "new_controlfield_value")
                .lock();
        modified3 = base.openCopy().findDatafield("df_tag")
                .findSubfield("sf_code")
                .replaceControlfield("cf_tag", "new_controlfield_value")
                .lock();
        assertExpectedMarcxml(1, 1, 1, 1, modified1, modified2, modified3);
    }

    @Test
    public void deleteControlfield_fromAllLevels() {
        modified1 = base.openCopy().deleteControlfield("cf_tag").lock();
        modified2 = base.openCopy().findControlfield("cf_tag")
                .deleteControlfield("cf_tag").lock();
        modified3 = base.openCopy().findDatafield("df_tag")
                .findSubfield("sf_code").deleteControlfield("cf_tag").lock();
        assertExpectedMarcxml(1, 0, 1, 1, modified1, modified2, modified3);
    }

    @Test
    public void addDatafield_fromAllLevels() {
        modified1 = base.openCopy()
                .addDatafield("df_new", "ind1_new", "ind2_new").lock();
        modified2 = base.openCopy().findControlfield("cf_tag")
                .addDatafield("df_new", "ind1_new", "ind2_new").lock();
        modified3 = base.openCopy().findDatafield("df_tag")
                .findSubfield("sf_code")
                .addDatafield("df_new", "ind1_new", "ind2_new").lock();
        assertExpectedMarcxml(1, 1, 2, 1, modified1, modified2, modified3);
    }

    @Test
    public void replaceDatafield_fromAllLevels() {
        modified1 = base.openCopy()
                .replaceDatafield("df_tag", "ind1_new", "ind2_new").lock();
        modified2 = base.openCopy().findControlfield("cf_tag")
                .replaceDatafield("df_tag", "ind1_new", "ind2_new").lock();
        modified3 = base.openCopy().findDatafield("df_tag")
                .findSubfield("sf_code")
                .replaceDatafield("df_tag", "ind1_new", "ind2_new").lock();
        assertExpectedMarcxml(1, 1, 1, 0, modified1, modified2, modified3);
    }

    @Test
    public void deleteDatafield_fromAllLevels() {
        modified1 = base.openCopy().deleteDatafield("df_tag").lock();
        modified2 = base.openCopy().findControlfield("cf_tag")
                .deleteDatafield("df_tag").lock();
        modified3 = base.openCopy().findDatafield("df_tag")
                .findSubfield("sf_code").deleteDatafield("df_tag").lock();
        assertExpectedMarcxml(1, 1, 0, 0, modified1, modified2, modified3);
    }

    @Test
    public void addSubfield_fromLowerLevels() {
        modified1 = base.openCopy().findDatafield("df_tag")
                .addSubfield("sf_new", "sf_new_value").lock();
        modified2 = base.openCopy().findDatafield("df_tag")
                .findSubfield("sf_code").addSubfield("sf_new", "sf_new_value")
                .lock();
        assertExpectedMarcxml(1, 1, 1, 2, modified1, modified2);
    }

    @Test
    public void replaceSubfield_fromLowerLevels() {
        modified1 = base.openCopy().findDatafield("df_tag")
                .replaceSubfield("sf_code", "sf_new_value").lock();
        modified2 = base.openCopy().findDatafield("df_tag")
                .findSubfield("sf_code")
                .replaceSubfield("sf_code", "sf_new_value").lock();
        assertExpectedMarcxml(1, 1, 1, 1, modified1, modified2);
    }

    @Test
    public void deleteSubfield_fromLowerLevels() {
        modified1 = base.openCopy().findDatafield("df_tag")
                .deleteSubfield("sf_code").lock();
        modified2 = base.openCopy().findDatafield("df_tag")
                .findSubfield("sf_code").deleteSubfield("sf_code").lock();
        assertExpectedMarcxml(1, 1, 1, 0, modified1, modified2);
    }

    // ----------------------------------------------------------------------
    // Tests of value modifying methods
    // ----------------------------------------------------------------------

    @Test
    public void controlfield_setValue() {
        modified1 = base.openCopy().findControlfield("cf_tag")
                .setValue("cf_new_value").lock();
        assertThat(modified1.toString(),
                containsString("\"cf_new_value\"]"));
        assertExpectedMarcxml(1, 1, 1, 1, modified1);
    }

    @Test
    public void datafield_setInd1() {
        modified1 = base.openCopy().findDatafield("df_tag").setInd1("ind1_new")
                .lock();
        assertThat(modified1.toString(), containsString("ind1=\"ind1_new\""));
        assertExpectedMarcxml(1, 1, 1, 1, modified1);
    }

    @Test
    public void datafield_setInd2() {
        modified1 = base.openCopy().findDatafield("df_tag").setInd2("ind2_new")
                .lock();
        assertThat(modified1.toString(), containsString("ind2=\"ind2_new\""));
        assertExpectedMarcxml(1, 1, 1, 1, modified1);
    }

    @Test
    public void subfield_setValue() {
        modified1 = base.openCopy().findDatafield("df_tag")
                .findSubfield("sf_code").setValue("sf_new_value").lock();
        assertThat(modified1.toString(),
                containsString("\"sf_new_value\"]"));
        assertExpectedMarcxml(1, 1, 1, 1, modified1);
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------

    private String xmlToString(Element element) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
                    "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(element),
                    new StreamResult(writer));
            return writer.getBuffer().toString().replaceAll("\n|\r", "");
        } catch (IllegalArgumentException | TransformerFactoryConfigurationError
                | TransformerException e) {
            throw new RuntimeException("Couldn't convert XML to String.", e);
        }
    }

    private void assertExpectedMarcxml(int expectedLeaderCount,
            int expectedControlfieldCount, int expectedDatafieldCount,
            int expectedSubfieldCount, MockMarcxml... mmArray) {
        class Tuple {
            private final String label;
            private final MockMarcxml mm;

            public Tuple(String label, MockMarcxml mm) {
                super();
                this.label = label;
                this.mm = mm;
            }
        }

        List<Tuple> tuples = new ArrayList<>();
        for (int i = 0; i < mmArray.length; i++) {
            tuples.add(new Tuple("modified" + (i + 1), mmArray[i]));
        }

        for (Tuple tuple : tuples) {
            assertFieldCounts(expectedLeaderCount, expectedControlfieldCount,
                    expectedDatafieldCount, expectedSubfieldCount, tuple.mm,
                    tuple.label);
        }

        for (int i = 0; i < tuples.size(); i++) {
            Tuple tuple1 = tuples.get(i);
            for (int j = i + 1; j < tuples.size(); j++) {
                Tuple tuple2 = tuples.get(j);
                assertEquals(tuple1.label + " " + tuple2.label, tuple1.mm,
                        tuple2.mm);
            }
        }
    }

    private void assertFieldCounts(int expectedLeaderCount,
            int expectedControlfieldCount, int expectedDatafieldCount,
            int expectedSubfieldCount, MockMarcxml mm, String label) {
        int actualLeaderCount = 0;
        int actualControlfieldCount = 0;
        int actualDatafieldCount = 0;
        int actualSubfieldCount = 0;

        for (Field f : mm.fields) {
            if (f instanceof Leader) {
                actualLeaderCount++;
            } else if (f instanceof Controlfield) {
                actualControlfieldCount++;
            } else { // isDatafield
                actualDatafieldCount++;
                actualSubfieldCount += ((Datafield)f).subfields.size();
            }
        }

        assertEquals(label + " leaders", expectedLeaderCount,
                actualLeaderCount);
        assertEquals(label + " controlfields", expectedControlfieldCount,
                actualControlfieldCount);
        assertEquals(label + " datafields", expectedDatafieldCount,
                actualDatafieldCount);
        assertEquals(label + " subfields", expectedSubfieldCount,
                actualSubfieldCount);
    }
}
