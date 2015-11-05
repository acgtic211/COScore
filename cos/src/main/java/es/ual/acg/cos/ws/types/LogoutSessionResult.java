package es.ual.acg.cos.ws.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"deleted", "message"})
@XmlAccessorType(XmlAccessType.NONE)
public class LogoutSessionResult {

	@XmlElement(required=true) 
	private boolean deleted;
	@XmlElement(required=true) 
	private String message;
	
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
