package com.voxeo.tropo;

import java.io.Serializable;

/**
 * <p>Wraps the result from a Tropo action. Our {@link Tropo} will take care of generating this classes from the Tropo server
 * JSON response. It is normally included within a {@link TropoResult} object.</p>
 * 
 * @author martin
 *
 */
public class ActionResult implements Serializable {

	private static final long serialVersionUID = -6445440920067479716L;
	
	private String name;
	private Integer attempts;
	private String disposition;
	private String interpretation;
	private String utterance;
	private Integer confidence;
	private String value;
    private String concept;
    private String xml;
    private Integer duration;
    private String url;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAttempts() {
		return attempts;
	}
	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}
	public Integer getConfidence() {
		return confidence;
	}
	public void setConfidence(Integer confidence) {
		this.confidence = confidence;
	}
	public String getDisposition() {
		return disposition;
	}
	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}
	public String getInterpretation() {
		return interpretation;
	}
	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}
	public String getUtterance() {
		return utterance;
	}
	public void setUtterance(String utterrance) {
		this.utterance = utterrance;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Override
    public String toString(){
        return TropoUtils.toPrettyString(this);
    }
}
