package es.ual.acg.cos.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import es.ual.acg.cos.types.ComponentData;
import architectural_metamodel.Architectural_metamodelFactory;
import architectural_metamodel.ConcreteArchitecturalModel;
import architectural_metamodel.ConcreteComponent;
import architectural_metamodel.RuntimeProperty;

@Stateful 
public class ManageInteraction {
	private static final Logger LOGGER = Logger.getLogger(ManageInteraction.class);
	
	private HbDataStore dataStore;
	
	private HbDataStore dataStoreCC;
	
	// Variable to connect with DB
	Connection conn = null;
	
	private ConcreteArchitecturalModel cam;

	// @PostConstruct
	public void initialize() throws SQLException, ClassNotFoundException{
		// Establecimiento de conexión con la base de datos
		String url = "jdbc:postgresql://150.214.150.116:5432/interaction33";
		String login = "postgres";
		String password = "root";
		conn = null;

		Class.forName("org.postgresql.Driver");
		conn = (Connection) DriverManager.getConnection(url, login,
				password);
		if (conn != null) {
		}
		
		if(dataStore == null)		 
		{
			LOGGER.info("[IMM] Getting DataStore...");
			this.getDataStoreFromManageDB();
			LOGGER.info("[IMM] DataStore has been got");
		}
		else
			LOGGER.info("[IMM] DataStore is already got");
		
		if(dataStoreCC == null)		 
		{
			LOGGER.info("[IMM] Getting DataStoreCC...");
			this.getDataStoreCCFromManageDB();
			LOGGER.info("[IMM] DataStoreCC has been got");
		}
		else
			LOGGER.info("[IMM] DataStoreCC is already got");
	}

	/**
	 * Create an user
	 * 
	 * @param userName
	 * @param userPassword
	 * @param userProfile
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public void insertInteraction(String newSession, String deviceType, String interactionType, String dateTime, 
			String userId, String latitude, String longitude, String operationPerformed, String InstaceId, 
			List<String> groupComponent, List<String> ungroupComponent, 
			List<ComponentData> cotsget) throws ClassNotFoundException, SQLException {
		
		int userinteraction_id;
		int cotsget_id;

		initialize();
		Statement s = (Statement) conn.createStatement();
		int x = s.executeUpdate("Insert Into userinteraction (new_session, device_type, interaction_type, date_time, user_id, "
						+ "latitude, longitude, operation_performed, componentinstance_id) Values ('"
						+ newSession
						+ "', '"
						+ deviceType
						+ "', '"
						+ interactionType
						+ "', '"
						+ dateTime
						+ "', '"
						+ userId
						+ "', '"
						+ latitude
						+ "', '"
						+ longitude
						+ "', '" 
						+ operationPerformed 
						+ "', '" 
						+ InstaceId 
						+ "')");

		ResultSet rs = s.executeQuery("Select userinteraction_id From userinteraction Where date_time = '"
							+ dateTime + "' and user_id = '" + userId + "'");
		if (rs.next()) {
			userinteraction_id = rs.getInt(1);
			if (ungroupComponent != null && ungroupComponent.size() > 0){
				if (ungroupComponent.get(0).compareTo("") != 0) {
					for(int i = 0; i < ungroupComponent.size(); i++){
						s = (Statement) conn.createStatement();
						x = s.executeUpdate("Insert Into ungroupcomponent (userinteraction_id, serviceinstance_id) Values ('"
										+ userinteraction_id
										+ "', '"
										+ ungroupComponent.get(i)
										+ "')");
					}
				}
			}
			if (groupComponent != null && groupComponent.size() > 0){
				if (groupComponent.get(0).compareTo("") != 0) {
					for(int i = 0; i < groupComponent.size(); i++){
						s = (Statement) conn.createStatement();
						x = s.executeUpdate("Insert Into groupcomponent (userinteraction_id, serviceinstance_id) Values ('"
										+ userinteraction_id
										+ "', '"
										+ groupComponent.get(i)
										+ "')");
					}
				}
			}
			if(cotsget != null){
				for(int i = 0; i < cotsget.size(); i++){
					x = s.executeUpdate("Insert Into cotsget (userinteraction_id, pos_x, pos_y, tamano_x, tamano_y) Values ('"
									+ userinteraction_id
									+ "', '"
									+ cotsget.get(i).getPosx()
									+ "', '"
									+ cotsget.get(i).getPosy()
									+ "', '"
									+ cotsget.get(i).getTamanox()
									+ "', '"
									+ cotsget.get(i).getTamanoy()
									+ "')");
					rs = s.executeQuery("select lastval();");
					if (rs.next()) {
						cotsget_id = rs.getInt(1);
						for(int j = 0; j < cotsget.get(i).getServicios().size(); j++){
							x = s.executeUpdate("Insert Into service (cotsget_id, serviceinstance_id) Values ('"
											+ cotsget_id
											+ "', '"
											+ cotsget.get(i).getServicios().get(j).getInstanceId()
											+ "')");
						}
					}
				}
			}
		}

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
	
	/*@Lock(LockType.READ)
	public void loadInteraction7(String modelId, String userId, String componentId, 
			String componentName, String action, String position, String value) {
		
		String instanceId = makeInstanceId(userId, componentId, componentName);

		componentId = componentId + "," + instanceId;
		
		try {
			initialize();

			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat(
					"E yyyy.MM.dd 'at' hh:mm:ss a zzz");

			insertInteraction(modelId, componentId, userId, ft.format(dNow),
					action, position, value);
		} catch (Exception e) {
		}
	}*/


	/*@Lock(LockType.READ)
	public void loadInteraction6(String modelId, String componentId,
			String userId, String action, String position, String value) {

		try {
			initialize();
			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat(
					"E yyyy.MM.dd 'at' hh:mm:ss a zzz");
			insertInteraction(modelId, componentId, userId, ft.format(dNow),
					action, position, value);
		} catch (Exception e) {
		}
	}*/
	
	@Lock(LockType.READ)
	public void manageRuntimeProperty(String componentId, String property, String value)throws Exception{
		SessionFactory sessionFactory = dataStore.getSessionFactory();

		//Open a new Session
		Session session = sessionFactory.openSession();
		  
		//Start transaction
		session.beginTransaction();
		
		Query query = session.createQuery("FROM ConcreteArchitecturalModel WHERE camID = '" + cam.getCamID() + "'");

		List<?> cams = query.list();

		cam = (ConcreteArchitecturalModel) cams.get(0);
		LOGGER.info("[IMM] manage runtime property CAM ID: " + cam.getCamID());
		
		//Initialize the component
		//ConcreteComponent cc = new ConcreteComponentImpl();
		ConcreteComponent cc = Architectural_metamodelFactory.eINSTANCE.createConcreteComponent();
		
		boolean foundComponent = false;
		for(int i = 0; i < cam.getConcreteComponent().size() && foundComponent == false; i++){
			if(cam.getConcreteComponent().get(i).getComponentName().equalsIgnoreCase(componentId)){
				LOGGER.info("CC NAME: " + cam.getConcreteComponent().get(i).getComponentName());
				
				cc = cam.getConcreteComponent().get(i);

				boolean foundProperty = false;
				for(int j = 0; j < cam.getConcreteComponent().get(i).getRuntimeProperty().size() && foundProperty == false; j++){
					if(cam.getConcreteComponent().get(i).getRuntimeProperty().get(j).getPropertyID().equalsIgnoreCase(property) == true){

						cam.getConcreteComponent().get(i).getRuntimeProperty().get(j).setPropertyValue(value);
						
						foundProperty = true;
					}
				}
				if(foundProperty == false){

					//RuntimeProperty rp = new RuntimePropertyImpl();
				  RuntimeProperty rp = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
					rp.setCc(cc);
					rp.setPropertyID(property);
					rp.setPropertyValue(value);
					
					cam.getConcreteComponent().get(i).getRuntimeProperty().add(rp);
				}
				
				foundComponent = true;
			}
		}

		session.save(cam);
		
		//Commit the changes to the database.
		session.getTransaction().commit();
				  
		//Close the session.
		session.close();
	}
	
	/*private String queryComponentPlatform(String componentID) {
		SessionFactory sessionFactory = dataStoreCC.getSessionFactory();

		//Open a new Session
		Session session = sessionFactory.openSession();
		  
		//Start transaction
		session.beginTransaction();
				
		Query query = session.createQuery("FROM ConcreteComponentSpecification "
				+ "WHERE componentID = '" + componentID + "'");

		List<?> ccs = query.list();

		LOGGER.info("PlataformType -> " + ((ConcreteComponentSpecification) ccs.get(0)).getPackaging().
																getImplementation().getPlatformType());
				  
		//Close the session.
		session.close();
		
		if(ccs.size() == 1)
			return ((ConcreteComponentSpecification) ccs.get(0)).getPackaging().getImplementation().
																			getPlatformType().toString();
		else
			return null;
	}*/
	
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
			
			e.printStackTrace();
		}
		
		if(dataStore == null)
			LOGGER.info("[IMM] Error getting the DataStore");
		
	}
	
	public void getDataStoreCCFromManageDB() {
		dataStoreCC = null;
		
		ManageDB managdb = null;
		Context initialContext;
		try
		{
			initialContext = new InitialContext();
			
			managdb = (ManageDB)initialContext.lookup("java:module/ManageDB");
			dataStoreCC = managdb.getDataStoreCC();
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
		
		if(dataStoreCC == null)
			LOGGER.info("[IMM] Error getting the DataStoreConcreteComponent");
		
	}

	public ConcreteArchitecturalModel getCAM(){
		return cam;
	}

	public void setCAM(ConcreteArchitecturalModel cam){
		this.cam = cam;
	}

}
