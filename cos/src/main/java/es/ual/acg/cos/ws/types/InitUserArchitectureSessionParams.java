package es.ual.acg.cos.ws.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import es.ual.acg.cos.types.UserInteractionData;

@XmlType(propOrder = {"userId","interaction"})
@XmlAccessorType(XmlAccessType.NONE)
public class InitUserArchitectureSessionParams {

	@XmlElement(required=true) 
	private String userId;
	@XmlElement(required=true)
	private UserInteractionData interaction;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public UserInteractionData getInteraction() {
		return interaction;
	}
	public void setInteraction(UserInteractionData interaction) {
		this.interaction = interaction;
	}

}