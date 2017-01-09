package org.ld4l.bib2lod.util.rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

/**
 * A utility class for working with RDF.
 */
// TODO Can we put these as static methods in one of the conversion classes?
public final class RdfUtil {

    /**
     * Output a string representation of a Model for logging and debugging.
     * @param model - the Model
     * @param msg - a message to prefix to the output
     * @return the string representation of the prefix and Model
     */
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
    
    /**
     * Append a line to a StringBuilder, using the system-dependent line
     * separator.
     * @param sb - the StringBuilder
     * @param line - the line to append to the StringBuilder
     */
    private static void appendLine(StringBuilder sb, String line) {
        sb.append(line + System.getProperty("line.separator"));
    }


}
