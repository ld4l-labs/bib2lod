package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.ontology.OntologyProperty;
import org.ld4l.bib2lod.ontology.WorkClass;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

public class MarcxmlWorkBuilder extends MarcxmlEntityBuilder {
    
    private final MarcxmlRecord record;
    private final Entity instance;
    
    /**
     * Constructor
     */
    public MarcxmlWorkBuilder(Record record, Entity instance) {
        this.record = (MarcxmlRecord) record;
        this.instance = instance;
    }

    @Override
    public Entity build() throws EntityBuilderException {
        Entity work = Entity.instance(WorkClass.superClass());
        
        Entity instanceTitle = 
                instance.getChild(OntologyProperty.HAS_PREFERRED_TITLE.link());

//        ** Add a copy constructor for Entity
//        ** see josh bloch http://www.artima.com/intv/bloch13.html
//        ** also check book
//        
//        instance.addChild(OntologyProperty.INSTANCE_OF.link(), work);
        return work;
    }

}
