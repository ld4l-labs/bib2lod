package org.ld4l.bib2lod.context.configuration;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO There may be no common methods here; then remove this class
public abstract class BaseConfigurer implements Configurer {

    private static final Logger LOGGER = 
            LogManager.getLogger(BaseConfigurer.class);
    
    protected String[] args;
    
    public BaseConfigurer(String[] args)  {
        this.args = args;
    }
    
    /**
     * Parse commandline options.
     * @param options
     * @param args
     * @return
     * @throws ParseException
     */
    protected CommandLine getCommandLine(Options options, String[] args) 
            throws ParseException {
        
        // Parse program arguments
        CommandLineParser parser = new DefaultParser();
        
        return parser.parse(options, args);           
    }
    
//  /**
//  * Print help text.
//  * @param options
//  */
 // TODO Currently not used. What is best way to do this? If print here,
 // need to catch exceptions and return null up the chain. If print from
 // manager or elsewhere, need to supply a method to provide the help string.
 // See HelpFormatter.renderOptions() or HelpFormatter.renderWrappedText().
// private void printHelp(Options options) {
//     
//     HelpFormatter formatter = new HelpFormatter();
//     formatter.setWidth(80);
//     formatter.printHelp("java -jar Bib2Lod.jar", options, true);
// }

}
