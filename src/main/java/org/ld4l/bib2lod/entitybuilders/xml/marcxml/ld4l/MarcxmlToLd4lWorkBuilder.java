package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.Entity;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlLeader;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

public class MarcxmlToLd4lWorkBuilder extends MarcxmlToLd4lEntityBuilder {
    
    private MarcxmlRecord record;
    private Entity instance;
    private Entity work;
    
    private static Map<Character, Type> codes = 
            new HashMap<Character, Type>();
    static {
        codes.put('a', Ld4lWorkType.TEXT);
        codes.put('c', Ld4lWorkType.NOTATED_MUSIC);
        codes.put('d', Ld4lWorkType.NOTATED_MUSIC);
        codes.put('e', Ld4lWorkType.CARTOGRAPHY);
        codes.put('f', Ld4lWorkType.CARTOGRAPHY);
        codes.put('g', Ld4lWorkType.MOVING_IMAGE);
        codes.put('i', Ld4lWorkType.AUDIO);
        codes.put('j', Ld4lWorkType.AUDIO);
        codes.put('k', Ld4lWorkType.STILL_IMAGE);
        codes.put('m', Ld4lWorkType.SOFTWARE);
        codes.put('o', Ld4lWorkType.MIXED_MATERIAL);
        codes.put('p', Ld4lWorkType.MIXED_MATERIAL);
        codes.put('r', Ld4lWorkType.OBJECT);
        codes.put('t', Ld4lWorkType.TEXT);
    }
    

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
        this.record = (MarcxmlRecord) params.getRecord();
        this.instance = params.getRelatedEntity();  
        this.work = new Entity(Ld4lWorkType.superClass());
        
        addTitle();
        
        addWorkTypeFromLeader();
        
        addLanguageFrom008();
        
        instance.addChild(Ld4lObjectProp.IS_INSTANCE_OF, work);

        return work;
    }
    
    private void addTitle() {
        
        Entity instanceTitle = 
                instance.getChild(Ld4lObjectProp.HAS_PREFERRED_TITLE);        
        Entity workTitle = new Entity(instanceTitle);
        work.addChild(Ld4lObjectProp.HAS_PREFERRED_TITLE, workTitle);    
        
    }
    
    private void addWorkTypeFromLeader() {

        MarcxmlLeader leader = record.getLeader();
        char code = leader.getTextValue().charAt(6);
        Type type = codes.get(code); 
        if (type != null) {
            work.addType(type);
        }           
    }
    
    private void addLanguageFrom008() {
        
        MarcxmlControlField field008 = record.getControlField("008");
        String code = field008.getTextValue().substring(35,38);
        if (code != null && code.length() > 0) {
            // Assumes LC codes are the same as Lexvo, which seems to be true.
            // TODO Read lexvo dataset into a model and get language from it.
            work.addExternal(Ld4lObjectProp.HAS_LANGUAGE, 
                    Ld4lNamespace.LEXVO.uri() + code);
        }
    }

}
