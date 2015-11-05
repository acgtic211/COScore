package es.ual.acg.cos.ws.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"validation", "iduser", "message"})
@XmlAccessorType(XmlAccessType.NONE)
public class QueryUserResult {
	
	@XmlElement(required=true) 
	private boolean validation;
	@XmlElement(required=true) 
	private int iduser;	
	@XmlElement(required=true) 
	private String message;
	
	public boolean isValidation() {
		return validation;
	}
	public void setValidation(boolean validation) {
		this.validation = validation;
	}
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
	
	

}
