package es.ual.acg.cos.types;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="UserInteractionData")
@XmlType(propOrder = {"deviceType", "interactionType", "latitude", "longitude"})
public class UserInteractionData {
	private String deviceType; //Nombre del componente. Si el servicio es el servicio base coincide con el componentData.componentname
	private String interactionType; //Alias del componente. Si el servicio es el servicio base coincide con el componentData.componentalias
	private String latitude; //Instancia del componente. Si el servicio es el servicio base coincide con el componentData.InstanceId
	private String longitude;   //Mapa del servicio OGC cargado


	public UserInteractionData() {
		this.deviceType = null;
		this.interactionType = null;
		this.latitude = null;
		this.longitude = null;
	}
	
	public UserInteractionData(String deviceType, String interactionType, String latitude, String longitude) {
		this.deviceType = deviceType;
		this.interactionType = interactionType;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getInteractionType() {
		return interactionType;
	}

	public void setInteractionType(String interactionType) {
		this.interactionType = interactionType;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


}