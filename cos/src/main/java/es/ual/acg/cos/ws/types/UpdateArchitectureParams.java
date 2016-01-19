package es.ual.acg.cos.ws.types;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import es.ual.acg.cos.types.ComponentData;
import es.ual.acg.cos.types.UserInteractionData;

@XmlType(propOrder = {"userId","componentInstance","actionDone","newsComponentData","interaction"})
@XmlAccessorType(XmlAccessType.NONE)
public class UpdateArchitectureParams {
	@XmlElement(required=true) 
	private String userId;
	@XmlElement(required=false)	
	private String componentInstance;
	@XmlElement(required=true)
	private String actionDone;
	@XmlElement(required=true)
	private List<ComponentData> newsComponentData;
	@XmlElement(required=true)
	private UserInteractionData interaction;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getComponentInstance() {
		return componentInstance;
	}
	public void setComponentInstance(String componentInstance) {
		this.componentInstance = componentInstance;
	}
	public String getActionDone() {
		return actionDone;
	}
	public void setActionDone(String actionDone) {
		this.actionDone = actionDone;
	}
	public List<ComponentData> getNewsComponentData() {
		return newsComponentData;
	}
	public void setNewsComponentData(List<ComponentData> newsComponentData) {
		this.newsComponentData = newsComponentData;
	}
	public UserInteractionData getInteraction() {
		return interaction;
	}
	public void setInteraction(UserInteractionData interaction) {
		this.interaction = interaction;
	}
}
