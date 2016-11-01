package org.ld4l.bib2lod.conversion.to_rdf.ld4l;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.clean.Cleaner;
import org.ld4l.bib2lod.clean.marcxml.MarcxmlCleaner;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.conversion.to_rdf.MarcxmlToRdf;
import org.ld4l.bib2lod.conversion.to_rdf.ResourceBuilder;
import org.ld4l.bib2lod.util.rdf.RdfUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MarcxmlToLd4lRdf extends MarcxmlToRdf {

    private static final Logger LOGGER = 
            LogManager.getLogger(MarcxmlToLd4lRdf.class);
    
    public MarcxmlToLd4lRdf(Configuration configuration) {
        super(configuration);
    }
    
//    protected StringBuilder convert() {
//        return null;
//    }
    
    @Override
    public Model convertRecord(Element record) {

        // TODO Use factory instead of constructor?
        Cleaner cleaner = new MarcxmlCleaner();
        // Node cleaned = clean(record);
        
        Model model = ModelFactory.createDefaultModel();
        
        ResourceBuilder instanceBuilder = new InstanceBuilder(uriMinter);
        Resource instance = instanceBuilder.build(record, model);
        model.add(instance.getModel());
        
        // TODO *** Put this in the instance builder - that's why we pass in
        // the record.
        //

        // then datafields
        // for each datafield - loop through subfields
        // but not so simple - may need to consider some fields together
        
        // model.add(convertDataFields())
        
        LOGGER.debug(RdfUtil.printModel(model, "Model at end of convertRecord():"));
        return model;
    }    
    


}
