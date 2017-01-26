/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ld4l.bib2lod.Namespace;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entities.Instance;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Entity.EntityInstantiationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Builds an Instance Entity from a record. This class is input-specific: it
 * interprets the MARCML record to build the input-neutral Instance.
 */
public class MarcxmlInstanceBuilder extends BaseEntityBuilder {
 
    private static final String TAG_ATTRIBUTE = "tag";
    
    private Instance instance;
    
    /**
     * Constructor
     * @param configuration
     */
    public MarcxmlInstanceBuilder(Configuration configuration) {
        super(configuration);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.entitybuilders.EntityBuilder#build(org.w3c.dom.Element)
     */
    @Override
    public List<Entity> build(Element record) {
        
        List<Entity> resources = new ArrayList<Entity>();
        
        try {
            instance = (Instance) Entity.instance(Instance.class);
        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException e) {
            throw new EntityInstantiationException(e.getMessage(), e.getCause());
        }
        
        addTypes(record);       
        addIdentifiers(record);
        addTitles(record);      
        addWorks(record);
        
        // TODO add others...
        
        resources.add(instance);       
        
        return resources;
    }
    
    private void addTypes(Element record) {
        Map<Namespace, String> types = new HashMap<Namespace, String>();
        // For now, all we have is the Instance superclass
        types.put(Namespace.BIBFRAME, "Instance");
        instance.setTypes(types);
    }
    
    private void addIdentifiers(Element record) {

        // extract the local identifier value
        // call instance.setLocalIdentifier(value);
        //<controlfield tag="001">102063</controlfield>
        // can we get by tagname and attributevalue? or first tagname,
        // then attribute value?
        NodeList controlFields = 
                record.getElementsByTagName("controlfield");
        for (int index = 0; index <= controlFields.getLength(); index++) {
            Element field = (Element) controlFields.item(index);
            String tag = field.getAttribute(TAG_ATTRIBUTE);
            if (tag != null && tag.equals("001")) {
                // TODO Add this
                // instance.setIdentifier(type, value);
                break;
            }               
        }        
    }
    
    private void addTitles(Element record) {
        
    }
    
    private void addWorks(Element record) {
        // In some cases there is more than one Work (collections)
        
    }



}
