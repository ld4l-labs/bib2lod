package org.ld4l.bib2lod.entity;

import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.uris.UriService;

public class InstanceEntity extends Entity {
    
    private String bibId;

    /**
     * Constructor
     */
    public InstanceEntity() {
        super(Ld4lInstanceType.superClass());
    }
    
    /**
     * Constructor
     */    
    public InstanceEntity(Type type) {
        super(type);
    }
    
    public void setUri(String s) {
        this.bibId = s;
    }
    
    public void setBibId(String bibId) {
        this.bibId = bibId;
    }
    
    public String getBibId() {
        return bibId;
    }
    
    @Override
    protected String getUri() {
        return UriService.getUri(this, "_instance" + bibId);
    }
}
