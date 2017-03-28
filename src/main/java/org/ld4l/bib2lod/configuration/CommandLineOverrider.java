/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;
import java.util.List;

import org.ld4l.bib2lod.configuration.Arguments.AttributeOverride;
/**
 * TODO
 */
public class CommandLineOverrider implements Configurator {

    public CommandLineOverrider(Configurator inner,
            List<AttributeOverride> overrides) {
        throw new RuntimeException(
                "CommandLineOverrider.CommandLineOverrider not implemented.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.ld4l.bib2lod.configuration.Configurator#getTopLevelConfiguration()
     */
    @Override
    public Configuration getTopLevelConfiguration() {
        // TODO Auto-generated method stub
        throw new RuntimeException(
                "CommandLineConfigurationOverrider.getTopLevelConfiguration() not implemented.");

    }

}
