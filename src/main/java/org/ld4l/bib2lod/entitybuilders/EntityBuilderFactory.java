package org.ld4l.bib2lod.entitybuilders;

import java.util.Map;

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

    public void instantiateBuilders() throws EntityBuilderFactoryException;
    
    public Map<Type, Class<? extends EntityBuilder>> 
        getTypeToBuilderClassMap();
    
    public Map<Type, EntityBuilder> getBuilders();

    public EntityBuilder getBuilder(Type type);
    
}
