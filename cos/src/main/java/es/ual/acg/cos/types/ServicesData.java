/*
 * ServicesData.java -- Estructura que almacena un conjunto de propiedades para cada uno de los servicios de un determinado componente
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * ServicesData.java is part of COScore Community.
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


