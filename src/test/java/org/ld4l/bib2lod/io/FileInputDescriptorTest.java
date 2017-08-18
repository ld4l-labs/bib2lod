/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Rudimentary tests. Can we read from the file? Can we see the name? What if
 * there is no file? (should have been checked by FileInputService)
 */
public class FileInputDescriptorTest extends AbstractTestClass {
    private static final String SOME_FILE = "some.file";

    private static final String SOME_DATA = "Some very fine data";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private FileInputDescriptor input;
    private File inputFile;

    @Before
    public void setUp() {
        inputFile = new File(folder.getRoot(), SOME_FILE);
    }

    // ---------------------------------------------------------------------
    // the tests
    // ---------------------------------------------------------------------

    @Test
    public void metadataShouldContainFilename() {
        input = new FileInputDescriptor(inputFile);
        assertEquals(SOME_FILE, input.getMetadata().getName());
    }

    @Test(expected = FileNotFoundException.class)
    public void fileDoesntExist_throwsException() throws IOException {
        input = new FileInputDescriptor(inputFile);
        input.getInputStream();
    }

    @Test
    public void canReadFromTheFile() throws IOException {
        FileUtils.writeStringToFile(inputFile, SOME_DATA);
        input = new FileInputDescriptor(inputFile);
        InputStream stream = input.getInputStream();
        assertEquals(SOME_DATA, IOUtils.readLines(stream).get(0));
    }

}
