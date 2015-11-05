package es.ual.acg.cos.ws.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"userId", "newUserName", "newUserPassword", "newUserProfile"})
@XmlAccessorType(XmlAccessType.NONE)
public class UpdateUserParams {
	
	@XmlElement(required=true) 
	private String userId;
	@XmlElement(required=true) 
	private String newUserName;
	@XmlElement(required=true) 
	private String newUserPassword;
	@XmlElement(required=true) 
	private String newUserProfile;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNewUserName() {
		return newUserName;
	}
	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
	}
	public String getNewUserPassword() {
		return newUserPassword;
	}
	public void setNewUserPassword(String newUserPassword) {
		this.newUserPassword = newUserPassword;
	}
	public String getNewUserProfile() {
		return newUserProfile;
	}
	public void setNewUserProfile(String newUserProfile) {
		this.newUserProfile = newUserProfile;
	}
	
}