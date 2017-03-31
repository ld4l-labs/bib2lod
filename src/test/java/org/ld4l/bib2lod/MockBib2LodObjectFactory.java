/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod;

/**
 * When created, sets itself as a wrapper for the default factory instance.
 * 
 * When client code requests an object, checks to see whether a mock has been
 * supplied. If not, delegates to the default factory instance.
 */
public class MockBib2LodObjectFactory extends Bib2LodObjectFactory {

//    // ----------------------------------------------------------------------
//    // Mocking infrastructure
//    // ----------------------------------------------------------------------
//
//    private final Bib2LodObjectFactory defaultFactory;
//    private OptionsReader optionsReader;
//    private Configuration configuration;
//    private Converter converter;
//    private InputService inputService;
//    private OutputService outputService;
//
//    public MockBib2LodObjectFactory() {
//        try {
//            Field field = Bib2LodObjectFactory.class
//                    .getDeclaredField("instance");
//            field.setAccessible(true);
//            this.defaultFactory = (Bib2LodObjectFactory) field.get(null);
//            field.set(null, this);
//        } catch (NoSuchFieldException | SecurityException
//                | IllegalArgumentException | IllegalAccessException e) {
//            throw new RuntimeException(
//                    "Failed to initialize the MockBib2LodObjectFactory", e);
//        }
//    }
//
//    // ----------------------------------------------------------------------
//    // Setters
//    // ----------------------------------------------------------------------
//    
//    public void setOptionsReader(OptionsReader optionsReader) {
//        this.optionsReader = optionsReader;
//    }
//
//    public void setConfiguration(Configuration configuration) {
//        this.configuration = configuration;
//    }
//    
//    public void setConverter(Converter converter) {
//        this.converter = converter;
//    }
//    
//    public void setInputService(InputService service) {
//        this.inputService = service;
//    }
//    
//    public void setOutputService(OutputService service) {
//        this.outputService = service;
//    }
//    
//    // ----------------------------------------------------------------------
//    // Getters
//    // ----------------------------------------------------------------------
//    
//    public Converter getConverter() {
//        return this.converter;
//    }
//    
//    // ----------------------------------------------------------------------
//    // Mocked methods
//    // ----------------------------------------------------------------------
//
//    /* (non-Javadoc)
//     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createOptionsReader(org.ld4l.bib2lod.configuration.Configuration)
//     */
//    @Override
//    public OptionsReader createOptionsReader(String[] args) {
//        return (optionsReader != null) ? optionsReader
//                : defaultFactory.createOptionsReader(args);
//    }
//
//    /* (non-Javadoc)
//     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createConfiguration(org.ld4l.bib2lod.configuration.Configuration)
//     */
//    @Override
//    public Configuration createConfiguration(String[] args) {
//        try {
//            return new MockConfiguration(args);
//        } catch (IOException | ParseException e) {
//            throw new Bib2LodObjectFactoryException(e);
//        }
//    }
//
//    /* (non-Javadoc)
//     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createCleaner()
//     */
//    @Override
//    public Cleaner createCleaner() {
//        throw new RuntimeException("Method not implemented.");
//    }
//
//    /* (non-Javadoc)
//     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createConverter()
//     */
//    @Override
//    public Converter createConverter() {
//        return (converter != null) ? converter
//                : defaultFactory.createConverter();
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createUriService(java.lang.String, org.ld4l.bib2lod.configuration.Configuration)
//     */
//    @Override
//    public UriService createUriService(String className, Configuration configuration) {
//        throw new RuntimeException("Method not implemented.");
//    }
//    
//    @Override
//    public InputService createInputService(Configuration configuration) {
//        return (inputService != null) ? inputService
//                : defaultFactory.createInputService(configuration);
//    }
//    
//    @Override
//    public OutputService createOutputService(Configuration configuration) {
//        return (outputService != null) ? outputService
//                : defaultFactory.createOutputService(configuration);
//    }
//
//    @Override
//    public Entity createEntity(Resource ontClass) {
//        throw new RuntimeException("Method not implemented.");
//    }
//    
//    @Override
//    public Entity createEntity(Type ontClass) {
//        throw new RuntimeException("Method not implemented.");
//    }
//
//    @Override
//    public Entity createEntity(Entity entity) {
//        throw new RuntimeException("Method not implemented.");
//    }
//
}
