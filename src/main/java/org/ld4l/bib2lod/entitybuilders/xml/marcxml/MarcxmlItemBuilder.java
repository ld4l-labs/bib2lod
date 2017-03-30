package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.OntologyClass;
import org.ld4l.bib2lod.ontology.OntologyProperty;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

public class MarcxmlItemBuilder extends MarcxmlEntityBuilder {
    
    public enum ItemClass implements OntologyClass {
        
        ITEM(Namespace.BIBFRAME, "Item");
        
        private String uri;
        private Resource ontClass;
        
        /**
         * Constructor
         */
        ItemClass(Namespace namespace, String localName) {
            this.uri = namespace.uri() + localName;
            this.ontClass = ResourceFactory.createResource(uri);
        }
        
        @Override
        public String uri() {
            return uri;
        }

        @Override
        public Resource ontClass() {
            return ontClass;
        } 

        public static Resource superClass() {
            return ITEM.ontClass;
        }
    }
    
    private Entity instance;
    private MarcxmlRecord record;
    
    /**
     * Constructor
     */
    public MarcxmlItemBuilder(Record record, Entity instance) {
        this.instance = instance;
        this.record = (MarcxmlRecord) record;
    }

    @Override
    public Entity build() throws EntityBuilderException {
        Entity item = Entity.instance(ItemClass.superClass());
        
        // TODO Fill in other data about Item from other fields in the record
        
        instance.addChild(
                OntologyProperty.HAS_ITEM.link(), item);
        return item; 
    }

}
