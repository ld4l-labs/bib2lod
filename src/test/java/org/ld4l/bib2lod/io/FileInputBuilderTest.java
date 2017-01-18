/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.ld4l.bib2lod.test.AbstractTestClass;

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

/**
 * Tests class FileInputBuilder
 */
public class FileInputBuilderTest extends AbstractTestClass {

   
    private FileInputBuilder fileInputBuilder;
    private List<BufferedReader> readers;


    @Rule 
    public TemporaryFolder folder = new TemporaryFolder() {
        
        @Override
        protected void before() throws Exception {
            folder.create();
            fileInputBuilder = (FileInputBuilder) InputBuilder.instance(
                    "org.ld4l.bib2lod.io.FileInputBuilder");
        };

        @Override
        protected void after() {
            folder.delete();
        };
    };
    
      
    @Test (expected = IOException.class)
    public void inputSourceDoesntExist_ThrowsException() throws Exception {
        File file = new File(folder.getRoot().getCanonicalPath(), "test");
        readers = fileInputBuilder.buildInputList(
                file.getCanonicalPath()); 
    }
    
    @Test (expected = IOException.class)
    public void inputFileNotReadable_ThrowsException() throws Exception {
        File file = folder.newFile();
        file.setReadable(false);
        readers = fileInputBuilder.buildInputList(
                file.getCanonicalPath()); 
    }
    
    @Test (expected = IOException.class)
    public void inputDirectoryNotReadable_ThrowsException() throws Exception {
        File subfolder = folder.newFolder();
        subfolder.setReadable(false);
        readers = fileInputBuilder.buildInputList(
                subfolder.getCanonicalPath()); 
    }
    
    @Test
    public void inputDirectoryEmpty() throws Exception {
        readers = fileInputBuilder.buildInputList(
                folder.getRoot().getCanonicalPath()); 
        assertTrue(readers.size() == 0);
    }
    
    @Test
    public void inputDirectoryContainsTwoFiles() throws Exception {
        folder.newFile();
        folder.newFile();
        readers = fileInputBuilder.buildInputList(
                folder.getRoot().getCanonicalPath());
        assertTrue(readers.size() == 2);
    }
    
    @Test
    public void noReadableFilesInInputDirectory_Succeeds() throws Exception {
        fail("noReadableFilesInInputDirectory_Succeeds not yet implemented");
    }
    
    @Test
    public void testFileExtensionFilter()  {
        fail("testFileExtensionFilter not yet implemented");
    }
    
    
    @Test
    public void testReadabilityFilter()  {
        fail("testReadabilityFilter not yet implemented");
    }
    
    @Test
    public void subfoldersNotIncluded() throws Exception {
        fail("subfoldersNotIncluded not yet implemented");
//        folder.newFile();
//        folder.newFile();
//        folder.newFolder();
//        readers = fileInputBuilder.buildInputList(
//                folder.getRoot().getCanonicalPath());
//        assertTrue(readers.size() == 2);
    }
    
    @Test
    public void noRecursionIntoSubfolders() throws Exception {
          fail("noRecursionIntoSubfolders not yet implemented");
//        folder.newFile();
//        folder.newFile();
//        File subFolder = folder.newFolder();
          // add a file to the subfolder
//        readers = fileInputBuilder.buildInputList(
//                folder.getRoot().getCanonicalPath());
//        assertTrue(readers.size() == 2);
    }
    
    @Test
    public void inputSourceIsFile() throws Exception {
        File file = folder.newFile();
        readers = fileInputBuilder.buildInputList(file.getCanonicalPath());
        assertTrue(readers.size() == 1);
    }
}
