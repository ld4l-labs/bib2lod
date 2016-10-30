package org.ld4l.bib2lod.uri;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.JsonConfigConfiguration;

/**
 * Tests for RandomUriMinter
 */
public class RandomUriMinterTest {

    @Before
    public void setup() {
        
    }
    
    @Test
    // Is there any point to this test? It just duplicates what RandomUriMinter 
    // does, and it ends up requiring a lot of changes to that class.
    public void mintRandomLocalName() {
        String[] args = new String[] {"-c", "src/test/java/org/ld4l/bib2lod/uri/resources/config.json"};
        Configuration configuration;
        try {
            configuration = new JsonConfigConfiguration(args);
            RandomUriMinter minter = (RandomUriMinter) configuration.getUriMinter();
            String uuid = minter.getRandomUuid();
            String expectedLocalName = minter.getLocalNameAlphaPrefix() + minter.hash(uuid);
            String actualLocalName = minter.mintLocalName(uuid);
            assertEquals(expectedLocalName, actualLocalName);
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        } 
        

    }

}
