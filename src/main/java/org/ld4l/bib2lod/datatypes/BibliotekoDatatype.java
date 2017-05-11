package org.ld4l.bib2lod.datatypes;

import org.apache.jena.datatypes.BaseDatatype;
import org.apache.jena.datatypes.DatatypeFormatException;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;

/**
 * Groups the bibliotek-o custom datatypes together and provides an enum to
 * designate possible values.
 */
public class BibliotekoDatatype {
    
    public enum BibDatatype implements Datatype {
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
        
        public static final String TYPE_URI = NAMESPACE + "legacySourceData";               
        public static final RDFDatatype TYPE = new LegacySourceDataType();
        
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
        
    }
    
    private static final String NAMESPACE = "http://bibliotek-o.org/datatypes/";

}
