package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.BibDatatype;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

/**
 * Builds an Identifier for a bib resource from a field in the record.
 */
public class MarcxmlToLd4lIdentifierBuilder extends BaseEntityBuilder { 

    private static final Logger LOGGER = LogManager.getLogger();
    
    private static final Pattern PATTERN_035_IDENTIFIER = 
            /* 
             * Example values:
             * 102063, (CStRLIN)NYCX86B63464, (NIC)notisAAL3258, (OCoLC)1345399
             *
             * Organization code format: alphas and dashes enclosed in 
             * parentheses. See https://www.loc.gov/marc/organizations/
             * 
             * TODO Not sure which chars are allowed in identifier; using \w for
             * now
             */
            Pattern.compile("^(\\(([a-zA-Z-]+)\\))?(\\w+)$");
    
    private MarcxmlField field;
    private Entity relatedEntity;
    
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        this.relatedEntity = params.getRelatedEntity();
        if (relatedEntity == null) {
            throw new EntityBuilderException(
                    "Cannot build identifier without a related entity.");
        }
        
        this.field = (MarcxmlField) params.getField();
        if (field == null) {
            throw new EntityBuilderException(
                    "Cannot build identifier without an input field.");
        }
        
        Entity identifier;
        if (field instanceof MarcxmlControlField) {
            identifier = build((MarcxmlControlField) field);
        } else if (field instanceof MarcxmlDataField) {
            identifier = build((MarcxmlDataField) field, params);
        } else {
            throw new EntityBuilderException("Invalid field type.");
        }

        relatedEntity.addRelationship(
                Ld4lObjectProp.IDENTIFIED_BY, identifier);
 
        return identifier;
    }
    
    
    /**
     * Builds an Identifier from a control field. Returns null if the control
     * field is not recognized or implemented.
     */   
    private Entity build(MarcxmlControlField field) {
        
        Entity identifier = null;
        
        if (((MarcxmlControlField) field).getTag() == 1) {
            identifier = new Entity(Ld4lIdentifierType.LOCAL);
            String value = field.getTextValue();
            identifier.addAttribute(Ld4lDatatypeProp.VALUE, value);    
        }
        
        return identifier;
    }
    /**
     * Builds an identifier from a data field. Returns null if the field is
     * not recognized or implemented.
     */
    private Entity build(MarcxmlDataField field, BuildParams params)
             throws EntityBuilderException {       

        Entity identifier = null;
        
        MarcxmlSubfield subfield = (MarcxmlSubfield) params.getSubfield();
 
        if (field.getTag() == 35) {
            identifier = convert035(subfield);
        }
        
        return identifier;
    }

    /**
     * Builds an identifier from field 035. Never returns null.
     */
    // TODO Parts of this method will probably be shared by other conversions.
    private Entity convert035(MarcxmlSubfield subfield) 
            throws EntityBuilderException {
        
        Entity identifier;
        
        char code = subfield.getCode();
        
        // Ignoring $6 and $8 (not mapped by LC)
        if (code != 'a' && code != 'z') {
            throw new EntityBuilderException("Invalid subfield for 035.");                
        }

        Matcher matcher = 
                PATTERN_035_IDENTIFIER.matcher(subfield.getTextValue());
        if (! matcher.matches()) {
            throw new EntityBuilderException("Invalid 035 value.");
        }
        
        LOGGER.debug(matcher.group(2) + " " + matcher.group(3));
        
        /* Identifier type / source. Null if the group didn't match. */
        String orgCode = matcher.group(1);
        Type type = orgCode != null && orgCode.equals("OCoLC") ? 
                Ld4lIdentifierType.OCLC : Ld4lIdentifierType.LOCAL;
        identifier = new Entity(type);
        
        /* Identifier value */
        String id = matcher.group(3);
        identifier.addAttribute(Ld4lDatatypeProp.VALUE, id);

        /* Identifier status */
        Ld4lNamedIndividual status = (code == 'a' ?  
                Ld4lNamedIndividual.CURRENT : Ld4lNamedIndividual.CANCELLED );
        identifier.addExternalRelationship(Ld4lObjectProp.HAS_STATUS, status);
        
        return identifier;
    }
    
}
