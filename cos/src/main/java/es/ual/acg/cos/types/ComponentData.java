/*
 * ComponentData.java -- Estructura que almacena un conjunto de propiedades para cada componente de la arquitectura
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * ComponentData.java is part of COScore Community.
 * 
 * COScore Community is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/gpl.html> or
 * see  <https://github.com/acgtic211/COScore-Community>.  Or write to
 * the Free Software Foundation, I51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1335, USA.
 *
 *  Authors: Alfredo Valero Rodríguez  Software Developer 
 *           Javier Criado Rodríguez   Doctor/Researcher/Software Developer
 *           Jesús Vallecillos Ruíz    Pre-doctoral scholarship holders/Researcher/Software Developer
 *    Group: ACG 		               Applied Computing Group
 * Internet: http://acg.ual.es/        
 *   E-mail: acg.tic211@ual.es        
 *   Adress: Edif. Científico Técnico, CITE-III
 *           Universidad de Almería
 *           Almeria, España
 *           04120
*/
package es.ual.acg.cos.types;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="ComponentData")
@XmlType(propOrder = {"platform", "componentname", "componentAlias", "instanceId", "codeHTML", "objectJava", 
					"jarJava", "idHtml", "posx", "posy", "tamanox", "tamanoy", "servicio_maximizable", "servicio_agrupable", 
					"numero_servicios", "servicios"})
public class ComponentData {

	private String platform; //Plataforma a la que esta sirviendo web, java, ...  *(Almacenado en BD de especificación del componente concreto)
	private String componentname; //Nombre del componente (antiguo componentID) EJ: http://acg.ual.es/wookie/widgets/OGC-RENPA-EENNPP *(Almacenado en BD de especificación del componente concreto  y BD especificacion de arquitectura concreta)
	private String componentAlias; //Alias de la instancia componente EJ: OGC-RENPA-EENNPP2334  *(Almacenado en BD de especificación del componente concreto y BD especificacion de arquitectura concreta)
	private String instanceId; //Instancia del componente EJ: '0H2Y40tUByKUPvgkVITSFT9CvMY.eq.' *(Almacenada en BD especificacion de arquitectura concreta)
	private String codeHTML;   //Codigo html que incluye el iframe *(Se calcula al obtener instancia y es una RuntimeProperty invariable)
	private String objectJava; //Objeto serializado *(Se calcula al obtener instancia) 
	private String jarJava;	//Nombre del archivo jar que contiene el objeto java *(Se calcula al obtener instancia)
	private String idHtml;	//Identificador del elemento html en el DOM EJ: <div id="COTSget1"></div> *(Lo proporciona el usuario y es RuntimeProperty)
	private int posx;		//Posicion del componente que indica en que columna está *(Lo proporciona el usuario y es RuntimeProperty)
	private int posy;		//Posicion del componente que indica en que fila está *(Lo proporciona el usuario y es RuntimeProperty)
	private int tamanox;	//Tamaño que ocupa a lo ancho *(Almacenado en BD de especificación del componente concreto)
	private int tamanoy;	//Tamaño que ocupa a lo alto *(Almacenado en BD de especificación del componente concreto)
	private String servicio_maximizable;	//Determina si el componente puede verse a pantalla completa *(Almacenado en BD de especificación del componente concreto)
	private String servicio_agrupable;	//Determina si ese componente puede agrupar servicios *(Almacenado en BD de especificación del componente concreto)
	private int numero_servicios;	//Numero de servicios en este componente *(Lo proporciona el usuario y es RuntimeProperty)
	private List<ServicesData> servicios; //Lista con los servicios cargados en ese COTSget *(Lo proporciona el usuario y es RuntimeProperty, excepto mapa y capa)
	
	public ComponentData(){
		this.platform = null;
		this.componentname = null;
		this.componentAlias = null;
		this.instanceId = null;
		this.codeHTML = null;
		this.objectJava = null;
		this.jarJava = null;
		this.idHtml = null;
		this.posx = -1;
		this.posy = -1;
		this.tamanox = -1;
		this.tamanoy = -1;
		this.servicio_maximizable = "false";
		this.servicio_agrupable = "false";
		this.numero_servicios = -1;
		this.servicios = null;
	}

	public ComponentData(String platform, String componentname, String componentAlias,
			String instanceId, String codeHTML, String objectJava,
			String jarJava, String idHtml, int posx, int posy, int tamanox,
			int tamanoy,  String  servicio_maximizable,
			 String  servicio_agrupable, int numero_servicios,
			List<ServicesData> servicios) {

		this.platform = platform;
		this.componentname = componentname;
		this.componentAlias = componentAlias;
		this.instanceId = instanceId;
		this.codeHTML = codeHTML;
		this.objectJava = objectJava;
		this.jarJava = jarJava;
		this.idHtml = idHtml;
		this.posx = posx;
		this.posy = posy;
		this.tamanox = tamanox;
		this.tamanoy = tamanoy;
		this.servicio_maximizable = servicio_maximizable;
		this.servicio_agrupable = servicio_agrupable;
		this.numero_servicios = numero_servicios;
		this.servicios = servicios;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getComponentname() {
		return componentname;
	}

	public void setComponentAlias(String componentAlias) {
		this.componentAlias = componentAlias;
	}
	
	public String getComponentAlias() {
		return componentAlias;
	}

	public void setComponentname(String componentname) {
		this.componentname = componentname;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getCodeHTML() {
		return codeHTML;
	}

	public void setCodeHTML(String codeHTML) {
		this.codeHTML = codeHTML;
	}

	public String getObjectJava() {
		return objectJava;
	}

	public void setObjectJava(String objectJava) {
		this.objectJava = objectJava;
	}

	public String getJarJava() {
		return jarJava;
	}

	public void setJarJava(String jarJava) {
		this.jarJava = jarJava;
	}

	public String getIdHtml() {
		return idHtml;
	}

	public void setIdHtml(String idHtml) {
		this.idHtml = idHtml;
	}

	public int getPosx() {
		return posx;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}

	public int getTamanox() {
		return tamanox;
	}

	public void setTamanox(int tamanox) {
		this.tamanox = tamanox;
	}

	public int getTamanoy() {
		return tamanoy;
	}

	public void setTamanoy(int tamanoy) {
		this.tamanoy = tamanoy;
	}

	public String getServicio_maximizable() {
		return servicio_maximizable;
	}

	public void setServicio_maximizable(String servicio_maximizable) {
		this.servicio_maximizable = servicio_maximizable;
	}

	public String getServicio_agrupable() {
		return servicio_agrupable;
	}

	public void setServicio_agrupable(String servicio_agrupable) {
		this.servicio_agrupable = servicio_agrupable;
	}

	public int getNumero_servicios() {
		return numero_servicios;
	}

	public void setNumero_servicios(int numero_servicios) {
		this.numero_servicios = numero_servicios;
	}

	public List<ServicesData> getServicios() {
		return servicios;
	}

	public void setServicios(List<ServicesData> servicios) {
		this.servicios = servicios;
	}
	

	
}
