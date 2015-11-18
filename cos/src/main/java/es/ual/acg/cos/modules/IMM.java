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
import es.ual.acg.cos.controllers.ManageDB;
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
	
	// Variable to connect with DB
	//Connection conn = null;
	
//	private ConcreteArchitecturalModel cam;

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
	// @PostConstruct
//	public void initialize(){
//		// Establecimiento de conexión con la base de datos
//		String url = "jdbc:postgresql://150.214.150.116:5432/interaction";
//		String login = "postgres";
//		String password = "root";
//		conn = null;
//		try {
//			Class.forName("org.postgresql.Driver");
//			conn = (Connection) DriverManager.getConnection(url, login,
//					password);
//			if (conn != null) {
//			}
//		} catch (SQLException ex) {
//			System.out.println("La conexión ha sido nula");
//			System.out.println(ex);
//		} catch (ClassNotFoundException ex) {
//			System.out.println("La conexión ha sido nula");
//			System.out.println(ex);
//		} catch (Exception e){
//			
//		}
//		
//		if(dataStore == null)		 
//		{
//			LOGGER.info("[IMM] Getting DataStore...");
//			this.getDataStoreFromManageDB();
//			LOGGER.info("[IMM] DataStore has been got");
//		}
//		else
//			LOGGER.info("[IMM] DataStore is already got");
//		
//		if(dataStoreCC == null)		 
//		{
//			LOGGER.info("[IMM] Getting DataStoreCC...");
//			this.getDataStoreCCFromManageDB();
//			LOGGER.info("[IMM] DataStoreCC has been got");
//		}
//		else
//			LOGGER.info("[IMM] DataStoreCC is already got");
//	}
//
//	/**
//	 * Create an user
//	 * 
//	 * @param userName
//	 * @param userPassword
//	 * @param userProfile
//	 * @return
//	 */
//	public boolean insertInteraction(String modelId, String componentId,
//			String userId, String interactionMoment, String action,
//			String property, String value) {
//		try {
//			initialize();
//			Statement s = (Statement) conn.createStatement();
//			int x = s
//					.executeUpdate("Insert Into userinteraction (model_id, component_id, user_id, interaction_moment, action, property, value) Values ('"
//							+ modelId
//							+ "', '"
//							+ componentId
//							+ "', '"
//							+ userId
//							+ "', '"
//							+ interactionMoment
//							+ "', '"
//							+ action
//							+ "', '" + property + "', '" + value + "')");
//
//			s.close();
//			conn.close();
//			if (x == 0)
//				return false;
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		}
//		return true;
//	}
//	
//	private String makeInstanceId(String userId, String componentId, String componentName){
//		String platform = queryComponentPlatform(componentId);
//		String instanceId = null;
//		try{
//			if(platform.equalsIgnoreCase("Web")){
//				ManageWookie wookie = new ManageWookie();
//	
//				WidgetData widgetData = wookie.getOrCreateWidgetInstance(userId, componentId, componentName);
//				
//				instanceId = widgetData.getIdentifier();
//				
//			}else{
//				if(platform.equalsIgnoreCase("Java")){
//					ManageJava manageJava = new ManageJava();
//	
//					JavaComponentResponse r = manageJava.getOrCreateJavaInstance(userId, componentId, componentName);
//	
//					instanceId = r.getComponentInstanceName();
//				}
//				
//			}
//		} catch (Exception e) {
//			LOGGER.error(e);
//		}
//		return instanceId;
//	}
//	
	
	/*@Lock(LockType.READ)
	public void loadInteraction7(String modelId, String userId, String componentId, 
			String componentName, String action, String position, String value) {
		
		try {
			InitialContext initialContext = new InitialContext();
		    ManageInteraction mi = (es.ual.acg.cos.controllers.ManageInteraction)initialContext.lookup("java:app/cos/ManageInteraction");
	
		    mi.loadInteraction7(modelId, userId, componentId, componentName, action, position, value);
			    
		} catch (Exception e) {
			LOGGER.error(e);

		} 
		
	}*/


	/*@Lock(LockType.READ)
	public void loadInteraction6(String modelId, String componentId,
			String userId, String action, String position, String value) {

		try {
			InitialContext initialContext = new InitialContext();
		    ManageInteraction mi = (es.ual.acg.cos.controllers.ManageInteraction)initialContext.lookup("java:app/cos/ManageInteraction");
	
		    mi.loadInteraction6(modelId,  componentId, userId, action, position, value);
			    
		} catch (Exception e) {
			LOGGER.error(e);

		} 
	}
	*/
//	
//	@Lock(LockType.READ)
//	public void manageRuntimeProperty(String componentId, String property, String value){
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
//		LOGGER.info("[IMM] manage runtime property CAM ID: " + cam.getCamID());
//		
//		//Initialize the component
//		//ConcreteComponent cc = new ConcreteComponentImpl();
//		ConcreteComponent cc = Architectural_metamodelFactory.eINSTANCE.createConcreteComponent();
//		
//		boolean foundComponent = false;
//		for(int i = 0; i < cam.getConcreteComponent().size() && foundComponent == false; i++){
//			if(cam.getConcreteComponent().get(i).getComponentName().equalsIgnoreCase(componentId)){
//				LOGGER.info("CC NAME: " + cam.getConcreteComponent().get(i).getComponentName());
//				
//				cc = cam.getConcreteComponent().get(i);
//
//				boolean foundProperty = false;
//				for(int j = 0; j < cam.getConcreteComponent().get(i).getRuntimeProperty().size() && foundProperty == false; j++){
//					if(cam.getConcreteComponent().get(i).getRuntimeProperty().get(j).getPropertyID().equalsIgnoreCase(property) == true){
//
//						cam.getConcreteComponent().get(i).getRuntimeProperty().get(j).setPropertyValue(value);
//						
//						foundProperty = true;
//					}
//				}
//				if(foundProperty == false){
//
//					//RuntimeProperty rp = new RuntimePropertyImpl();
//				  RuntimeProperty rp = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
//					rp.setCc(cc);
//					rp.setPropertyID(property);
//					rp.setPropertyValue(value);
//					
//					cam.getConcreteComponent().get(i).getRuntimeProperty().add(rp);
//				}
//				
//				foundComponent = true;
//			}
//		}
//
//		session.save(cam);
//		
//		//Commit the changes to the database.
//		session.getTransaction().commit();
//				  
//		//Close the session.
//		session.close();
//	}
//	
//	private String queryComponentPlatform(String componentID) {
//		SessionFactory sessionFactory = dataStoreCC.getSessionFactory();
//
//		//Open a new Session
//		Session session = sessionFactory.openSession();
//		  
//		//Start transaction
//		session.beginTransaction();
//				
//		Query query = session.createQuery("FROM ConcreteComponentSpecification "
//				+ "WHERE componentID = '" + componentID + "'");
//
//		List<?> ccs = query.list();
//
//		LOGGER.info("PlataformType -> " + ((ConcreteComponentSpecification) ccs.get(0)).getPackaging().
//																getImplementation().getPlatformType());
//				  
//		//Close the session.
//		session.close();
//		
//		if(ccs.size() == 1)
//			return ((ConcreteComponentSpecification) ccs.get(0)).getPackaging().getImplementation().
//																			getPlatformType().toString();
//		else
//			return null;
//	}
//	
//	public void getDataStoreFromManageDB() {
//		dataStore = null;
//		
//		ManageArchitectures managArch = null;
//		Context initialContext;
//		try
//		{
//			initialContext = new InitialContext();
//			
//			managArch = (ManageArchitectures)initialContext.lookup("java:module/ManageArchitectures");
//			dataStore = managArch.getDataStore();
//		}
//		catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//		
//		if(dataStore == null)
//			LOGGER.info("[IMM] Error getting the DataStore");
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
//			LOGGER.info("[IMM] Error getting the DataStoreConcreteComponent");
//		
//	}
//
//	public ConcreteArchitecturalModel getCAM(){
//		return cam;
//	}
//
//	public void setCAM(ConcreteArchitecturalModel cam){
//		this.cam = cam;
//	}
}
