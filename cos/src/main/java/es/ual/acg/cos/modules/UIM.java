package es.ual.acg.cos.modules;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.codec.binary.Hex;
import org.jboss.logging.Logger;

import es.ual.acg.cos.controllers.ManageUsers;
import es.ual.acg.cos.types.InterModulesData;
import es.ual.acg.cos.ws.types.CreateUserParams;
import es.ual.acg.cos.ws.types.CreateUserResult;
import es.ual.acg.cos.ws.types.DeleteUserResult;
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
		        
		    /******OJO,una vez realizado Perfiles de usuario hay que incluir este codigo aqui
		    
		    if(userProfile.equalsIgnoreCase("anonimousprofile")) {
		    	camID = CamAnonimousDefault //El cam anonimo por defecto 
			}else{
				CamID = CamProfile //El cam asociado al pefil que haya elegido el usuario
			}
		     
		    ****** Recordar que el CAM ahora mismo se mete a mano en ManageUSer */
		    
		    Context initialContext;
			initialContext = new InitialContext();
			ManageUsers mu = (es.ual.acg.cos.controllers.ManageUsers)initialContext.lookup("java:app/cos/ManageUsers");
			mu.createUser(userName, userPassword, userProfile);
			
			result.setCreated(true);
			result.setMessage("> Successfully created");
        
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
