package es.ual.acg.cos.ws.types;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"validation", "profiles","message"})
@XmlAccessorType(XmlAccessType.NONE)
public class QueryProfileResult {
	
	@XmlElement(required=true) 
	private boolean validation;
	@XmlElement(required=true) 
	private List<String> profiles;
	@XmlElement(required=true) 
	private String message;
	
	public boolean isValidation() {
		return validation;
	}
	public void setValidation(boolean validation) {
		this.validation = validation;
	}
	public List<String> getProfiles() {
		return profiles;
	}
	public void setProfiles(List<String> profiles) {
		this.profiles = profiles;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

	
}