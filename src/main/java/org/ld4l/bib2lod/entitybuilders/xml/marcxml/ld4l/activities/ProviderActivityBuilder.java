package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l.activities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lLocationType;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

public class ProviderActivityBuilder extends ActivityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();
    
    private static final Ld4lActivityType TYPE = 
            Ld4lActivityType.PROVIDER_ACTIVITY;
    
    // TODO Move up to ActivityBuilder if it works for other activities
    protected void buildDate(MarcxmlDataField field, char code) {

        MarcxmlSubfield subfield = field.getSubfield(code);
        if (subfield == null) {
            return;
        }
        String date = subfield.getTrimmedTextValue();
        // Unlike the controlled 008 date, the 260$c date value is an 
        // untyped literal.
        activity.addAttribute(Ld4lDatatypeProp.DATE, date);
    }
   
    // TODO Move up to ActivityBuilder if it works for other activities   
    protected void buildLocation(MarcxmlDataField field, char code) 
            throws EntityBuilderException {  
        
        EntityBuilder builder = getBuilder(Ld4lLocationType.superClass());
        
        MarcxmlSubfield subfield = field.getSubfield(code);       
        if (subfield == null) {
            return;
        }
        
        BuildParams params = new BuildParams() 
              .setGrandparent(parent)
              .setParent(activity)
              .setSubfield(subfield)
              .setType(Ld4lLocationType.superClass());
        builder.build(params);
    }

    // TODO Move up to ActivityBuilder if it works for other activities
    protected void buildAgent(MarcxmlDataField field, char code)
            throws EntityBuilderException {  
        
        EntityBuilder builder = getBuilder(Ld4lAgentType.superClass());
        
        MarcxmlSubfield subfield = field.getSubfield(code);
        
        if (subfield == null) {
            return;
        }

        BuildParams params = new BuildParams() 
              .setGrandparent(parent)
              .setParent(activity)
              .setSubfield(subfield)
              .setType(Ld4lAgentType.superClass());
        builder.build(params);  
    }
    
    
    @Override
    protected Ld4lActivityType getType() {
        return TYPE;
    }
}
