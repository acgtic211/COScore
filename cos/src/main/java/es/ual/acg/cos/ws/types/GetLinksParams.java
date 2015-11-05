package es.ual.acg.cos.ws.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"userId", "componentInstance", "portId"})
@XmlAccessorType(XmlAccessType.NONE)
public class GetLinksParams {

	@XmlElement(required=true) 
	private String userId;
	@XmlElement(required=true) 
	private String componentInstance;
	@XmlElement(required=true) 
	private String portId;
	
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
	public String getPortId() {
		return portId;
	}
	public void setPortId(String portId) {
		this.portId = portId;
	}
	
}
