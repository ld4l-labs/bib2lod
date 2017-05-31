package org.ld4l.bib2lod.entitybuilders;

import java.util.HashMap;
import java.util.Map.Entry;

import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.Type;

public abstract class BaseEntityBuilderFactory implements EntityBuilderFactory {

    // Maps an ontology Type to an EntityBuilder class used to build Entities
    // of that type.
    private HashMap<Class<? extends Type>, EntityBuilder> typeToBuilderInstance;
    
    /**
     * Constructor
     */
    public BaseEntityBuilderFactory() {
        typeToBuilderInstance = new HashMap<>();
        instantiateBuilders();
    }
              
    @Override 
    public void instantiateBuilders() throws EntityBuilderFactoryException {
        
        for (Entry<Class<? extends Type>, Class<? extends EntityBuilder>> entry : 
                    getTypeToBuilderClassMap().entrySet()) {
            EntityBuilder builder;
            try {
                builder = EntityBuilder.instance(entry.getValue());
            } catch (EntityBuilderException e) {
                throw new EntityBuilderFactoryException(e);
            }
            typeToBuilderInstance.put(entry.getKey(), builder);
        }
    }
    
    @Override
    public HashMap<Class<? extends Type>, EntityBuilder> getBuilders() {
        return typeToBuilderInstance;
    }
    
    @Override
    public EntityBuilder getBuilder(Class<? extends Type> type) {
        return typeToBuilderInstance.get(type);        
    }
   
}
