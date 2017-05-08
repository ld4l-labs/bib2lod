package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import java.util.HashMap;

import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;

public class MarcxmlToLd4lEntityBuilderFactory extends BaseEntityBuilderFactory {

    private static HashMap<Class<? extends Type>, Class<? extends EntityBuilder>> typeToBuilder = 
            new HashMap<>();
    static {
        typeToBuilder.put(Ld4lActivityType.class, MarcxmlToLd4lActivityBuilder.class);
        typeToBuilder.put(Ld4lIdentifierType.class, MarcxmlToLd4lIdentifierBuilder.class);
        typeToBuilder.put(Ld4lInstanceType.class, MarcxmlToLd4lInstanceBuilder.class);
        typeToBuilder.put(Ld4lItemType.class, MarcxmlToLd4lItemBuilder.class);
        typeToBuilder.put(Ld4lWorkType.class, MarcxmlToLd4lWorkBuilder.class);
        typeToBuilder.put(Ld4lTitleType.class, MarcxmlToLd4lTitleBuilder.class);
    }
    
    @Override
    public HashMap<Class<? extends Type>, Class<? extends EntityBuilder>> 
            getTypeToBuilderClassMap() {
        return typeToBuilder;
    }
      
}
