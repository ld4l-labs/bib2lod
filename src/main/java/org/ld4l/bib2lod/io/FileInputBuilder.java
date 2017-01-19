/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * File-system-based input builder.
 */
public class FileInputBuilder extends BaseInputBuilder {

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    // Not used yet
    private class InputFileFilter implements FileFilter {


        /* (non-Javadoc)
         * @see java.io.FileFilter#accept(java.io.File)
         */
        @Override
        public boolean accept(File file) {
            
// TODO Implement these tests
//            // File must be readable
//            if (! file.canRead()) {
//                return false;
//            }
//            
//            // File extension must match a specified extension
//            String extension = configuration.getInputFileExtension();
//            if (extension != null) {
//                String thisExtension = 
//                        FilenameUtils.getExtension(file.getName());
//                if (! extension.equals(thisExtension)) {
//                    return false;
//                }
//            }
            
            return true;
        }
        
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.InputBuilder#buildInputList(org.ld4l.bib2lod.configuration.Configuration)
     */
    @Override
    public List<BufferedReader> buildInputList(
            String inputSource, String extension) throws IOException {
        
        File source = new File(inputSource);
        
        if (! source.exists()) {
            throw new IOException(
                    "Input source " + inputSource + " doesn't exist.");
        }
  
        if (! source.canRead()) {
            throw new IOException("Can't read input source " + inputSource);                   
        }     

        // Create a list of input files. Subfolders are ignored.
        List<File> inputFiles = new ArrayList<File>();

        if (source.isDirectory()) {     
            // TODO Use filters to make sure files are readable, and if an 
            // extension is defined, only with that extension. See issue #16.
            // Also filter out subdirectories, since there should be no
            // recursion.
            inputFiles = Arrays.asList(source.listFiles(new InputFileFilter()));
        } else {
            // Wrap the input file in a List
            inputFiles.add(source);
        }

        // Create a list of readers from the list of input files
        List<BufferedReader> readers = new ArrayList<BufferedReader>();
        for (File file : inputFiles) {
            Path path = null;
            try {
                path = file.toPath();
                readers.add(Files.newBufferedReader(path));
                LOGGER.debug("Adding input file " + path.toString());
            } catch (IOException e) {
                LOGGER.warn(
                        "IOException: can't add input file " + path.toString());
            }
        }
  
        if (readers.isEmpty()) {
            LOGGER.warn("No readable input files found");
        }
        
        return readers;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.InputBuilder#buildInputList(java.lang.String)
     */
    @Override
    public List<BufferedReader> buildInputList(String source) throws IOException {
        return buildInputList(source, null);
    }

}
