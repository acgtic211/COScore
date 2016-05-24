/*
 * IMM.java -- Módulo de Gestión de Interacción.
 * Copyright (C) 2016  Alfredo Valero Rodríguez and Javier Criado Rodríguez
 *
 * IMM.java is part of COScore Community.
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
package es.ual.acg.cos.modules;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.eclipse.emf.teneo.hibernate.HbDataStore;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;

import ccmm.ConcreteComponentSpecification;
import es.ual.acg.cos.controllers.ManageArchitectures;
import es.ual.acg.cos.controllers.ManageInteraction;
import es.ual.acg.cos.controllers.ManageJava;
import es.ual.acg.cos.controllers.ManageWookie;
import es.ual.acg.cos.java.JavaComponentResponse;
import es.ual.acg.cos.types.ComponentData;
import es.ual.acg.cos.types.InterModulesData;
import es.ual.acg.cos.wookie.WidgetData;
import es.ual.acg.cos.ws.types.CreateUserResult;
import es.ual.acg.cos.ws.types.DefaultInitSessionResult;
import architectural_metamodel.Architectural_metamodelFactory;
import architectural_metamodel.ConcreteArchitecturalModel;
import architectural_metamodel.ConcreteComponent;
import architectural_metamodel.RuntimeProperty;

@Stateful 
public class IMM {
	private static final Logger LOGGER = Logger.getLogger(IMM.class);
	
	public InterModulesData registerinteraction(String newSession, String deviceType, String interactionType, String dateTime, String userId,
			String latitude, String longitude, String operationPerformed, String componentId, List<String> groupComponent, List<String> ungroupComponent, 
			List<ComponentData> cotsget){
		
		Context initialContext;
		InterModulesData result = new InterModulesData();
		result.setValue("-1");
		try {
			initialContext = new InitialContext();
		    ManageInteraction mi = (es.ual.acg.cos.controllers.ManageInteraction)initialContext.lookup("java:app/cos/ManageInteraction");

		    mi.insertInteraction(newSession, deviceType, interactionType, dateTime, userId,
					latitude, longitude, operationPerformed, componentId, groupComponent, ungroupComponent, cotsget);
			result.setValue("1");
			result.setMessage("> Registered Information Sucessfully");
			    
		} catch (SQLException e){
			LOGGER.error(e);
			result.setMessage("> " + e);
		} catch (Exception e) {
			LOGGER.error(e);
			result.setMessage("> Internal Server Error");
		}
		return result;
	}
}
