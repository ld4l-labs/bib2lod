package org.ld4l.bib2lod.datatypes;

import org.apache.jena.datatypes.BaseDatatype;
import org.apache.jena.datatypes.DatatypeFormatException;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;

public class BibliotekoDatatype {
    
    public enum BibDatatype implements Datatype {
        LEGACY_SOURCE_DATA(LegacySourceDataType.getType());
        
        private RDFDatatype type;
        
        private BibDatatype(RDFDatatype type) {
            this.type = type;
        }
        
        @Override
        public RDFDatatype rdfType() {
            return type;
        }
    }

    public static class LegacySourceDataType extends BaseDatatype {
        
        public static final String TYPE_URI = NAMESPACE + "legacySourceData";               
        public static final RDFDatatype TYPE = new LegacySourceDataType();
        
        static {
            TYPE_MAPPER.registerDatatype(TYPE);
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
        
        public static RDFDatatype getType() {
            return TYPE;
        }
        
    }
    
    private static final String NAMESPACE = "http://bibliotek-o.org/datatypes/";
    private static final TypeMapper TYPE_MAPPER = TypeMapper.getInstance();

}
