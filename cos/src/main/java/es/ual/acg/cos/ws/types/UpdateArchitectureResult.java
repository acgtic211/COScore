package es.ual.acg.cos.ws.types;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import es.ual.acg.cos.types.ComponentData;

@XmlType(propOrder = {"allowed", "oldComponentData", "message"})
@XmlAccessorType(XmlAccessType.NONE)
public class UpdateArchitectureResult {

		@XmlElement(required=true) 
		private boolean allowed;
		@XmlElement(required=true) 
		private List<ComponentData> oldComponentData;
		@XmlElement(required=true) 
		private String message;
		
		public boolean isAllowed() {
			return allowed;
		}
		public void setAllowed(boolean allowed) {
			this.allowed = allowed;
		}
		public List<ComponentData> getOldComponentData() {
			return oldComponentData;
		}
		public void setOldComponentData(List<ComponentData> oldComponentData) {
			this.oldComponentData = oldComponentData;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	
}
