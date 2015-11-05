package es.ual.acg.cos.ws.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"validation", "userID", "message"})
@XmlAccessorType(XmlAccessType.NONE)
public class LoginSessionResult {
	
	@XmlElement(required=true) 
	private boolean validation;
	@XmlElement(required=true) 
	private String userID;
	@XmlElement(required=true) 
	private String message;
	
	public boolean isValidation() {
		return validation;
	}
	public void setValidation(boolean validation) {
		this.validation = validation;
	}
	public String getUserId() {
		return userID;
	}
	public void setUserId(String userID) {
		this.userID = userID;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
}