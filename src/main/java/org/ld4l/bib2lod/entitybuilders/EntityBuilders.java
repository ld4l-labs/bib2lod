package org.ld4l.bib2lod.entitybuilders;

import java.util.HashMap;

import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configurable;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Instantiates the EntityBuilders needed for conversion and stores them in 
 * a map of Types to EntityBuilders.
 */
public interface EntityBuilders {

    /**
     * Signals a problem when instantiating an EntityBuilder.
     * TODO - need to exit application when this fails.
     */
    public static class EntityBuildersException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public EntityBuildersException(String message, Throwable cause) {
            super(message, cause);
        }

        public EntityBuildersException(String message) {
            super(message);
        }

        public EntityBuildersException(Throwable cause) {
            super(cause);
        }
    }  

    /**
     * Factory method
     * @throws EntityBuildersException 
     */
    static EntityBuilders instance() throws EntityBuildersException {
        EntityBuilders builders = Bib2LodObjectFactory.getFactory()
                .instanceForInterface(EntityBuilders.class);
        return builders;
    }
    
    /**
     * Returns a map of Types to EntityBuilders used to instantiate Entities of
     * that Type.
     */
    public HashMap<Class<? extends Type>, Class<? extends EntityBuilder>> 
            getTypeToBuilderClassMap();
    
    /**
     * Instantiates EntityBuilders for each Type.
     * @throws EntityBuildersException
     */
    public void instantiateBuilders() throws EntityBuildersException;
    
    /**
     * Returns the EntityBuilder instance for the specified Type.
     */
    public EntityBuilder getBuilder(Class<? extends Type> type); 
    
    /**
     * Returns the map of Types to EntityBuilders.
     */
    public HashMap<Class<? extends Type>, EntityBuilder> getBuilders();
    
}
