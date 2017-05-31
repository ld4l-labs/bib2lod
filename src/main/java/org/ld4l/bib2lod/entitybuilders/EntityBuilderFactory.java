package org.ld4l.bib2lod.entitybuilders;

import java.util.HashMap;

import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Instantiates the EntityBuilderFactory needed for conversion and stores them in 
 * a map of Types to EntityBuilderFactory.
 */
public interface EntityBuilderFactory {

    /**
     * Signals a problem when instantiating an EntityBuilder.
     * TODO - need to exit application when this fails.
     */
    public static class EntityBuilderFactoryException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public EntityBuilderFactoryException(String message, Throwable cause) {
            super(message, cause);
        }

        public EntityBuilderFactoryException(String message) {
            super(message);
        }

        public EntityBuilderFactoryException(Throwable cause) {
            super(cause);
        }
    }  

    /**
     * Factory method
     * @throws EntityBuilderFactoryException 
     */
    static EntityBuilderFactory instance() throws EntityBuilderFactoryException {
        EntityBuilderFactory builders = Bib2LodObjectFactory.getFactory()
                .instanceForInterface(EntityBuilderFactory.class);
        return builders;
    }
    
    /**
     * Returns a map of Types to EntityBuilderFactory used to instantiate Entities of
     * that Type.
     */
    public HashMap<Class<? extends Type>, Class<? extends EntityBuilder>> 
            getTypeToBuilderClassMap();
    
    /**
     * Instantiates EntityBuilderFactory for each Type.
     * @throws EntityBuilderFactoryException
     */
    public void instantiateBuilders() throws EntityBuilderFactoryException;
    
    /**
     * Returns the EntityBuilder instance for the specified Type.
     */
    public EntityBuilder getBuilder(Class<? extends Type> type); 
    
    /**
     * Returns the map of Types to EntityBuilderFactory.
     */
    public HashMap<Class<? extends Type>, EntityBuilder> getBuilders();
    
}
