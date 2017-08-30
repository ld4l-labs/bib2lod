/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.testing.xml.testrecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Navigate around the MockMarcxml instance and operate on the fields.
 */
interface FieldFinder {
    Leader.Builder findLeader();

    Leader.Builder addLeader(String value);

    Leader.Builder replaceLeader(String value);

    MockMarcxml.Builder deleteLeader();

    Controlfield.Builder findControlfield(String tag);

    Controlfield.Builder addControlfield(String tag, String value);

    Controlfield.Builder replaceControlfield(String tag, String value);

    MockMarcxml.Builder deleteControlfield(String tag);

    Datafield.Builder findDatafield(String tag);

    Datafield.Builder addDatafield(String tag, String ind1, String ind2);

    Datafield.Builder replaceDatafield(String tag, String ind1, String ind2);

    MockMarcxml.Builder deleteDatafield(String tag);

    MockMarcxml lock();

    /**
     * MockMarcxml.Builder inherits this implementation of FieldFinder, which
     * actually does the work.
     */
    static class Impl implements FieldFinder {
        protected MockMarcxml.Builder mmb;

        @Override
        public Leader.Builder findLeader() {
            for (Field.Builder fb : mmb.getFieldBuilders()) {
                if (fb.isLeader()) {
                    return fb.asLeader();
                }
            }
            throw new IllegalStateException("No Leader");
        }

        @Override
        public Leader.Builder addLeader(String value) {
            Leader.Builder l = new Leader.Builder(mmb, value);
            modifyBuilderList(mmb.getFieldBuilders(), null, l);
            return l;
        }

        @Override
        public Leader.Builder replaceLeader(String value) {
            Leader.Builder l = new Leader.Builder(mmb, value);
            modifyBuilderList(mmb.getFieldBuilders(), findLeader(), l);
            return l;
        }

        @Override
        public MockMarcxml.Builder deleteLeader() {
            modifyBuilderList(mmb.getFieldBuilders(), findLeader(), null);
            return mmb;
        }

        @Override
        public Controlfield.Builder findControlfield(String tag) {
            for (Field.Builder fb : mmb.getFieldBuilders()) {
                if (fb.isControlfield()
                        && fb.asControlfield().getTag().equals(tag)) {
                    return fb.asControlfield();
                }
            }
            throw new IllegalStateException(
                    "No Controlfield with tag '" + tag + "'");
        }

        @Override
        public Controlfield.Builder addControlfield(String tag, String value) {
            Controlfield.Builder cf = new Controlfield.Builder(mmb, tag, value);
            modifyBuilderList(mmb.getFieldBuilders(), null, cf);
            return cf;
        }

        @Override
        public Controlfield.Builder replaceControlfield(String tag,
                String value) {
            Controlfield.Builder cf = new Controlfield.Builder(mmb, tag, value);
            modifyBuilderList(mmb.getFieldBuilders(), findControlfield(tag),
                    cf);
            return cf;
        }

        @Override
        public MockMarcxml.Builder deleteControlfield(String tag) {
            modifyBuilderList(mmb.getFieldBuilders(), findControlfield(tag),
                    null);
            return mmb;
        }

        @Override
        public Datafield.Builder findDatafield(String tag) {
            for (Field.Builder fb : mmb.getFieldBuilders()) {
                if (fb.isDatafield() && fb.asDatafield().getTag().equals(tag)) {
                    return fb.asDatafield();
                }
            }
            throw new IllegalStateException(
                    "No Controlfield with tag '" + tag + "'");
        }

        @Override
        public Datafield.Builder addDatafield(String tag, String ind1,
                String ind2) {
            Datafield.Builder newField = new Datafield.Builder(mmb, tag, ind1,
                    ind2, Collections.<Subfield>emptyList());
            modifyBuilderList(mmb.getFieldBuilders(), null, newField);
            return newField;
        }

        @Override
        public Datafield.Builder replaceDatafield(String tag, String ind1,
                String ind2) {
            Datafield.Builder newField = new Datafield.Builder(mmb, tag, ind1,
                    ind2, Collections.<Subfield>emptyList());
            modifyBuilderList(mmb.getFieldBuilders(), findDatafield(tag),
                    newField);
            return newField;
        }

        @Override
        public MockMarcxml.Builder deleteDatafield(String tag) {
            modifyBuilderList(mmb.getFieldBuilders(), findDatafield(tag), null);
            return mmb;
        }

        @Override
        public MockMarcxml lock() {
            return new MockMarcxml(mmb);
        }

        /**
         * Create a new instance with a modified field list, whose fields point
         * to the new instance.
         */
        protected <T> void modifyBuilderList(List<T> existingList, T oldField,
                T newField) {
            List<T> newList = new ArrayList<>();
            for (T nextField : existingList) {
                if (nextField == oldField && newField == null) {
                    // omit the field
                } else if (nextField == oldField && newField != null) {
                    // replace the field
                    newList.add(newField);
                } else {
                    // keep the field
                    newList.add(nextField);
                }
            }
            if (oldField == null && newField != null) {
                // append the field
                newList.add(newField);
            }

            existingList.clear();
            existingList.addAll(newList);
        }

    }

    /**
     * Fields and Subfields inherit this implementation of FieldFinder, which
     * just delegates to the MockMarcxml instance.
     */
    static class Wrapper implements FieldFinder {
        private final FieldFinder parent;

        Wrapper(FieldFinder parent) {
            this.parent = parent;
        }

        @Override
        public Leader.Builder findLeader() {
            return parent.findLeader();
        }

        @Override
        public Leader.Builder addLeader(String value) {
            return parent.addLeader(value);
        }

        @Override
        public Leader.Builder replaceLeader(String value) {
            return parent.replaceLeader(value);
        }

        @Override
        public MockMarcxml.Builder deleteLeader() {
            return parent.deleteLeader();
        }

        @Override
        public Controlfield.Builder findControlfield(String tag) {
            return parent.findControlfield(tag);
        }

        @Override
        public Controlfield.Builder addControlfield(String tag, String value) {
            return parent.addControlfield(tag, value);
        }

        @Override
        public Controlfield.Builder replaceControlfield(String tag,
                String value) {
            return parent.replaceControlfield(tag, value);
        }

        @Override
        public MockMarcxml.Builder deleteControlfield(String tag) {
            return parent.deleteControlfield(tag);
        }

        @Override
        public Datafield.Builder findDatafield(String tag) {
            return parent.findDatafield(tag);
        }

        @Override
        public Datafield.Builder addDatafield(String tag, String ind1,
                String ind2) {
            return parent.addDatafield(tag, ind1, ind2);
        }

        @Override
        public Datafield.Builder replaceDatafield(String tag, String ind1,
                String ind2) {
            return parent.replaceDatafield(tag, ind1, ind2);
        }

        @Override
        public MockMarcxml.Builder deleteDatafield(String tag) {
            return parent.deleteDatafield(tag);
        }

        @Override
        public MockMarcxml lock() {
            return parent.lock();
        }
    }
}
