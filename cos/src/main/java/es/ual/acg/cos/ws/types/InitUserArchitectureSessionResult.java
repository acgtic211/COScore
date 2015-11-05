package es.ual.acg.cos.ws.types;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import es.ual.acg.cos.types.ComponentData;

@XmlType(propOrder = {"init", "componentData", "message"})
@XmlAccessorType(XmlAccessType.NONE)
public class InitUserArchitectureSessionResult {

	@XmlElement(required=true) 
	private boolean init;
	@XmlElement(required=true) 
	private List<ComponentData> componentData;
	@XmlElement(required=true) 
	private String message;
	
	public boolean isInit() {
		return init;
	}
	public void setInit(boolean init) {
		this.init = init;
	}
	public List<ComponentData> getComponentData() {
		return componentData;
	}
	public void setComponentData(List<ComponentData> componentData) {
		this.componentData = componentData;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}