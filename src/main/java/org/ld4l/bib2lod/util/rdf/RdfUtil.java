package org.ld4l.bib2lod.util.rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

// TODO Can we put these as static methods in one of the conversion classes?
public final class RdfUtil {

    // For development/debugging
    public static String printModel(Model model, String msg) {
 
        StringBuilder sb = new StringBuilder();
        appendLine(sb, "");
        if (msg != null) {
            appendLine(sb, msg);
        }
        int stmtCount = 0;
        StmtIterator statements = model.listStatements();   
        while (statements.hasNext()) {
            stmtCount++;
            Statement statement = statements.nextStatement();
            appendLine(sb, stmtCount + ". " + statement.toString());
        }
        
        return sb.toString();
    }
    
    public static String appendLine(StringBuilder sb, String value) {
        sb.append(value + System.getProperty("line.separator"));
        return sb.toString();
    }


}
