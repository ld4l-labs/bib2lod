/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.List;

/**
 * TODO
 */
public abstract class Bib2LodObjectFactory {
    /**
     * @param defaultBib2LodObjectFactory
     */
    public static void setFactoryInstance(Bib2LodObjectFactory factory) {
        // TODO Auto-generated method stub
        throw new RuntimeException(
                "Bib2LodObjectFactory.setInstance() not implemented.");
    }
    
    public static Bib2LodObjectFactory getFactory() {
        // TODO Auto-generated method stub
        throw new RuntimeException("Bib2LodObjectFactory.getFactory() not implemented.");
    }
    
    /**
     * @param class1
     * @return
     */
    public abstract <T extends Configurable> T instanceForClass(
            Class<T> class1);

    /**
     * @param class1
     * @return
     */
    public abstract <T extends Configurable> List<T> instancesForClass(
            Class<T> class1);

}
