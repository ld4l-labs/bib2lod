package org.ld4l.bib2lod.configuration.options;

import java.io.IOException;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.fasterxml.jackson.databind.JsonNode;

public interface OptionsReader {

    public JsonNode configure() throws IOException, ParseException;
    
}
