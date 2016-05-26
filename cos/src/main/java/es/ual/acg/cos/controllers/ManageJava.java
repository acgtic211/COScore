/*
 * ManageJava.java -- Gestiona la base de datos de componentes e instancias de tipo Java.
 * Copyright (C) 2016  Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * ManageJava.java is part of COScore Community.
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
 *  Authors: Javier Criado Rodríguez   Doctor/Researcher/Software Developer
 *           Jesús Vallecillos Ruíz    Pre-doctoral scholarship holders/Researcher/Software Developer
 *    Group: ACG 		               Applied Computing Group
 * Internet: http://acg.ual.es/        
 *   E-mail: acg.tic211@ual.es        
 *   Adress: Edif. Científico Técnico, CITE-III
 *           Universidad de Almería
 *           Almeria, España
 *           04120
*/
package es.ual.acg.cos.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import org.jboss.logging.Logger;

import es.ual.acg.cos.java.JavaComponentResponse;
import es.ual.acg.cos.java.JavaComponentsApplication;

@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Singleton
@Lock(LockType.READ)
public class ManageJava {

	private static final Logger LOGGER = Logger.getLogger(ManageJava.class);
	
	@PostConstruct
	public void initManageJava() {
		LOGGER.info("[ManageJava] EJB initializated (Singleton mode)");
	}
	
	public JavaComponentResponse getOrCreateJavaInstance(String userID, String componentJavaID, String componentJavaName) {
	  JavaComponentsApplication jca = new JavaComponentsApplication(); 

	  JavaComponentResponse o = jca.getComponent("localhost", 4445, "localhost", 8888, userID, "applicationId", 
			  																		componentJavaID, componentJavaName);
	  
	  /*JavaComponentClient jcr = new JavaComponentClient();
	  JavaComponentResponse o = jcr.getOrCreateJavaInstance(userID, componentJavaID, componentJavaName);*/
	  
	  return o;
	}
	
	/*private String makeInstanceId(String userId, String componentId, String componentName){
	String platform = queryComponentPlatform(componentId);
	String instanceId = null;
	try{
		if(platform.equalsIgnoreCase("Web")){
			ManageWookie wookie = new ManageWookie();

			WidgetData widgetData = wookie.getOrCreateWidgetInstance(userId, componentId, componentName);
			
			instanceId = widgetData.getIdentifier();
			
		}else{
			if(platform.equalsIgnoreCase("Java")){
				ManageJava manageJava = new ManageJava();

				JavaComponentResponse r = manageJava.getOrCreateJavaInstance(userId, componentId, componentName);

				instanceId = r.getComponentInstanceName();
			}
			
		}
	} catch (Exception e) {
		LOGGER.error(e);
	}
	return instanceId;
	}*/
}
