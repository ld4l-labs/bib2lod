package org.ld4l.bib2lod.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public final class Bib2LodStringUtils {
    
    private static final Pattern PATTERN_FINAL_PUNCT = 
            Pattern.compile(".+[.,;:]$");
    
    private static final String PATTERN_FINAL_PUNCT_AND_WHITESPACE = 
            "\\s*[.,;:]?\\s*$";
    
    public static boolean endsWithPunct(String s) {
        Matcher m = PATTERN_FINAL_PUNCT.matcher(s);
        return m.matches();
    }
    
    public static String removeFinalPunct(String s) {
        return endsWithPunct(s) ? StringUtils.chop(s) : s;
    }
    
    public static String removeFinalPunctAndWhitespace(String s) {
        return s.replaceAll(PATTERN_FINAL_PUNCT_AND_WHITESPACE, "");
    }
    
    /**
     * Removes initial whitespace and final punctuation and  whitespace in any
     * order. Punctuation includes only ".,;:", since these may occur in the
     * record extraneously on data values such as dates, locations, names, 
     * titles, etc. and are not part of the actual text value.
     */
    public static String trim(String s) {
        return removeFinalPunctAndWhitespace(s.trim());
    }
}
