/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.io.InputService;

/**
 * Mocking infrastructure for SimpleManager tests.
 */
public class MockInputService implements InputService {
    
    private static final String INPUT_1 = "input 1";
    private static final String INPUT_2 = "input 2";
    private static final String INPUT_3 = "input 3";

//    private static final String OPEN_ROOT_ELEMENT = "<collection>";
//    private static final String CLOSE_ROOT_ELEMENT = "</collection>";
//    private static final String RECORD_1 = 
//            "<record><child>record 1</child></record>"; 
//    
//    private static final String RECORD_2 = 
//            "<record><child>record 2</child></record>"; 
//    
//    private static final String RECORD_3 = 
//            "<record><child>record 3</child></record>"; 
//     
//    private static final String RECORDS = OPEN_ROOT_ELEMENT + RECORD_1 +
//               RECORD_2 + RECORD_3 + CLOSE_ROOT_ELEMENT;
    
    private final List<InputDescriptor> descriptor;

    public MockInputService(Configuration configuration) throws IOException {
        String[] inputs = {INPUT_1, INPUT_2, INPUT_3};
        this.descriptor = wrapStringsInDescriptors(inputs);
    }

    private List<InputDescriptor> wrapStringsInDescriptors(String[] strings) {
        List<InputDescriptor> list = new ArrayList<InputDescriptor>();
        for (String string : strings) {
            list.add(new MockInputDescriptor(string));
        }
        return Collections.unmodifiableList(list);
    }

    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.io.InputService#getDescriptors()
     */
    @Override
    public Iterable<InputDescriptor> getDescriptors() {
        return descriptor;
    }


}
