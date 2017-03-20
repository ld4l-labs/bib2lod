/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import org.ld4l.bib2lod.configuration.Configuration;

/**
 * Testing infrastructure
 */
public class MockConverter extends BaseConverter {

    /**
     * Constructor
     */
    public MockConverter(Configuration configuration) {
        super(configuration);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.conversion.BaseConverter#getParserClass()
     */
    @Override
    protected Class<?> getParserClass() {
        return MockXmlParser.class;
    }

}
