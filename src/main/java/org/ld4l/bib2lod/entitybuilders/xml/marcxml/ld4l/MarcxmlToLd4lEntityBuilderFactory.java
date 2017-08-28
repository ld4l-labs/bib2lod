package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import java.util.HashMap;
import java.util.Map;

import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l.activities.ActivityBuilder;
import org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l.activities.ManufacturerActivityBuilder;
import org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l.activities.PublisherActivityBuilder;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAdminMetadataType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lLocationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleElementType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;

public class MarcxmlToLd4lEntityBuilderFactory extends BaseEntityBuilderFactory {
    
    private static Map<Type, Class<? extends EntityBuilder>> typeToBuilderClass = 
            new HashMap<>();
    
    static {
        typeToBuilderClass.put(Ld4lActivityType.ACTIVITY, ActivityBuilder.class);
        typeToBuilderClass.put(Ld4lAdminMetadataType.ADMIN_METADATA, AdminMetadataBuilder.class);
        typeToBuilderClass.put(Ld4lAgentType.AGENT, AgentBuilder.class);
        typeToBuilderClass.put(Ld4lIdentifierType.IDENTIFIER, IdentifierBuilder.class);
        typeToBuilderClass.put(Ld4lInstanceType.INSTANCE, InstanceBuilder.class);
        typeToBuilderClass.put(Ld4lItemType.ITEM, ItemBuilder.class);
        typeToBuilderClass.put(Ld4lLocationType.LOCATION, LocationBuilder.class);
        typeToBuilderClass.put(Ld4lActivityType.MANUFACTURER_ACTIVITY, ManufacturerActivityBuilder.class);
        typeToBuilderClass.put(Ld4lActivityType.PUBLISHER_ACTIVITY, PublisherActivityBuilder.class);
        typeToBuilderClass.put(Ld4lTitleType.TITLE, TitleBuilder.class);
        typeToBuilderClass.put(Ld4lTitleElementType.TITLE_ELEMENT, TitleElementBuilder.class);
        typeToBuilderClass.put(Ld4lWorkType.WORK, WorkBuilder.class);
    }
    
    @Override
    public Map<Type, Class<? extends EntityBuilder>> getTypeToBuilderClassMap() {
        return typeToBuilderClass;
    }

}
