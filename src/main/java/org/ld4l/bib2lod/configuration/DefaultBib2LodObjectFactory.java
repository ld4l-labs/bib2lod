/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.List;

/**
 * TODO
 */
public class DefaultBib2LodObjectFactory extends Bib2LodObjectFactory {

    /**
     * @param jsonConfigurator
     */
    public DefaultBib2LodObjectFactory(Configuration configuration) {
        // TODO Auto-generated constructor stub
        throw new RuntimeException("DefaultBib2LodObjectFactory Constructor not implemented.");
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Bib2LodObjectFactory#instanceForClass(java.lang.Class)
     */
    @Override
    public <T extends Configurable> T instanceForClass(Class<T> class1) {
        // TODO Auto-generated method stub
        throw new RuntimeException("Bib2LodObjectFactory.instanceForClass() not implemented.");
        
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Bib2LodObjectFactory#instancesForClass(java.lang.Class)
     */
    @Override
    public <T extends Configurable> List<T> instancesForClass(Class<T> class1) {
        // TODO Auto-generated method stub
        throw new RuntimeException("Bib2LodObjectFactory.instancesForClass() not implemented.");
        
    }

}
