package es.ual.acg.cos.ws.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"userName", "userPassword", "userProfile"})
@XmlAccessorType(XmlAccessType.NONE)
public class CreateUserParams {

	@XmlElement(required=true) 
	private String userName;
	@XmlElement(required=true) 
	private String userPassword;
	@XmlElement(required=true) 
	private String userProfile;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
}
