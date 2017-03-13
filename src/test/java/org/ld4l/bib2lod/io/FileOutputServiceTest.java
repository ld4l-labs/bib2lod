/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.ld4l.bib2lod.configuration.BaseConfiguration;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.io.InputService.InputMetadata;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Check how the service reacts to bad configurations, and good ones.
 */
public class FileOutputServiceTest extends AbstractTestClass {

    private static final String NTRIPLES = "N-TRIPLE";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void destinationNotSupplied_throwsExeption() throws IOException {
        createServiceAndGetDescriptor(null, NTRIPLES, metadata("test"));
    }

    @Test(expected = FileNotFoundException.class)
    public void destinationDoesntExist_throwsException() throws IOException {
        File file = new File(folder.getRoot().getCanonicalPath(),
                "doesnt_exist");
        createServiceAndGetDescriptor(file, NTRIPLES, metadata("test"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void destinationNotADirectory_throwsException() throws IOException {
        File file = folder.newFile("not_a_directory");
        createServiceAndGetDescriptor(file, NTRIPLES, metadata("test"));
    }

    @Test(expected = IOException.class)
    public void destinationNotWriteable_throwsException() throws IOException {
        File dest = folder.newFolder("cant_write");
        dest.setWritable(false);
        createServiceAndGetDescriptor(dest, NTRIPLES, metadata("test"));
    }

    @Test(expected = NullPointerException.class)
    public void formatNotSupplied_throwsExeption() throws IOException {
        File dest = folder.newFolder("ok");
        createServiceAndGetDescriptor(dest, null, metadata("test"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatNotValid_throwsException() throws IOException {
        File dest = folder.newFolder("ok");
        createServiceAndGetDescriptor(dest, "bogus_format", metadata("test"));
    }


    @Test(expected = NullPointerException.class)
    public void openSinkWithNoMetadata_throwsException() throws IOException {
        File dest = folder.newFolder("ok");
        createServiceAndGetDescriptor(dest, NTRIPLES, null);
    }

    @Test(expected = NullPointerException.class)
    public void openSinkWithNoName_throwsException() throws IOException {
        File dest = folder.newFolder("ok");
        createServiceAndGetDescriptor(dest, NTRIPLES, metadata(null));
    }

    @Test
    public void simpleSuccess() throws IOException {
        File dest = folder.newFolder("we_win");
        createServiceAndGetDescriptor(dest, NTRIPLES, metadata("test"));
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------

    private InputMetadata metadata(String name) {
        return new InputMetadata() {
            @Override
            public String getName() {
                return name;
            }
        };
    }

    private void createServiceAndGetDescriptor(File destination, String format,
            InputMetadata metadata) throws IOException {
        Configuration config = new BaseConfiguration() {
            {
                outputDestination = destination == null ? null
                        : destination.getCanonicalPath();
                outputFormat = format;
            }
        };
        FileOutputService service = new FileOutputService(config);
        service.openSink(metadata);
    }
}
