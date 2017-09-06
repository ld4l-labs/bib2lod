package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import java.util.HashMap;
import java.util.Map;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.marcxml.MarcxmlEntityBuilder;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlLeader;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;

public class WorkBuilder extends MarcxmlEntityBuilder {
  
    private Entity instance;
    private MarcxmlRecord record;
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
        
        reset();
        parseBuildParams(params);
        
        this.work = new Entity(Ld4lWorkType.superclass());
        
        buildTitle();       
        assignType();        
        addLanguages();
        buildActivities();
        
        instance.addRelationship(Ld4lObjectProp.IS_INSTANCE_OF, work);

        return work;
    }
    
    private void reset() {
        this.instance = null;
        this.record = null;
        this.work = null;
    }
    
    private void parseBuildParams(BuildParams params) 
            throws EntityBuilderException {
        
        this.record = (MarcxmlRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A record is required to build a work.");
        }

        this.instance = params.getParent();
        if (instance == null) {
            throw new EntityBuilderException(
                    "An instance is required to build a work.");
        }        
    }
    
    private void buildTitle() {
        
        Entity instanceTitle = 
                instance.getChild(Ld4lObjectProp.HAS_PREFERRED_TITLE); 
        if (instanceTitle != null) {
            Entity workTitle = new Entity(instanceTitle);
            work.addRelationship(
                    Ld4lObjectProp.HAS_PREFERRED_TITLE, workTitle);      
        }
    }
    
    private void assignType() {

        // Work type from leader
        MarcxmlLeader leader = record.getLeader();
        char code = leader.getCharAt(6);

        Type type = codes.get(code); 
        if (type != null) {
            work.addType(type);
        }    
        
        // TODO - s for Serial - use BF model
    }
    
    private void addLanguages() {
        
        /* TODO Codes not the same between lexvo and lc. Just use lc URIs 
         * for now. Eventually need a concordance. */
        // Language from 008
        MarcxmlControlField field_008 = record.getControlField("008");
        String code = field_008.getTextSubstring(35,38);
        if (code != null && code.length() > 0) {
            // Lexvo iso639-3 codes are not completely identical with LC 
            work.addExternalRelationship(Ld4lObjectProp.HAS_LANGUAGE, 
                    Ld4lNamespace.LC_LANGUAGES.uri() + code);
        }
    }
    
    private void buildActivities() throws EntityBuilderException {
        
        buildAuthorActivity();
    }
    
    private void buildAuthorActivity() throws EntityBuilderException {
        
        // Field 100 non-repeating 
        buildChildFromDataField(
                Ld4lActivityType.AUTHOR_ACTIVITY, work, record, "100");
    }

}
