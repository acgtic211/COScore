package es.ual.acg.cos.ws.types;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"gotten","portList","message"})
@XmlAccessorType(XmlAccessType.NONE)
public class GetLinksResult {

	@XmlElement(required=true) 
	private boolean gotten;
	@XmlElement(required=true) 
	private String portList;
	@XmlElement(required=true) 
	private String message;
	
	public boolean isGotten() {
		return gotten;
	}
	public void setGotten(boolean gotten) {
		this.gotten = gotten;
	}
	public String getPortList() {
		return portList;
	}
	public void setPortList(String portList) {
		this.portList = portList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
