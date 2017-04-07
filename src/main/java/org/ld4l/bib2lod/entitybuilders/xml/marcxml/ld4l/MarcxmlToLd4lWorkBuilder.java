package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.ld4l.bib2lod.entitybuilders.Entity;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

public class MarcxmlToLd4lWorkBuilder extends MarcxmlToLd4lEntityBuilder {
    
    private final MarcxmlRecord record;
    private final Entity instance;
    
    /**
     * Constructor
     */
    public MarcxmlToLd4lWorkBuilder(Record record, Entity instance) {
        this.record = (MarcxmlRecord) record;
        this.instance = instance;
    }

    @Override
    public Entity build() throws EntityBuilderException {
        Entity work = new Entity(Ld4lWorkType.superClass());
        
        Entity instanceTitle = 
                instance.getChild(Ld4lObjectProp.HAS_PREFERRED_TITLE);
        
        Entity workTitle = new Entity(instanceTitle);
        work.addChild(Ld4lObjectProp.HAS_PREFERRED_TITLE, workTitle);
        
        instance.addChild(Ld4lObjectProp.INSTANCE_OF, work);

        return work;
    }

}
