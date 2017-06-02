package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class MarcxmlToLd4lWorkBuilder.
 */
/*
 * Test plan:
 * record is null - throw exception
 * instance is null - throw exception
 * instance has no title - don't add title
 * leader doesn't exist (or is this excluded in the validation?)
 * leader has no text value (or is this excluded in the validation?)
 * leader text exists but no char at position 6
 * char at position 6 but not one of those in the map
 * language - no 008 control field (or is this excluded in the validation?)
 * language - 008 control field exists but no text value (or is this excluded in the validation?)
 * language - 008 has text value but no substring at positions 35-38
 * 
 */
public class MarcxmlToLd4lWorkBuilderTest extends AbstractTestClass {

}
