package es.ual.acg.cos.ws.types;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import es.ual.acg.cos.types.ComponentData;
import es.ual.acg.cos.types.UserInteractionData;

@XmlType(propOrder = {"userId","newSession","interaction","operationPerformed","componentId","groupComponent","ungroupComponent","cotsget"})
@XmlAccessorType(XmlAccessType.NONE)
public class RegisterInteractionParams {
	@XmlElement(required=true) 
	private String userId;
	@XmlElement(required=false) 
	private String newSession;
	@XmlElement(required=false) 
	private UserInteractionData interaction;
	@XmlElement(required=true)
	private String operationPerformed;
	@XmlElement(required=false)
	private String componentId;
	@XmlElement(required=false)
	private List<String> groupComponent;
	@XmlElement(required=false)
	private List<String> ungroupComponent; 
	@XmlElement(required=false)
	private List<ComponentData> cotsget;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNewSession() {
		return newSession;
	}
	public void setNewSession(String newSession) {
		this.newSession = newSession;
	}
	public String getOperationPerformed() {
		return operationPerformed;
	}
	public void setOperationPerformed(String operationPerformed) {
		this.operationPerformed = operationPerformed;
	}
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	public List<String> getGroupComponent() {
		return groupComponent;
	}
	public void setGroupComponent(List<String> groupComponent) {
		this.groupComponent = groupComponent;
	}
	public List<String> getUngroupComponent() {
		return ungroupComponent;
	}
	public void setUngroupComponent(List<String> ungroupComponent) {
		this.ungroupComponent = ungroupComponent;
	}
	public List<ComponentData> getCotsget() {
		return cotsget;
	}
	public void setCotsget(List<ComponentData> cotsget) {
		this.cotsget = cotsget;
	}
	public UserInteractionData getInteraction() {
		return interaction;
	}
	public void setInteraction(UserInteractionData interaction) {
		this.interaction = interaction;
	}
}
