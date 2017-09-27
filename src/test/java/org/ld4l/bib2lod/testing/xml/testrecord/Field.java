/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.testing.xml.testrecord;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The MockMarcxml instance will keep a list of Fields.
 * 
 * Leader, Controlfield, and Datafield should each implement this interface, 
 * and override the appropriate "is" method.
 */
interface Field {
    Element element(Document doc);

    Builder builder(MockMarcxml.Builder mmb);

    interface Builder {
        default boolean isLeader() {
            return false;
        }

        default Leader.Builder asLeader() {
            return (Leader.Builder) this;
        }

        default boolean isControlfield() {
            return false;
        }

        default Controlfield.Builder asControlfield() {
            return (Controlfield.Builder) this;
        }

        default boolean isDatafield() {
            return false;
        }

        default Datafield.Builder asDatafield() {
            return (Datafield.Builder) this;
        }

        Field buildField();
    }
}
