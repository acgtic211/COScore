/*
 * UpdateArchitectureParams.java -- Estructura con los parametos de entrada de la operacion UpdateArchitecture.
 * Copyright (C) 2016  Alfredo Valero Rodríguez and Javier Criado Rodríguez
 *
 * UpdateArchitectureParams.java is part of COScore Community.
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
package es.ual.acg.cos.ws.types;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import es.ual.acg.cos.types.ComponentData;
import es.ual.acg.cos.types.UserInteractionData;

@XmlType(propOrder = {"userId","componentInstance","actionDone","newsComponentData","interaction"})
@XmlAccessorType(XmlAccessType.NONE)
public class UpdateArchitectureParams {
	@XmlElement(required=true) 
	private String userId;
	@XmlElement(required=false)	
	private String componentInstance;
	@XmlElement(required=true)
	private String actionDone;
	@XmlElement(required=true)
	private List<ComponentData> newsComponentData;
	@XmlElement(required=true)
	private UserInteractionData interaction;
	
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
	public String getActionDone() {
		return actionDone;
	}
	public void setActionDone(String actionDone) {
		this.actionDone = actionDone;
	}
	public List<ComponentData> getNewsComponentData() {
		return newsComponentData;
	}
	public void setNewsComponentData(List<ComponentData> newsComponentData) {
		this.newsComponentData = newsComponentData;
	}
	public UserInteractionData getInteraction() {
		return interaction;
	}
	public void setInteraction(UserInteractionData interaction) {
		this.interaction = interaction;
	}
}
