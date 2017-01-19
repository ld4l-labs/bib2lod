/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.MockBib2LodObjectFactory;
import org.ld4l.bib2lod.test.AbstractTestClass;

import com.fasterxml.jackson.databind.node.ObjectNode;

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

/**
 * Tests class FileInputBuilder
 */
public class FileInputBuilderTest extends AbstractTestClass {

    private FileInputBuilder fileInputBuilder;
    private List<BufferedReader> readers;
    private Bib2LodObjectFactory factory;
    private Configuration config;
    private ObjectNode optionsNode;


    @Rule 
    public TemporaryFolder folder = new TemporaryFolder() {

        @Override
        protected void before() throws Exception {
            factory = new MockBib2LodObjectFactory();
            setBaseOptionsNode();
            folder.create();
        };

        @Override
        protected void after() {
            folder.delete();
        };
    };
    
    private void setBaseOptionsNode() {
        optionsNode = jsonObject();
        optionsNode.set("input",
                jsonObject() 
                        .put("file_extension", 
                                "xml")
                        .put("format", "MARCXML"));  
    }
      
    @Test // (expected = IOException.class)
    public void inputSourceDoesntExist_ThrowsException() throws Exception {

//        fail("inputSourceDoesntExist_ThrowsException not yet implemented");
//        File file = new File(folder.getRoot().getCanonicalPath(), "test");
//        readers = fileInputBuilder.buildInputList(); 
    }
    
    @Test // (expected = IOException.class)
    public void inputFileNotReadable_ThrowsException() throws Exception {
//        fail("inputFileNotReadable_ThrowsException not yet implemented");
//        File file = folder.newFile();
//        file.setReadable(false);
//        readers = fileInputBuilder.buildInputList(); 
    }
    
    @Test // (expected = IOException.class)
    public void inputDirectoryNotReadable_ThrowsException() throws Exception {
//        fail("inputDirectoryNotReadable_ThrowsException not yet implemented");
//        File subfolder = folder.newFolder();
//        subfolder.setReadable(false);
//        readers = fileInputBuilder.buildInputList(); 
    }
    
    @Test
    public void inputDirectoryEmpty() throws Exception {
//        fail("inputDirectoryEmpty not yet implemented");
//        readers = fileInputBuilder.buildInputList(); 
//        assertTrue(readers.size() == 0);
    }
    
    @Test
    public void inputDirectoryContainsTwoFiles() throws Exception {
//        fail("inputDirectoryContainsTwoFiles not yet implemented");
//        folder.newFile();
//        folder.newFile();
//        readers = fileInputBuilder.buildInputList();
//        assertTrue(readers.size() == 2);
    }
    
    @Test
    public void noReadableFilesInInputDirectory_Succeeds() throws Exception {
//        fail("noReadableFilesInInputDirectory_Succeeds not yet implemented");
    }
    
    @Test
    public void testFileExtensionFilter()  {
//        fail("testFileExtensionFilter not yet implemented");
    }
    
    
    @Test
    public void testReadabilityFilter()  {
//       fail("testReadabilityFilter not yet implemented");
    }
    
    @Test
    public void testSubfoldersExcluded() throws Exception {
//        fail("testSubfoldersExcluded not yet implemented");
//        folder.newFile();
//        folder.newFile();
//        folder.newFolder();
//        readers = fileInputBuilder.buildInputList(
//                folder.getRoot().getCanonicalPath());
//        assertTrue(readers.size() == 2);
    }
    
    @Test
    public void noRecursionIntoSubfolders() throws Exception {
//        fail("noRecursionIntoSubfolders not yet implemented");
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
//        fail("inputSourceIsFile not yet implemented");
//        File file = folder.newFile();
//        readers = fileInputBuilder.buildInputList();
//        assertTrue(readers.size() == 1);
    }
}
