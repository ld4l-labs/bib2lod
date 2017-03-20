/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders;

/**
 * Builds an Instance Entity from a record. This class is input-specific: it
 * interprets the MARCML record to build the input-neutral Instance.
 */
public class MarcxmlInstanceBuilderDeprecated { //extends BaseEntityBuilder {
 
//    private static final String TAG_ATTRIBUTE = "tag";
//    
//    private Instance instance;
//    
//    /**
//     * Constructor
//     * @param configuration
//     */
//    public MarcxmlInstanceBuilderDeprecated(Configuration configuration) {
//        super(configuration);
//    }
//
//    /* (non-Javadoc)
//     * @see org.ld4l.bib2lod.entitybuilders.EntityBuilder#build(org.w3c.dom.Element)
//     */
//    @Override
//    public List<Entity> build(Element record) {
//        
//        // Returns a List of Entities because creating one Entity may entail
//        // creating additional Entities
//        List<Entity> entities = new ArrayList<Entity>();
//
//        instance = (Instance) Entity.instance(Instance.class);
//        
//        addTypes(record);
//        
//        // These need to get added to the entities returned. The current
//        // structure doesn't allow for that.
//        addIdentifiers(record);
//        addTitles(record);      
//        addWorks(record);
//        
//        // TODO add others...
//        
//        entities.add(instance);       
//        
//        return entities;
//    }
//    
//    // Later it will be apparent that determining the types depends on the input 
//    // format - instance subclasses will be indicated in an input-specific way.
//    private void addTypes(Element record) {
//        Map<Namespace, String> types = new HashMap<Namespace, String>();
//        // For now, all we have is the Instance superclass
//        types.put(Namespace.BIBFRAME, "Instance");
//        instance.setTypes(types);
//    }
//    
//    private void addIdentifiers(Element record) {
//
//        // extract the local identifier value
//        // call instance.setLocalIdentifier(value);
//        //<controlfield tag="001">102063</controlfield>
//        // can we get by tagname and attributevalue? or first tagname,
//        // then attribute value?
//        NodeList controlFields = 
//                record.getElementsByTagName("controlfield");
//        for (int index = 0; index <= controlFields.getLength(); index++) {
//            Element field = (Element) controlFields.item(index);
//            String tag = field.getAttribute(TAG_ATTRIBUTE);
//            if (tag != null && tag.equals("001")) {
//                // TODO Add this
//                // instance.setIdentifier(type, value);
//                break;
//            }               
//        }        
//    }
//    
//    private void addTitles(Element record) {
//        
//    }
//    
//    private void addWorks(Element record) {
//        // In some cases there is more than one Work (collections)
//        
//    }



}
