/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.record.Record;

/**
 * Mock infrastructure for Converter tests.
 */
public class MockEntityBuilder extends BaseEntityBuilder {

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder#build(org.ld4l.bib2lod.record.Record)
     */
    @Override
    public Entity build(Record record) throws EntityBuilderException {
        return null;
        
    }

}
