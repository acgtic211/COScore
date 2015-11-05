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

	public void initialize() {

		if(dataStore == null) {
			LOGGER.info("[TMM] Getting DataStore...");
			this.getDataStoreFromManageDB();
			LOGGER.info("[TMM] DataStore has been got");
		}
		else
			LOGGER.info("[TMM] DataStore is already got");
		
		//this.createRountingTable();
	}
	

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
			initialContext = new InitialContext();
			ManageArchitectures ma = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");

			if (result.isGotten()){
				cam = ma.readModel(camID);
		
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

//	@Lock(LockType.READ)
//	public void deleteComponent(String idInstance) {
//		LOGGER.info("[TMM] Delete Instance: " + idInstance);
//
//		for(int i = 0; i < cam.getConcreteComponent().size(); i++) {
//			ConcreteComponent cc = cam.getConcreteComponent().get(i);
//			
//			LOGGER.info("-COMPONENT - NAME: " + cc.getComponentName());
//			
//			if((cc.getComponentAlias()).equalsIgnoreCase(idInstance)){
//				cam.getConcreteComponent().remove(i);
//				LOGGER.info("DELETE - Component: " + cc.getComponentAlias());
//			}
//		}
//	}
	
	public void getDataStoreFromManageDB() {
		
		dataStore = null;
		
		ManageArchitectures managArch = null;
		Context initialContext;
		try
		{
			initialContext = new InitialContext();
			
			managArch = (ManageArchitectures)initialContext.lookup("java:module/ManageArchitectures");
			dataStore = managArch.getDataStore();
		}
		catch (Exception e) {
			LOGGER.error(e);
		}
		
		if(dataStore == null)
			LOGGER.info("[TMM] Error getting the DataStore");
		
	}
	
//	public ConcreteArchitecturalModel getCAM(){
//		//PRUEBA DE CREACIÓN DE TABLA DE RUTAS
//		SessionFactory sessionFactory = dataStore.getSessionFactory();
//
//		//Open a new Session
//		Session session = sessionFactory.openSession();
//		  
//		//Start transaction
//		session.beginTransaction();
//		
//		Query query = session.createQuery("FROM ConcreteArchitecturalModel WHERE camID = '" + cam.getCamID() + "'");
//
//		List<?> cams = query.list();
//		cam = (ConcreteArchitecturalModel) cams.get(0);
//		
//		//Commit the changes to the database.
//		session.getTransaction().commit();
//		  
//		//Close the session.
//		session.close();
//		
//		return cam;
//	}
	

	public void setCAM(ConcreteArchitecturalModel cam){
		this.cam = cam;
	}
	
}
