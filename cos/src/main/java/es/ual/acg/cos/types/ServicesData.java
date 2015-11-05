package es.ual.acg.cos.types;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="ServicesData")
@XmlType(propOrder = {"componentname", "componentalias", "instanceId", "mapaKML", "capa"})
public class ServicesData {
	private String componentname; //Nombre del componente. Si el servicio es el servicio base coincide con el componentData.componentname
	private String componentalias; //Alias del componente. Si el servicio es el servicio base coincide con el componentData.componentalias
	private String instanceId; //Instancia del componente. Si el servicio es el servicio base coincide con el componentData.InstanceId
	private String mapaKML;   //Mapa del servicio OGC cargado
	private String capa; //Capa del servicio OGC cargado


	public ServicesData() {
		this.componentname = null;
		this.componentalias = null;
		this.instanceId = null;
		this.mapaKML = null;
		this.capa = null;
	}
	
	public ServicesData(String componentname, String componetalias, String instanceId,
			String mapaKML, String capa) {
		this.componentname = componentname;
		this.componentalias = componetalias;
		this.instanceId = instanceId;
		this.mapaKML = mapaKML;
		this.capa = capa;
	}
	public String getComponentname() {
		return componentname;
	}
	public void setComponentname(String componentname) {
		this.componentname = componentname;
	}
	public String getComponentalias() {
		return componentalias;
	}
	public void setComponentalias(String componentalias) {
		this.componentalias = componentalias;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getMapaKML() {
		return mapaKML;
	}
	public void setMapaKML(String mapaKML) {
		this.mapaKML = mapaKML;
	}
	public String getCapa() {
		return capa;
	}
	public void setCapa(String capa) {
		this.capa = capa;
	}

}


