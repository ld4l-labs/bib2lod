package org.ld4l.bib2lod.datatypes;

import org.apache.jena.datatypes.BaseDatatype;
import org.apache.jena.datatypes.DatatypeFormatException;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.LegacySourceDataEntityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;

/**
 * Groups the bibliotek-o custom datatypes together and provides an enum to
 * designate values.
 */
public class Ld4lCustomDatatypes {
    
    public enum BibDatatype implements Datatype {
        EDTF(EdtfType.getRdfDatatype()),
        LEGACY_SOURCE_DATA(LegacySourceDataType.getRdfDatatype());
        
        private RDFDatatype type;
        
        private BibDatatype(RDFDatatype type) {
            this.type = type;
        }
        
        @Override
        public RDFDatatype rdfType() {
            return type;
        }
    }

    /**
     * A bibliotek-o custom datatype to flag legacy source data for future
     * parsing and normalization.
     */
    public static class LegacySourceDataType extends BaseDatatype {
        
        private static final String TYPE_URI = 
                Ld4lNamespace.BIB_DATATYPE.uri() + "legacySourceData";               
        private static final RDFDatatype TYPE = new LegacySourceDataType();
        
        /*
         * A kluge to instantiate this builder only once per program 
         * execution rather than every time it's needed, since it doesn't
         * fit into the overall Type-to-Builder architecture.
         */
        private static final EntityBuilder ENTITY_BUILDER = 
                new LegacySourceDataEntityBuilder();
        
        static {
            TypeMapper.getInstance().registerDatatype(TYPE);
        }
        
        /* Private constructor, since global instance */
        private LegacySourceDataType() {
            super(TYPE_URI);
        }
        
        /*
         * (non-Javadoc)
         * @see org.apache.jena.datatypes.BaseDatatype#unparse(java.lang.Object)
         */
        @Override
        public String unparse(Object value) {
            return (String) value;
        }
        
        /*
         * (non-Javadoc)
         * @see org.apache.jena.datatypes.BaseDatatype#parse(java.lang.String)
         */
        @Override
        public Object parse(String lexicalForm) throws DatatypeFormatException {
            return lexicalForm;
        }
        
        public static RDFDatatype getRdfDatatype() {
            return TYPE;
        }   
        
        public static EntityBuilder getBuilder() {
            return ENTITY_BUILDER;
        }
    }
    
    /**
     * The Extended Date/Time Format datatype.
     */
    public static class EdtfType extends BaseDatatype {
        
        private static final String TYPE_URI = Ld4lNamespace.EDTF.uri() + "EDTF";               
        private static final RDFDatatype TYPE = new EdtfType();
        
        static {
            TypeMapper.getInstance().registerDatatype(TYPE);
        }
        
        /* Private constructor, since global instance */
        private EdtfType() {
            super(TYPE_URI);
        }
        
        /*
         * (non-Javadoc)
         * @see org.apache.jena.datatypes.BaseDatatype#unparse(java.lang.Object)
         */
        @Override
        public String unparse(Object value) {
            return (String) value;
        }
        
        /*
         * (non-Javadoc)
         * @see org.apache.jena.datatypes.BaseDatatype#parse(java.lang.String)
         */
        @Override
        public Object parse(String lexicalForm) throws DatatypeFormatException {
            return lexicalForm;
        }
        
        public static RDFDatatype getRdfDatatype() {
            return TYPE;
        }    
    }

}
