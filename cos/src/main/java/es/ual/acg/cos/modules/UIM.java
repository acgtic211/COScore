package es.ual.acg.cos.modules;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.codec.binary.Hex;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.jboss.logging.Logger;

import architectural_metamodel.ConcreteArchitecturalModel;
import es.ual.acg.cos.controllers.ManageArchitectures;
import es.ual.acg.cos.controllers.ManageUsers;
import es.ual.acg.cos.types.InterModulesData;
import es.ual.acg.cos.ws.types.CreateUserParams;
import es.ual.acg.cos.ws.types.CreateUserResult;
import es.ual.acg.cos.ws.types.DeleteUserResult;
import es.ual.acg.cos.ws.types.QueryProfileResult;
import es.ual.acg.cos.ws.types.QueryUserParams;
import es.ual.acg.cos.ws.types.QueryUserResult;
import es.ual.acg.cos.ws.types.UpdateUserResult;

@Stateful
public class UIM {
	
	private static final Logger LOGGER = Logger.getLogger(ManageUsers.class);
	
	/**
	 * Create an user
	 * 
	 * @param userName
	 * @param userPassword
	 * @param userProfile
	 * @return
	 */
	public CreateUserResult createUser(CreateUserParams params) {
		CreateUserResult result = new CreateUserResult();
		InterModulesData resultquerycamprofile = new InterModulesData();
		result.setCreated(false);
		
		String userName = params.getUserName();
		String userPassword = params.getUserPassword();
		String userProfile = params.getUserProfile();
	
		try {
		  /** Encriptamos el password **/
			MessageDigest md = null;
			String password = userPassword;
			md= MessageDigest.getInstance("MD5");
		    md.update(password.getBytes());
		    byte[] mb = md.digest();
		    userPassword = new String (Hex.encodeHex(mb));
		    params.setUserPassword(userPassword);
		    
		    resultquerycamprofile = queryCamProfile(userProfile);

		    if(resultquerycamprofile.getValue().compareTo("-1") != 0) {
		    	String camID = resultquerycamprofile.getValue();
		    	String camidforUser = camID+userName; //Nuevo identificado de cam formado por el cam del perfil + el nombre de usuario
			    Context initialContext;
				initialContext = new InitialContext();

				ConcreteArchitecturalModel cam;
				
				ManageArchitectures ma = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
				cam = ma.readModel(camID);
				if(cam != null){
				  //Clonar objeto antes de guardarlo en la base de datos
				  ConcreteArchitecturalModel camCopy = EcoreUtil.copy(cam);
				  
				  camCopy.setCamID(camidforUser);
					ma.saveModel(camCopy);
					
					ManageUsers mu = (es.ual.acg.cos.controllers.ManageUsers)initialContext.lookup("java:app/cos/ManageUsers");
					mu.createUser(userName, userPassword, userProfile, camidforUser);
					
					result.setCreated(true);
					result.setMessage("> Successfully created");
				}else{
					LOGGER.error("Cam no exist!");
					result.setMessage("> Internal Server Error");
				}
				
		    }else{
				LOGGER.error("Profile no exist!");
				result.setMessage("> Internal Server Error");
		    }
        
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e);
			result.setMessage("> Internal Server Error");
		} catch (SQLException e){
			LOGGER.error(e);
			result.setMessage("> " + e);
		} catch (ClassNotFoundException e){
			LOGGER.error(e);
			result.setMessage("> Internal Server Error");
		} catch (NamingException e) {
			LOGGER.error(e);
			result.setMessage("> Internal Server Error");
		} catch (Exception e) {
			LOGGER.error(e);
			result.setMessage("> Internal Server Error");
		}
			
		return result;
	}
	/**
	 * Query user
	 * 
	 * @param userName
	 * @param userPassword
	 * @return
	 */
	public QueryUserResult queryUser(QueryUserParams params) {
		QueryUserResult result = new QueryUserResult();
		result.setValidation(false);
		result.setIduser(-1);
		result.setMessage("> Validation Error user o password incorrect");
		
		String userName = params.getUserName();
		String userPassword = params.getUserPassword();
		int idUser;
		
		try {
		  /** Encriptamos el password **/
			MessageDigest md = null;
			String password = userPassword;
			md= MessageDigest.getInstance("MD5");
		    md.update(password.getBytes());
		    byte[] mb = md.digest();
		    userPassword = new String (Hex.encodeHex(mb));
		    params.setUserPassword(userPassword);
		        
		    Context initialContext;
			initialContext = new InitialContext();
			ManageUsers mu = (es.ual.acg.cos.controllers.ManageUsers)initialContext.lookup("java:app/cos/ManageUsers");
			idUser=mu.queryUser(userName, userPassword);
			
			if(idUser != -1) {
				result.setValidation(true);
				result.setIduser(idUser);
				result.setMessage("> Successfully validation");
			}
        
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e);
			result.setMessage("> Internal Server Error");
		} catch (SQLException e){
			LOGGER.error(e);
			result.setMessage("> " + e);
		} catch (ClassNotFoundException e){
			LOGGER.error(e);
			result.setMessage("> Internal Server Error");
		} catch (NamingException e) {
			LOGGER.error(e);
			result.setMessage("> Internal Server Error");
		}
			
		return result;
	}

	public QueryProfileResult queryProfile() {
		QueryProfileResult result = new QueryProfileResult();
		List<String> perfiles = new ArrayList<String>();
		
		result.setValidation(false);
		result.setProfiles(null);
		result.setMessage("> Not found o Empty profile Error");
			
		try {
			        
		    Context initialContext;
			initialContext = new InitialContext();
			ManageUsers mu = (es.ual.acg.cos.controllers.ManageUsers)initialContext.lookup("java:app/cos/ManageUsers");
			perfiles=mu.queryProfile();
			
			if(perfiles.get(0) != null) {
				result.setValidation(true);
				result.setProfiles(perfiles);
				result.setMessage("> Successfully request");
			}

		} catch (SQLException e){
			LOGGER.error(e);
			result.setMessage("> " + e);
		} catch (ClassNotFoundException e){
			LOGGER.error(e);
			result.setMessage("> Internal Server Error");
		} catch (NamingException e) {
			LOGGER.error(e);
			result.setMessage("> Internal Server Error");
		}
		return result;
	}
	
	public InterModulesData queryCamUser(String userID){
		Context initialContext;
		InterModulesData resultquerycam = new InterModulesData();
		resultquerycam.setValue("-1");
		
		try {
			initialContext = new InitialContext();
			ManageUsers mu = (es.ual.acg.cos.controllers.ManageUsers)initialContext.lookup("java:app/cos/ManageUsers");

			String cam = mu.queryCamUser(userID);

			resultquerycam.setValue(cam); //Devuelve el cam
			resultquerycam.setMessage("> CAM OK");

			
		} catch (NamingException e) {
			LOGGER.error(e);
			resultquerycam.setMessage("> Internal Server Error");
		} catch (SQLException e){
			LOGGER.error(e);
			resultquerycam.setMessage("> " + e);
		} catch (ClassNotFoundException e){
			LOGGER.error(e);
			resultquerycam.setMessage("> Internal Server Error");
		}
		return resultquerycam;
	}
	public InterModulesData queryCamProfile(String profileName){
		Context initialContext;
		InterModulesData resultquerycam = new InterModulesData();
		resultquerycam.setValue("-1");
		
		try {
			initialContext = new InitialContext();
			ManageUsers mu = (es.ual.acg.cos.controllers.ManageUsers)initialContext.lookup("java:app/cos/ManageUsers");

			String cam = mu.queryCamProfile(profileName);

			resultquerycam.setValue(cam); //Devuelve el cam
			resultquerycam.setMessage("> CAM OK");

			
		} catch (NamingException e) {
			LOGGER.error(e);
			resultquerycam.setMessage("> Internal Server Error");
		} catch (SQLException e){
			LOGGER.error(e);
			resultquerycam.setMessage("> " + e);
		} catch (ClassNotFoundException e){
			LOGGER.error(e);
			resultquerycam.setMessage("> Internal Server Error");
		}
		return resultquerycam;
	}
	public DeleteUserResult deleteUser(String userID) {
		DeleteUserResult result = new DeleteUserResult();
		boolean queryresult;
		result.setDeleted(false);
		result.setMessage("> Not found o Empty userid Error");
		
		try {
		    Context initialContext;
			initialContext = new InitialContext();
			ManageUsers mu = (es.ual.acg.cos.controllers.ManageUsers)initialContext.lookup("java:app/cos/ManageUsers");
			queryresult=mu.deleteUser(userID);
			
			result.setDeleted(queryresult);
			if (queryresult)
				result.setMessage("> Successfully deleted");
			
		} catch (SQLException e){
			LOGGER.error(e);
			result.setMessage("> " + e);
		} catch (Exception e){
			LOGGER.error(e);
			result.setMessage("> Internal Server Error"+ e);
		}
			
		return result;
	}
	public UpdateUserResult updateUser(String userID, String userName, String userPassword, String userProfile) {
		UpdateUserResult result = new UpdateUserResult();
		boolean queryresult;
		result.setUpdated(false);
		result.setMessage("> Not found o Empty userid Error");
		
		try {
		  /** Encriptamos el password **/
			MessageDigest md = null;
			String password = userPassword;
			md= MessageDigest.getInstance("MD5");
		    md.update(password.getBytes());
		    byte[] mb = md.digest();
		    userPassword = new String (Hex.encodeHex(mb));
		        
		    Context initialContext;
			initialContext = new InitialContext();
			ManageUsers mu = (es.ual.acg.cos.controllers.ManageUsers)initialContext.lookup("java:app/cos/ManageUsers");
			queryresult=mu.updateUser(userID, userName, userPassword, userProfile);
			
			result.setUpdated(queryresult);
			if (queryresult)
				result.setMessage("> Successfully updated");

        
		} catch (SQLException e){
			LOGGER.error(e);
			result.setMessage("> " + e);
		} catch (Exception e){
			LOGGER.error(e);
			result.setMessage("> Internal Server Error"+ e);
		}
			
		return result;
	}
}
