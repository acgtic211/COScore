/*
 * ManageInteraction.java -- Gestiona la base de datos de interacción.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * ManageInteraction.java is part of COScore Community.
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
package es.ual.acg.cos.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

import es.ual.acg.cos.controllers.ManageArchitectures;
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
		
		ManageRegister managdb = null;
		Context initialContext;
		try
		{
			initialContext = new InitialContext();
			
			managdb = (ManageRegister)initialContext.lookup("java:module/ManageRegister");
			dataStoreCC = managdb.getDataStoreCC();
		}
		catch (NamingException e) {
			LOGGER.error("NamingException: " + e);
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
