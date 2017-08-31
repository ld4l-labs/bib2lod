/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.testing.xml.testrecord;

/**
 * Additional methods to navigate around the Datafield and operate on the
 * Subfields.
 */
interface SubfieldFinder extends FieldFinder {
    Subfield.Builder findSubfield(String code);

    Subfield.Builder addSubfield(String code, String value);

    Subfield.Builder replaceSubfield(String code, String value);

    Datafield.Builder deleteSubfield(String code);

    /**
     * Datafield.Builder inherits this implementation of SubfieldFinder, which
     * actually does the work.
     */
    static class Impl extends FieldFinder.Impl implements SubfieldFinder {
        protected Datafield.Builder dfb;

        @Override
        public Subfield.Builder findSubfield(String code) {
            for (Subfield.Builder sfb : dfb.getSubfields()) {
                if (sfb.getCode().equals(code)) {
                    return sfb;
                }
            }
            throw new RuntimeException("No Subfield with code '" + code + "'");
        }

        @Override
        public Subfield.Builder addSubfield(String code, String value) {
            Subfield.Builder sfb = new Subfield.Builder(mmb, dfb, code, value);
            modifyBuilderList(dfb.getSubfields(), null, sfb);
            return sfb;
        }

        @Override
        public Subfield.Builder replaceSubfield(String code, String value) {
            Subfield.Builder sfb = new Subfield.Builder(mmb, dfb, code, value);
            modifyBuilderList(dfb.getSubfields(), findSubfield(code), sfb);
            return sfb;
        }

        @Override
        public Datafield.Builder deleteSubfield(String code) {
            modifyBuilderList(dfb.getSubfields(), findSubfield(code), null);
            return dfb;
        }

    }

    /**
     * Subfields inherit this implementation of SubfieldFinder, which just
     * delegates to the MockMarcxml instance.
     */
    static class Wrapper extends FieldFinder.Wrapper implements SubfieldFinder {
        private SubfieldFinder df;

        Wrapper(FieldFinder parent, SubfieldFinder df) {
            super(parent);
            this.df = df;
        }

        @Override
        public Subfield.Builder findSubfield(String code) {
            return df.findSubfield(code);
        }

        @Override
        public Subfield.Builder addSubfield(String code, String value) {
            return df.addSubfield(code, value);
        }

        @Override
        public Subfield.Builder replaceSubfield(String code, String value) {
            return df.replaceSubfield(code, value);
        }

        @Override
        public Datafield.Builder deleteSubfield(String code) {
            return df.deleteSubfield(code);
        }

    }
}
