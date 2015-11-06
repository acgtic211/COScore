package es.ual.acg.cos.modules;

import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PreDestroy;
import javax.ejb.PrePassivate;
import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.teneo.hibernate.HbDataStore;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;

import ccmm.ConcreteComponentSpecification;
import es.ual.acg.cos.controllers.ManageArchitectures;
import es.ual.acg.cos.controllers.ManageDB;
import es.ual.acg.cos.controllers.ManageUsers;
import es.ual.acg.cos.controllers.ManageWookie;
import es.ual.acg.cos.wookie.WidgetData;
import architectural_metamodel.Architectural_metamodelFactory;
import architectural_metamodel.Binary;
import architectural_metamodel.ComponentType;
import architectural_metamodel.ConcreteArchitecturalModel;
import architectural_metamodel.ConcreteComponent;
import architectural_metamodel.ConcreteDependency;
import architectural_metamodel.Connector;
import architectural_metamodel.InputPort;
import architectural_metamodel.Nary;
import architectural_metamodel.OutputPort;
import architectural_metamodel.Port;
import architectural_metamodel.impl.ConcreteComponentImpl;
import architectural_metamodel.impl.InputPortImpl;

@Stateful
public class LRMM {
	
	private HbDataStore dataStore;
	private HbDataStore dataStoreCC;
	private ConcreteArchitecturalModel cam;
	private static final Logger LOGGER = Logger.getLogger(LRMM.class);
	
//	@PrePassivate
//	public void prePassivate(){
//	  LOGGER.info("[LRMM] PrePassivate method called");
//	}
//
//	@PreDestroy
//	public void preDestry() {
//	  LOGGER.info("[LRMM] PreDestroy method called ");
//	}
//	
//	public void initialize(String userID) {
//		try {
//			if(dataStore == null) {
//				LOGGER.info("[LRMM] Getting DataStore...");
//				LOGGER.info("[LRMM] Getting DataStore...");
//				this.getDataStoreFromManageDB();
//				this.getDataStoreCCFromManageDB();
//				LOGGER.info("[LRMM] DataStore has been got");
//			}
//			else
//				LOGGER.info("[LRMM] DataStore is already got");
//			
//			LOGGER.info("[LRMM] Creating CAM...");
//			ManageUsers umc = new ManageUsers(); 
//			
//			this.readCAM(userID, umc.queryCamUser(userID));
//			LOGGER.info("[LRMM] CAM has been created");
//			
//			LOGGER.info("[LRMM] Module Started");
//
//		} catch (SQLException e){
//			LOGGER.error(e);
//		} catch (ClassNotFoundException e){
//			LOGGER.error(e);
//		}
//	}
//
//	public void updateInstanceNames() {
//		SessionFactory sessionFactory = dataStore.getSessionFactory();
//		//Open a new Session
//		Session session = sessionFactory.openSession();
//		//Start transaction
//		session.beginTransaction();
//
//		Query query = session.createQuery("FROM ConcreteArchitecturalModel WHERE camID = '" + cam.getCamID() + "'");
//
//		List<?> cams = query.list();
//		ConcreteArchitecturalModel camAux = (ConcreteArchitecturalModel) cams.get(0);
//		LOGGER.info("CAM ID: " + cam.getCamID());
//
//		//Update all component names
//		for(int i = 0; i < cam.getConcreteComponent().size(); i++){
//			ConcreteComponent cc = camAux.getConcreteComponent().get(i);
//			cc.setComponentName(cam.getConcreteComponent().get(i).getComponentName());
//			session.save(cc);
//		}
//		session.save(camAux);
//		
//		//Commit the changes to the database.
//		session.getTransaction().commit();
//				  
//		//Close the session.
//		session.close();
//	}
//	
//	public String deleteComponent(String ccId) {
//		// Result is the instance id
//		StringTokenizer st = new StringTokenizer(ccId, ","); st.nextToken(); 
//		String result = st.nextToken();
//		
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
//
//		cam = (ConcreteArchitecturalModel) cams.get(0);
//		LOGGER.info("[LRMM] Delete CAM ID: " + cam.getCamID());
//
//		try{
//			//Search the component to be deleted
//			boolean found = false;
//			for(int i = 0; i < cam.getConcreteComponent().size() && found == false; i++){
//				LOGGER.info("ITEM(" + i + "): " + cam.getConcreteComponent().get(i).getComponentName());
//				if(cam.getConcreteComponent().get(i).getComponentName().equalsIgnoreCase(ccId)){
//					LOGGER.info("CC NAME to intro: " + cam.getConcreteComponent().get(i).getComponentName());
//					
//					ConcreteComponent cc = cam.getConcreteComponent().get(i);
//					
//					//Delete component source
//					LOGGER.info("CC NAME --- SIZE bSOURCE: " + cc.getBSource().size());
//					while(cc.getBSource().size() > 0) {
//						LOGGER.info("        --- POS(" + (0) + ") bSOURCE id: " + cc.getBSource().get(0).getRelationshipID() + "   --   " + cc.getBSource().size());
//						String idRelationship = cc.getBSource().get(0).getRelationshipID();
//						cc.getBSource().remove(0);						
//						
//						for(int k = 0; k < cam.getConcreteComponent().size(); k++) {
//							LOGGER.info("        -----&----- ConcreteComponent: " + cam.getConcreteComponent().get(k).getComponentName());
//							for(int p = 0; p < cam.getConcreteComponent().get(k).getBSource().size(); p++)
//								if(cam.getConcreteComponent().get(k).getBSource().get(p).getRelationshipID().equalsIgnoreCase(idRelationship))
//									cam.getConcreteComponent().get(k).getBSource().remove(p);
//							
//							
//							for(int p = 0; p < cam.getConcreteComponent().get(k).getBTarget().size(); p++)
//								if(cam.getConcreteComponent().get(k).getBTarget().get(p).getRelationshipID().equalsIgnoreCase(idRelationship))
//									cam.getConcreteComponent().get(k).getBTarget().remove(p);
//							
//						}
//						
//						for(int z = 0; z < cam.getRelationship().size(); z++) {
//							if(cam.getRelationship().get(z).getRelationshipID().equalsIgnoreCase(idRelationship)){
//								Binary b = (Binary) cam.getRelationship().get(z);
//								
//								// Delete nary relationship
//								if(((Binary) cam.getRelationship().get(z)).getNaryRelationship() != null){
//									Nary naryRelationship = ((Binary) cam.getRelationship().get(z)).getNaryRelationship();
//									cam.getRelationship().remove((Nary) naryRelationship);
//								}
//								
//								cam.getRelationship().remove(b);
//								z = 0;
//							}
//						}
//					}
//					
//										
//					//Delete component target
//					LOGGER.info("CC NAME --- SIZE bTARGET: " + cc.getBTarget().size());
//					while(cc.getBTarget().size() > 0) {
//						LOGGER.info("        --- POS(" + (0) + ") bTARGET id: " + cc.getBTarget().get(0).getRelationshipID());
//						String idRelationship = cc.getBTarget().get(0).getRelationshipID();
//						cc.getBTarget().remove(0);
//						
//						for(int k = 0; k < cam.getConcreteComponent().size(); k++) {
//							
//							for(int p = 0; p < cam.getConcreteComponent().get(k).getBSource().size(); p++)
//								if(cam.getConcreteComponent().get(k).getBSource().get(p).getRelationshipID().equalsIgnoreCase(idRelationship)){
//									//Objective connector
//									ConcreteDependency cd = (ConcreteDependency) cam.getConcreteComponent().get(k).getBSource().get(p).getDependency().get(0);
//									EList<Connector> connectorList = cd.getConnector();
//
//									//Delete connector
//									for(int q = 0; q < cam.getConcreteComponent().get(k).getPort().size(); q++){
//										if((cam.getConcreteComponent().get(k).getPort().get(q)) instanceof OutputPort){
//											OutputPort port = (OutputPort) cam.getConcreteComponent().get(k).getPort().get(q);
//											for(int d = 0; d < connectorList.size(); d++){
//												for(int t = 0; t < port.getCSource().size(); t++)
//													if(connectorList.get(d).getConnectorID().equalsIgnoreCase(port.getCSource().get(t).getConnectorID())){
//														port.getCSource().remove(t);
//													}
//											}											
//										} else {
//											if((cam.getConcreteComponent().get(k).getPort().get(q)) instanceof InputPort){
//												InputPort port = (InputPort) cam.getConcreteComponent().get(k).getPort().get(q);
//												for(int d = 0; d < connectorList.size(); d++){
//													for(int t = 0; t < port.getCTarget().size(); t++)
//														if(connectorList.get(d).getConnectorID().equalsIgnoreCase(port.getCTarget().get(t).getConnectorID())) {
//															port.getCTarget().remove(t);
//														}
//												}
//											}
//										}
//									}									
//									
//								
//									
//									cam.getConcreteComponent().get(k).getBSource().remove(p);
//								}
//							
//							for(int p = 0; p < cam.getConcreteComponent().get(k).getBTarget().size(); p++)
//								if(cam.getConcreteComponent().get(k).getBTarget().get(p).getRelationshipID().equalsIgnoreCase(idRelationship)){
//									//Objective connector
//									ConcreteDependency cd = (ConcreteDependency) cam.getConcreteComponent().get(k).getBTarget().get(p).getDependency().get(0);
//									EList<Connector> connectorList = cd.getConnector();
//
//									//Delete connector
//									for(int q = 0; q < cam.getConcreteComponent().get(k).getPort().size(); q++){
//										if((cam.getConcreteComponent().get(k).getPort()) instanceof OutputPort){
//											OutputPort port = (OutputPort) cam.getConcreteComponent().get(k).getPort().get(q);
//											for(int d = 0; d < connectorList.size(); d++){
//												for(int t = 0; t < port.getCSource().size(); t++){
//													if(connectorList.get(d).getConnectorID().equalsIgnoreCase(port.getCSource().get(t).getConnectorID())){
//														port.getCSource().remove(t);
//													}
//												}
//											}											
//										} else{
//											InputPort port = (InputPort) cam.getConcreteComponent().get(k).getPort().get(q);
//											for(int d = 0; d < connectorList.size(); d++){
//												for(int t = 0; t < port.getCTarget().size(); t++)
//													if(connectorList.get(d).getConnectorID().equalsIgnoreCase(port.getCTarget().get(t).getConnectorID())){
//														port.getCTarget().remove(t);
//													}
//											}
//										}
//									}
//									
//									
//									cam.getConcreteComponent().get(k).getBTarget().remove(p);
//								}							
//						}
//						
//						for(int z = 0; z < cam.getRelationship().size(); z++) {
//							if(cam.getRelationship().get(z).getRelationshipID().equalsIgnoreCase(idRelationship)){
//								Binary b = (Binary) cam.getRelationship().get(z);								
//								cam.getRelationship().remove(b);
//								z = 0;
//							}
//						}
//						
//					}
//					
//					//session.save(cc);
//					LOGGER.info("COMPONENT TO DELETE: " + cam.getConcreteComponent().get(i).getComponentName());
//					cam.getConcreteComponent().remove(i);
//					
//					session.save(cam);
//					found = true;
//				}
//			}
//		}catch(Exception e){
//			result = null;
//			LOGGER.info("[LRMM] Error to delete component - " + ccId);
//			LOGGER.info(e.getMessage());
//		}
//		   
//		//Commit the changes to the database.
//		session.getTransaction().commit();
//		  
//		//Close the session.
//		session.close();
//		
//		return result;
//	}
//	
////	public String addComponent(String userID, String componentID){
////		SessionFactory sessionFactory = dataStore.getSessionFactory();
////		String componentName = null;
////		
////		try{
////			//Open a new Session
////			Session session = sessionFactory.openSession();
////			  
////			//Start transaction
////			session.beginTransaction();
////			
////			Query query = session.createQuery("FROM ConcreteArchitecturalModel WHERE camID = '" + cam.getCamID() + "'");
////	
////			List<?> cams = query.list();
////	
////			cam = (ConcreteArchitecturalModel) cams.get(0);
////			LOGGER.info("[LRMM] Delete CAM ID: " + cam.getCamID());
////			
////			//Initialize the component
////			//ConcreteComponent cc = new ConcreteComponentImpl();
////			ConcreteComponent cc = Architectural_metamodelFactory.eINSTANCE.createConcreteComponent();
////			
////			cc.setComponentName(componentID);
////			componentName = String.valueOf((int) Math.floor(Math.random()*1000));
////			cc.setComponentAlias(componentName);
////			cc.setComponentType(ComponentType.USER_INTERACTION);
////			
////			//Initialize the port 
////			//InputPort p = new InputPortImpl();
////			InputPort p = Architectural_metamodelFactory.eINSTANCE.createInputPort();
////			p.setPortID("inputport");
////			p.setCc(cc);
////			session.save(p);
////			
////			cc.getPort().add(p);
////			
////			session.save(cc);
////			
////			//Add the concrete component to concrete architectural model
////			cam.getConcreteComponent().add(cc);
////			
////			//Add instance to the component to the DB
////			if(queryComponentPlatform(componentID).equalsIgnoreCase("Web")){
////				ManageWookie wookie = new ManageWookie();				
////				WidgetData widgetData = wookie.getOrCreateWidgetInstance(userID, componentID, componentName);
////				setComponentId(componentID, widgetData.getIdentifier(), componentName ,cam);
////			}
////	
////			
////			//Save cam
////			session.save(cam);
////			
////			//Commit the changes to the database.
////			session.getTransaction().commit();
////					  
////			//Close the session.
////			session.close();
////
////		} catch (Exception e) {
////			LOGGER.error(e);
////		}
////		return componentName;
////	}
//	
//	private void setComponentId(String componentID, String instanceID, String componentName, ConcreteArchitecturalModel cam) {
//		boolean end = false;
//		
//		// Check this instance doesn't exist
//		for(int i = 0; i < cam.getConcreteComponent().size() && end == false; i++){
//			if((componentID + "," + instanceID).equalsIgnoreCase(cam.getConcreteComponent().get(i).getComponentName())){
//				end = true;
//			}
//		}
//		
//		for(int i = 0; i < cam.getConcreteComponent().size() && end == false; i++){
//			if(componentID.equalsIgnoreCase(cam.getConcreteComponent().get(i).getComponentName()) && componentName.equalsIgnoreCase(cam.getConcreteComponent().get(i).getComponentAlias())){
//				cam.getConcreteComponent().get(i).setComponentName(componentID + "," + instanceID);
//				end = true;
//			}
//		}
//
//	}
//	
//	private String queryComponentPlatform(String componentID) {
//		SessionFactory sessionFactory = dataStoreCC.getSessionFactory();
//		//Open a new Session
//		Session session = sessionFactory.openSession();
//		//Start transaction
//		session.beginTransaction();
//		Query query = session.createQuery("FROM ConcreteComponentSpecification "
//				+ "WHERE componentID = '" + componentID + "'");
//		List<?> ccs = query.list();
//
//		LOGGER.info("PlataformType -> " + ((ConcreteComponentSpecification) ccs.get(0)).getPackaging().
//																getImplementation().getPlatformType());
//		//Close the session.
//		session.close();
//		if(ccs.size() == 1)
//			return ((ConcreteComponentSpecification) ccs.get(0)).getPackaging().getImplementation().
//																			getPlatformType().toString();
//		else
//			return null;
//	}
//	
//	private void readCAM(String userID, String camID) {
//		
//		final SessionFactory sessionFactory = dataStore.getSessionFactory();
//		{
//			final Session session = sessionFactory.openSession();
//			session.beginTransaction();
//
//			// Retrieve all ConcreteArchitecturalModels and display their components.
//			//Query query = session.createQuery("FROM ConcreteArchitecturalModel WHERE camID = '" + c + "'");
//			Query query = session.createQuery("FROM ConcreteArchitecturalModel WHERE camID = '" + camID + "'");
//			//Query query = session.createQuery("FROM ConcreteArchitecturalModel");
//			List<?> cams = query.list();
//
//			cam = (ConcreteArchitecturalModel) cams.get(0);
//			LOGGER.info("[LRMM - readCAM] CAM ID: " + cam.getCamID());
//				
//			//Initialize the object
//			for(int j = 0; j < cam.getConcreteComponent().size(); j++)
//			{
//				try{
//					ConcreteComponent cc = cam.getConcreteComponent().get(j);
//					// Changed
//					Hibernate.initialize(cc);
//					for (Port p : cc.getPort()) {
//						if (p instanceof InputPort) {
//							for (Connector cT : ((InputPort) p).getCTarget())
//								Hibernate.initialize(cT);
//							//Hibernate.initialize(((InputPort) p).getCTarget());
//						} else {
//							for (Connector cS : ((OutputPort) p).getCSource())
//								Hibernate.initialize(cS);
//							//Hibernate.initialize(((OutputPort) p).getCSource());
//						}
//					}
//				}catch(Exception e){
//					LOGGER.error(e.getMessage());
//				}
//			}
//
//			LOGGER.info("[LRMM - readCAM] model queried and initialized");
//				
//			session.getTransaction().commit();
//			
//			//Close the session
//			session.close();
//		}
//	}
//
//	public void getDataStoreFromManageDB() {
//		
//		dataStore = null;
//		
//		ManageArchitectures managArch = null;
//		Context initialContext;
//		try	{
//			initialContext = new InitialContext();
//			
//			managArch = (ManageArchitectures)initialContext.lookup("java:module/ManageArchitectures");
//			dataStore = managArch.getDataStore();
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		if(dataStore == null)
//			LOGGER.info("[LRMM] Error getting the DataStore");
//		
//	}
//
//	public void getDataStoreCCFromManageDB() {
//		dataStoreCC = null;
//		
//		ManageDB managdb = null;
//		Context initialContext;
//		try
//		{
//			initialContext = new InitialContext();
//			
//			managdb = (ManageDB)initialContext.lookup("java:module/ManageDB");
//			dataStoreCC = managdb.getDataStoreCC();
//		}
//		catch (NamingException e) {
//			e.printStackTrace();
//		}
//		
//		if(dataStoreCC == null)
//			LOGGER.info("[LRMM] Error getting the DataStoreConcreteComponent");
//		
//	}
//
//	public void setCAM(ConcreteArchitecturalModel cam){
//		this.cam = cam;
//	}
//	
//	public ConcreteArchitecturalModel getCAM(){
//		return cam;
//	}

}
