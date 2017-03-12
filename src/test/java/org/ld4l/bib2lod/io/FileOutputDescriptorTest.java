/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import static org.apache.jena.rdf.model.ModelFactory.createDefaultModel;
import static org.apache.jena.rdf.model.ResourceFactory.createPlainLiteral;
import static org.apache.jena.rdf.model.ResourceFactory.createProperty;
import static org.apache.jena.rdf.model.ResourceFactory.createResource;
import static org.apache.jena.rdf.model.ResourceFactory.createStatement;
import static org.junit.Assert.*;
import static org.ld4l.bib2lod.io.FileOutputService.Format.NTRIPLES;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.ld4l.bib2lod.configuration.BaseConfiguration;
import org.ld4l.bib2lod.io.FileOutputService.Format;
import org.ld4l.bib2lod.io.OutputService.OutputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputServiceException;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Check that the writing methods work, for an assortment of formats.
 */
public class FileOutputDescriptorTest extends AbstractTestClass {

    private static final String OUTPUT_FILENAME = "outputFile";
    private static final String SUBJECT_1 = "http://test/subject1";
    private static final String SUBJECT_2 = "http://test/subject2";
    private static final String PREDICATE_1 = "http://test/predicate1";
    private static final String PREDICATE_2 = "http://test/predicate2";
    private static final String LITERAL_1 = "Literal1";
    private static final String LITERAL_2 = "Literal2";

    private static final String OUTPUT_NOTHING = "";

    private static final String OUTPUT_ONE_MODEL = "" //
            + String.format( //
                    "<%s> <%s> \"%s\" .\n", SUBJECT_1, PREDICATE_1, LITERAL_1);

    private static final String OUTPUT_TWO_MODELS = "" //
            + String.format( //
                    "<%s> <%s> \"%s\" .\n", SUBJECT_1, PREDICATE_1, LITERAL_1)
            + String.format( //
                    "<%s> <%s> \"%s\" .\n", SUBJECT_2, PREDICATE_2, LITERAL_2);

    @Rule
    public TemporaryFolder folder = new TemporaryFolder() {
        @Override
        protected void before() throws Exception {
            folder.create();
        }

        @Override
        protected void after() {
            folder.delete();
        }
    };

    private Format format;
    private OutputDescriptor sink;

    // ----------------------------------------------------------------------
    // The tests
    //
    // Repeat with all acceptable formats.
    // ----------------------------------------------------------------------

    @Test
    public void writeNothing() throws IOException, OutputServiceException {
        createServiceAndSink(NTRIPLES);
        sink.close();
        assertOutputIsAsExpected(OUTPUT_NOTHING, readOutputFile());
    }

    @Test
    public void writeAModel() throws IOException, OutputServiceException {
        createServiceAndSink(NTRIPLES);
        sink.writeModel(model(dataProperty(SUBJECT_1, PREDICATE_1, LITERAL_1)));
        sink.close();
        assertOutputIsAsExpected(OUTPUT_ONE_MODEL, readOutputFile());
    }

    @Test
    public void writeTwoModels() throws IOException, OutputServiceException {
        createServiceAndSink(NTRIPLES);
        sink.writeModel(model(dataProperty(SUBJECT_1, PREDICATE_1, LITERAL_1)));
        sink.writeModel(model(dataProperty(SUBJECT_2, PREDICATE_2, LITERAL_2)));
        sink.close();
        assertOutputIsAsExpected(OUTPUT_TWO_MODELS, readOutputFile());
    }

    @Test
    public void multipleCallsToClose_noProblem()
            throws IOException, OutputServiceException {
        createServiceAndSink(NTRIPLES);
        sink.close();
        sink.close();
        assertOutputIsAsExpected(OUTPUT_NOTHING, readOutputFile());
    }

    @Test(expected = OutputServiceException.class)
    public void writeAfterClose_throwsException()
            throws IOException, OutputServiceException {
        createServiceAndSink(NTRIPLES);
        sink.close();
        sink.writeModel(model(dataProperty(SUBJECT_1, PREDICATE_1, LITERAL_1)));
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------

    private void createServiceAndSink(Format f) throws IOException {
        format = f;

        FileOutputService service = new FileOutputService(
                new BaseConfiguration() {
                    {
                        outputDestination = folder.getRoot().getAbsolutePath();
                        outputFormat = f.toString();
                    }
                });

        sink = service.openSink(new FileInputService.InputMetadata() {
            @Override
            public String getName() {
                return OUTPUT_FILENAME;
            }
        });
    }

    private Statement dataProperty(String subjectUri, String predicateUri,
            String objectLiteral) {
        return createStatement(createResource(subjectUri),
                createProperty(predicateUri),
                createPlainLiteral(objectLiteral));
    }

    private Model model(Statement... stmts) {
        return createDefaultModel().add(stmts);
    }

    private String readOutputFile() throws IOException {
        return FileUtils
                .readFileToString(new File(folder.getRoot().getAbsoluteFile(),
                        OUTPUT_FILENAME + "." + NTRIPLES.getExtension()));
    }

    private void assertOutputIsAsExpected(String expected, String actual) {
        String lang = format.getLanguage();
        assertEquals(modelToCanonicalString(expected, lang),
                modelToCanonicalString(actual, lang));
    }

    private String modelToCanonicalString(String rdf, String lang) {
        Writer w = new StringWriter();
        ModelFactory.createDefaultModel()
                .read(new StringReader(rdf), null, lang).write(w, "N-TRIPLES");
        return w.toString();
    }
}
