/*
 * UserInteractionData.java -- Estructura que almacena un conjunto de propiedades relacionadas con la interacción del usuario
 * Copyright (C) 2016  Alfredo Valero Rodríguez and Javier Criado Rodríguez
 *
 * UserInteractionData.java is part of COScore Community.
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

@XmlRootElement(name="UserInteractionData")
@XmlType(propOrder = {"deviceType", "interactionType", "latitude", "longitude"})
public class UserInteractionData {
	private String deviceType; //Tipo de dispositivo desde el que se está accediendo (Browser,Phone,Tablet,TV).
	private String interactionType; //Tipo de interacción de entrada (MouseKeyboard, Voice, Gestural, Touch).
	private String latitude; //Latitud, información geográfica que pueda proporcionar el dispositivo. 
	private String longitude;   //Latitud, información geográfica que pueda proporcionar el dispositivo. 


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