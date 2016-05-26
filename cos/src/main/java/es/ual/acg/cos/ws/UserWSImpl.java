/*
 * UserWSImpl.java -- Web service User - Servicio encargado de la gestión de usuarios.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * UserWSImpl.java is part of COScore Community.
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
package es.ual.acg.cos.ws;

import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import es.ual.acg.cos.modules.UIM;
import es.ual.acg.cos.ws.types.CreateUserParams;
import es.ual.acg.cos.ws.types.CreateUserResult;
import es.ual.acg.cos.ws.types.DeleteUserParams;
import es.ual.acg.cos.ws.types.DeleteUserResult;
import es.ual.acg.cos.ws.types.QueryProfileResult;
import es.ual.acg.cos.ws.types.QueryUserParams;
import es.ual.acg.cos.ws.types.QueryUserResult;
import es.ual.acg.cos.ws.types.UpdateUserParams;
import es.ual.acg.cos.ws.types.UpdateUserResult;

@WebService(endpointInterface = "es.ual.acg.cos.ws.UserWS")
public class UserWSImpl implements UserWS{
	
	private static final Logger LOGGER = Logger.getLogger(UserWSImpl.class);
	private final String privatekey = "3nia";

	public QueryUserResult queryUser(QueryUserParams params, String privatekey)	{
		  
		QueryUserResult queryUserResult = new QueryUserResult();	
		  //First: check the private key
		  if(privatekey!=null && this.privatekey.compareTo(privatekey) == 0) {
		  //Second: check the params.username
		  	if(params.getUserName()!=null && params.getUserName().compareTo("") != 0){ 
		  	  //Third: check the params.userpassword
			  	if(params.getUserPassword()!=null && params.getUserPassword().compareTo("") != 0){ 
				  	 //Fourth: check the params.userpassword
				  		//Fifth: call the corresponding module
			  	  		Context initialContext;
			  	  		try { 		  
			  	  			initialContext = new InitialContext();	
			  	  			UIM userInformation = (UIM)initialContext.lookup("java:app/cos/UIM");		
			  	  			queryUserResult = userInformation.queryUser(params);
				  		}
				  		catch (NamingException e) {
				  			LOGGER.error(e);
							 queryUserResult.setValidation(false);
							 queryUserResult.setIduser(-1);
							 queryUserResult.setMessage("> Internal Server Error");
				  			
				  		}
			    } else {
					 queryUserResult.setValidation(false);
					 queryUserResult.setIduser(-1);
					 queryUserResult.setMessage("> Not found o Empty userpassword Error");
					 LOGGER.error("Not found o Empty userpassword Error");   
				}
			} else {
				 queryUserResult.setValidation(false);
				 queryUserResult.setIduser(-1);
				 queryUserResult.setMessage("> Not found o Empty username Error");
				 LOGGER.error("Not found o Empty username Error"); 
			}

		  }else {
		    queryUserResult.setValidation(false);
		    queryUserResult.setIduser(-1);
		    queryUserResult.setMessage("> Private key Error");
		    LOGGER.error("Private key error");   
		  }
		  
		  
		  return queryUserResult;
	}

	public QueryProfileResult queryProfile(String privatekey)	{
		  
		QueryProfileResult queryProfileResult = new QueryProfileResult();	
		  //First: check the private key
		  if(privatekey!=null && this.privatekey.compareTo(privatekey) == 0) {
		  
			  	  		Context initialContext;
			  	  		try { 		  
			  	  			initialContext = new InitialContext();	
			  	  			UIM profileInformation = (UIM)initialContext.lookup("java:app/cos/UIM");		
			  	  			queryProfileResult = profileInformation.queryProfile();
				  		}
				  		catch (NamingException e) {
				  			LOGGER.error(e);
				  			queryProfileResult.setValidation(false);
				  			queryProfileResult.setProfiles(null);
				  			queryProfileResult.setMessage("> Internal Server Error");
				  			
				  		}
		  }else {
			  queryProfileResult.setValidation(false);
			  queryProfileResult.setProfiles(null);
			  queryProfileResult.setMessage("> Private key Error");
		    LOGGER.error("Private key error");   
		  }

		  return queryProfileResult;
	}	
	
	public DeleteUserResult deleteUser(DeleteUserParams params, String privatekey)	{
			  
			DeleteUserResult deleteUserResult = new DeleteUserResult();
			//First: check the private key
			  if(privatekey!=null && this.privatekey.compareTo(privatekey) == 0) {
			  //Second: check the params.username
			  	if(params.getUserId()!=null && params.getUserId().compareTo("") != 0){ 
		  		//Fifth: call the corresponding module
		  	  		Context initialContext;
		  	  		try { 		  
		  	  			initialContext = new InitialContext();	
		  	  			UIM userInformation = (UIM)initialContext.lookup("java:app/cos/UIM");		
		  	  			deleteUserResult = userInformation.deleteUser(params.getUserId());
			  		}
			  		catch (NamingException e) {
			  			LOGGER.error(e);
			  			deleteUserResult.setDeleted(false);
			  			deleteUserResult.setMessage("> Internal Server Error");
			  		}
				} else {
		  			deleteUserResult.setDeleted(false);
					 deleteUserResult.setMessage("> Not found o Empty userid Error");
					 LOGGER.error("Not found o Empty userid Error"); 
				}

			  }else {
		  			deleteUserResult.setDeleted(false);
			    deleteUserResult.setMessage("> Private key Error");
			    LOGGER.error("Private key error");   
			  }
			  
		return deleteUserResult;
	}
	
	public CreateUserResult createUser(CreateUserParams params, String privatekey)	{

		CreateUserResult createUserResult = new CreateUserResult();
	  //First: check the private key
	  if(privatekey!=null && this.privatekey.compareTo(privatekey) == 0) {
	  //Second: check the params.username
	  	if(params.getUserName()!=null && params.getUserName().compareTo("") != 0){ 
	  	  //Third: check the params.userpassword
		  	if(params.getUserPassword()!=null && params.getUserPassword().compareTo("") != 0){ 
			  	 //Fourth: check the params.userpassword
			  	if(params.getUserProfile()!=null && params.getUserProfile().compareTo("") != 0){ 		
			  		
			  		//Fifth: call the corresponding module
		  	  		Context initialContext;
		  	  		try { 		  
		  	  			initialContext = new InitialContext();	
		  	  			UIM userInformation = (UIM)initialContext.lookup("java:app/cos/UIM");	
		  	  			createUserResult = userInformation.createUser(params);
		  	  		}
		  			catch (NamingException e) {
		  				LOGGER.error(e);
				        createUserResult.setCreated(false);
				        createUserResult.setMessage("> Internal Server Error");  
		  			}
		  	  		
			  	} else {
			        createUserResult.setCreated(false);
			        createUserResult.setMessage("> Not found o Empty userprofile Error");  
			        LOGGER.error("Not found o Empty userprofile Error"); 
				}
		    } else {
		        createUserResult.setCreated(false);
		        createUserResult.setMessage("> Not found o Empty userpassword Error");  
		        LOGGER.error("Not found o Empty userpassword Error"); 
			}
	    } else {
	        createUserResult.setCreated(false);
	        createUserResult.setMessage("> Not found o Empty username Error");  
	        LOGGER.error("Not found o Empty username Error"); 
	    }
	    
	  }else {
	    createUserResult.setCreated(false);
	    createUserResult.setMessage("> Private key Error");	
	    LOGGER.error("Private key error");
	  }
		return createUserResult;
	}
	
	public UpdateUserResult updateUser(UpdateUserParams params, String privatekey)	{

		UpdateUserResult updateUserResult = new UpdateUserResult();	
		  //First: check the private key
		  if(privatekey!=null && this.privatekey.compareTo(privatekey) == 0) {
		  //Second: check the params.username
		  	if(params.getUserId()!=null && params.getUserId().compareTo("") != 0){ 
		  	  //Third: check the params.userpassword
			  	if(params.getNewUserName()!=null && params.getNewUserName().compareTo("") != 0){ 
			  		if(params.getNewUserPassword()!=null && params.getNewUserPassword().compareTo("") != 0){ 
				  		if(params.getNewUserProfile()!=null && params.getNewUserProfile().compareTo("") != 0){ 
				  	 //Fourth: check the params.userpassword
				  		//Fifth: call the corresponding module
			  	  		Context initialContext;
			  	  		try { 		  
			  	  			initialContext = new InitialContext();	
			  	  			UIM userInformation = (UIM)initialContext.lookup("java:app/cos/UIM");		
			  	  			updateUserResult = userInformation.updateUser(params.getUserId(),
			  	  														  params.getNewUserName(),
			  	  														  params.getNewUserPassword(),
			  	  														  params.getNewUserProfile());
				  		}
				  		catch (NamingException e) {
				  			LOGGER.error(e);
							updateUserResult.setUpdated(false);
				  			updateUserResult.setMessage("> Internal Server Error");
				  			
				  		}
				  		} else {
							updateUserResult.setUpdated(false);
				  			updateUserResult.setMessage("> Not found o Empty new userprofile Error");
				  			LOGGER.error("Not found o Empty userprofile Error");   
				  		}
			  		} else {
						updateUserResult.setUpdated(false);
			  			updateUserResult.setMessage("> Not found o Empty new userpassword Error");
			  			LOGGER.error("Not found o Empty userpassword Error");   
			  		}
			  	} else {
					updateUserResult.setUpdated(false);
			  		updateUserResult.setMessage("> Not found o Empty new username Error");
					LOGGER.error("Not found o Empty username Error"); 
				}
			} else {
				updateUserResult.setUpdated(false);
				updateUserResult.setMessage("> Not found o Empty userid Error");
				LOGGER.error("Not found o Empty username Error"); 
			}

		  }else {
			  updateUserResult.setUpdated(false);
			  updateUserResult.setMessage("> Private key Error");
			  LOGGER.error("Private key error");   
		  }
		  
		return updateUserResult;
	}

  public String getPrivatekey() {
    return privatekey;
  }
}
