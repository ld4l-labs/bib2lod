/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;
import org.ld4l.bib2lod.configuration.ConfigurationNode;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.InputService.InputServiceException;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class FileInputService
 */
public class FileInputServiceTest extends AbstractTestClass {

    private Configuration config;
    private FileInputService service;
    private List<InputDescriptor> inputs;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder() {
        @Override
        protected void before() throws Exception {
            folder.create();
        }

        @Override
        protected void after() {
            folder.delete();
        }
    };

    @After
    public void cleanUp() throws InputServiceException, IOException {
        if (inputs != null) {
            for (InputDescriptor input : inputs) {
                input.close();
            }
        }
    }

    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------

    @Test(expected = ConfigurationException.class)
    public void inputSourceDoesntExist_ThrowsException() throws Exception {
        File file = new File(folder.getRoot().getCanonicalPath(), "test");
        createServiceAndGetDescriptors(file.getCanonicalPath(), null);
    }

    @Test(expected = ConfigurationException.class)
    public void inputFileNotReadable_ThrowsException() throws Exception {
        File file = folder.newFile();
        file.setReadable(false);
        createServiceAndGetDescriptors(file.getCanonicalPath(), null);
    }

    @Test(expected = ConfigurationException.class)
    public void inputDirectoryNotReadable_ThrowsException() throws Exception {
        File subfolder = folder.newFolder();
        subfolder.setReadable(false);
        createServiceAndGetDescriptors(subfolder.getCanonicalPath(), null);
    }

    @Test
    public void inputDirectoryEmpty() throws Exception {
        createServiceAndGetDescriptors(folder.getRoot().getCanonicalPath(),
                null);
        assertEquals(0, inputs.size());
    }

    @Test
    public void inputDirectoryContainsTwoFiles() throws Exception {
        folder.newFile();
        folder.newFile();
        createServiceAndGetDescriptors(folder.getRoot().getCanonicalPath(),
                null);
        assertEquals(2, inputs.size());
    }

    @Test
    public void inputSourceIsFile() throws Exception {
        File file = folder.newFile();
        createServiceAndGetDescriptors(file.getCanonicalPath(), null);
        assertEquals(1, inputs.size());
    }

    @Test
    public void testReadabilityFilter() throws IOException {
        folder.newFile().setReadable(false);
        folder.newFile();
        createServiceAndGetDescriptors(folder.getRoot().getCanonicalPath(),
                null);
        assertEquals(1, inputs.size());
    }

    @Test
    public void noReadableFilesInInputDirectory_Succeeds() throws IOException {
        folder.newFile().setReadable(false);
        folder.newFile().setReadable(false);
        createServiceAndGetDescriptors(folder.getRoot().getCanonicalPath(),
                null);
        assertEquals(0, inputs.size());
    }

    @Test
    public void testFileExtensionFilter() throws IOException {
        folder.newFile("file1.good");
        folder.newFile("file2.bad");
        createServiceAndGetDescriptors(folder.getRoot().getCanonicalPath(),
                "good");
        assertEquals(1, inputs.size());
    }

    @Test
    public void testSubfoldersExcluded() throws Exception {
        folder.newFile();
        folder.newFile();
        folder.newFolder();
        createServiceAndGetDescriptors(folder.getRoot().getCanonicalPath(),
                null);
        assertEquals(2, inputs.size());
    }

    @Test
    public void noRecursionIntoSubfolders() throws Exception {
        folder.newFile();
        folder.newFile();
        File subFolder = folder.newFolder();
        new File(subFolder, "child").createNewFile();
        createServiceAndGetDescriptors(folder.getRoot().getCanonicalPath(),
                null);
        assertEquals(2, inputs.size());
    }

    @Test
    public void testMetadataHasName() throws IOException {
        folder.newFile("foo.bar");
        createServiceAndGetDescriptors(folder.getRoot().getCanonicalPath(),
                null);
        assertEquals(1, inputs.size());
        assertEquals("foo.bar", inputs.get(0).getMetadata().getName());
    }

    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------

    private void createServiceAndGetDescriptors(String path, String extension) {
        config = new ConfigurationNode.Builder().addAttribute("source", path)
                .addAttribute("extension", extension).build();
        service = new FileInputService();
        service.configure(config);

        inputs = new ArrayList<>();
        for (InputDescriptor in : service.getDescriptors()) {
            inputs.add(in);
        }
    }

}
