package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class MarcxmlToLd4lTitleBuilder.
 */
/*
 * Test plan:
 * record null - throws exception
 * bibEntity null - throws exception
 * no 245 or 130 or 245 - succeeds but no label
 * 245 only
 * 245 plus one of the other fields
 * Title elements - refactor into a TitleElementBuilder and test there
 * 
 */
public class MarcxmlToLd4lTitleBuilderTest extends AbstractTestClass {

}
