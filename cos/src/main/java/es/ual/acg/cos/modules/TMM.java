/*
 * TMM.java -- Módulo de Gestión de Transacción.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * TMM.java is part of COScore Community.
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
package es.ual.acg.cos.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.teneo.hibernate.HbDataStore;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;

import es.ual.acg.cos.controllers.ManageArchitectures;
import es.ual.acg.cos.types.ComponentData;
import es.ual.acg.cos.types.InterModulesData;
import es.ual.acg.cos.types.UserInteractionData;
import es.ual.acg.cos.ws.types.GetLinksResult;
import es.ual.acg.cos.ws.types.UpdateArchitectureResult;
import architectural_metamodel.Connector;
import architectural_metamodel.ConcreteArchitecturalModel;
import architectural_metamodel.ConcreteComponent;
import architectural_metamodel.OutputPort;
import architectural_metamodel.Port;

@Stateful
public class TMM {

	private ConcreteArchitecturalModel cam;
	
	private Map<String, List<String>> routingTable;
	
	private static final Logger LOGGER = Logger.getLogger(TMM.class);
	
	private HbDataStore dataStore;

	public GetLinksResult createRountingTable(String userID) {

		LOGGER.info("[TMM] Creating Rounting Table...");

		routingTable = new HashMap<String, List<String>>();
		String camID = "-1";
		InterModulesData resultUIM = new InterModulesData();
		GetLinksResult result = new GetLinksResult();
		Context initialContext;
		
		try{
			initialContext = new InitialContext();
			UIM uim = (es.ual.acg.cos.modules.UIM)initialContext.lookup("java:app/cos/UIM");

			resultUIM = uim.queryCamUser(userID);

			if (!resultUIM.getValue().equalsIgnoreCase("-1")){ //Sino hay error en cam
				camID = resultUIM.getValue();
				result.setGotten(true);
			}else{ //Errores producidos en UIM y en ManagerUSer
				result.setGotten(false);
				result.setMessage(resultUIM.getMessage());
			}
		} catch (Exception e) {
			LOGGER.error(e);
			result.setGotten(false);
			result.setMessage("> Internal Server Error " + e);
		}
		
		try{

			if (result.isGotten()){

				//Leemos el modelo d la base de datos
				initialContext = new InitialContext();
				InterModulesData resultDMM = new InterModulesData();
				try {
					DMM dmm = (es.ual.acg.cos.modules.DMM)initialContext.lookup("java:app/cos/DMM");
					resultDMM = dmm.ReadModelforcamId(camID);
					cam = resultDMM.getCam();
						
				} catch (Exception e) {
					LOGGER.error(e);
					result.setGotten(false);
					LOGGER.error("Profile no exist!");
					result.setMessage(resultDMM.getMessage());
				}
				
				if(cam != null){
				EList<ConcreteComponent> comps = cam.getConcreteComponent();
				LOGGER.info("COMPONENTS SIZE: " + comps.size());
	
				for(int i = 0; i < comps.size(); i++) {
					ConcreteComponent cc = comps.get(i);
					
					String componentInstance = cc.getComponentInstance();
					//String cID = cc.getComponentAlias();
					//LOGGER.info("-COMPONENT---ID: " + cID);
					LOGGER.info("-COMPONENT---ID: " + componentInstance);
					LOGGER.info("-COMPONENT---NAME: " + cc.getComponentName());
						
					EList<Port> ports = cc.getPort();
					for(int j = 0; j < ports.size(); j++) {
						
						Port puerto = ports.get(j);
						String componentport = puerto.getPortID();
						LOGGER.info("-PORT: " + componentport + " of COMPONENT " + componentInstance);
						
						String key = componentInstance + "," + componentport;
						
						List<String> portList = new ArrayList<String>();
						if(puerto instanceof OutputPort){

							EList<Connector> connectors = ((OutputPort) puerto).getCSource(); 
							
							for (int k = 0; k < connectors.size(); k++) {							
								Connector c = connectors.get(k);

								String connectorID = c.getConnectorID();
								LOGGER.info("---CONNECTOR ID: " + connectorID + " of PORT " + componentport);
		
								String connectionID = c.getTarget().getCc().getComponentInstance()+ ","+ c.getTarget().getPortID();
								
								LOGGER.info("---LINK -> SOURCE: " + key + " -- TARGET: " + connectionID);
								
								portList.add(connectionID);
							}
							
							routingTable.put(key, portList);
						}
					}
				}
			}
		}else{
			LOGGER.error("> Error in Architectural Models DB ");
			result.setGotten(false);
			result.setMessage("> Error in Architectural Models DB ");
		}
			//result.setMessage("[TMM] Rounting Table created");
			LOGGER.info("[TMM] Rounting Table created");
			
		} catch (Exception e) {
			LOGGER.error(e);
			result.setGotten(false);
			result.setMessage("> Error in Architectural Models DB " + e);
		}
		return result;
	}

	public GetLinksResult calculateConnectedPorts(String userID, String componentInstance, String portID) {
		GetLinksResult result = new GetLinksResult();
		Context initialContext;

		try	{
			initialContext = new InitialContext();
			COSSessionMM cossmng = (COSSessionMM)initialContext.lookup("java:app/cos/COSSessionMM");

			if(cossmng.isUserOnUserEJBMaps(userID) == true){
	
				cossmng.setTime(userID);
				
				result = this.createRountingTable(userID);
				
				String key = componentInstance + "," + portID;
				LOGGER.info("CALCULATE - Key: " + key);
				
				showRoutingTable();

				List<String> portList = routingTable.get(key);
				LOGGER.info("Component: " + componentInstance + " - Port LIST: " + portList);
				
		    	if(portList==null || portList.size() < 1){
		    		result.setGotten(false);
		    		result.setPortList(null);
					result.setMessage("> Not found o Empty Port Error");
		    	}else {
		    		String portListString = portList.get(0);
		    		for(int i = 1; i < portList.size(); i++){
		    			portListString += "-" + portList.get(i);
		    		}
		    		result.setGotten(true);
	    			result.setPortList(portListString);
					result.setMessage("> List of Ports Sucessfully");
		    	}
			}else{
				result.setGotten(false);
				result.setMessage("> Internal Server Error");

			}
		} catch (NamingException e) {
			LOGGER.error(e);
			result.setGotten(false);
			result.setMessage("> Internal Server Error");
		}
			
    	return result;
	}
	
	@Lock(LockType.READ)
	private void showRoutingTable()	{
		
		LOGGER.info("\n\n");
		LOGGER.info("                        ----- [TMM] SHOW Routing Table -----");

		Iterator<Map.Entry<String,List<String>>> it = routingTable.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<String, List<String>> entry = it.next();
			String key = entry.getKey();
			LOGGER.info("--> " + key);
			List<String> valueList = entry.getValue();
			for(String value : valueList)
				LOGGER.info("----o " + value);
		}
		LOGGER.info("------------------------------------------------------------------------------------------------");
		LOGGER.info("\n\n");
	}


	public void setCAM(ConcreteArchitecturalModel cam){
		this.cam = cam;
	}
	
}
