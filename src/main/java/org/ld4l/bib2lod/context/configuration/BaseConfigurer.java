package org.ld4l.bib2lod.context.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO There may be no common methods here; then remove this class
public abstract class BaseConfigurer implements Configurer {

    private static final Logger LOGGER = 
            LogManager.getLogger(BaseConfigurer.class);
    
    public BaseConfigurer()  {
        // TODO Auto-generated constructor stub
    }

}
