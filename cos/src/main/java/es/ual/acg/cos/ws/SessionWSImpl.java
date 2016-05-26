/*
 * SessionWSImpl.java -- Web service Session - Servicio encargado de manejar la inicialización de las sesiones.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * SessionWSImpl.java is part of COScore Community.
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

import es.ual.acg.cos.modules.COSSessionMM;
import es.ual.acg.cos.ws.types.DefaultInitSessionResult;
import es.ual.acg.cos.ws.types.LoginSessionParams;
import es.ual.acg.cos.ws.types.LoginSessionResult;
import es.ual.acg.cos.ws.types.LogoutSessionParams;
import es.ual.acg.cos.ws.types.LogoutSessionResult;
import es.ual.acg.cos.ws.types.InitUserArchitectureSessionParams;
import es.ual.acg.cos.ws.types.InitUserArchitectureSessionResult;

@WebService(endpointInterface = "es.ual.acg.cos.ws.SessionWS")
public class SessionWSImpl implements SessionWS{
	
	private static final Logger LOGGER = Logger.getLogger(SessionWSImpl.class);
	
	public LoginSessionResult login(LoginSessionParams params) {

		LoginSessionResult result = new LoginSessionResult();

		// Second: check the params.username
		if (params.getUserName() != null
				&& params.getUserName().compareTo("") != 0) {
			// Third: check the params.userpassword
			if (params.getUserPassword() != null
					&& params.getUserPassword().compareTo("") != 0) {

				COSSessionMM cossmng = null;
				Context initialContext;

				try {
					initialContext = new InitialContext();
					cossmng = (COSSessionMM) initialContext
							.lookup("java:app/cos/COSSessionMM");

					result = cossmng.initializeModules(params.getUserName(),
							params.getUserPassword());

				} catch (NamingException e) {
					LOGGER.error(e);
					result.setValidation(false);
					result.setUserId("-1");
					result.setMessage("> Internal Server Error");
				}
			} else {
				LOGGER.error("Not found o Empty userpassword Error");
				result.setValidation(false);
				result.setUserId("-1");
				result.setMessage("> Not found o Empty userpassword Error");
			}
		} else {
			LOGGER.error("Not found o Empty username Error");
			result.setValidation(false);
			result.setUserId("-1");
			result.setMessage("> Not found o Empty username Error");

		}

		return result;
	}
	
	
	public LogoutSessionResult logout(LogoutSessionParams params) {

		LogoutSessionResult result = new LogoutSessionResult();
		
		//First: check the params.userid
	  	if(params.getUserId()!=null && params.getUserId().compareTo("") != 0){ 
		
		  		COSSessionMM cossmng = null;
				Context initialContext;
				
				try	{
					initialContext = new InitialContext();
					cossmng = (COSSessionMM)initialContext.lookup("java:app/cos/COSSessionMM");

					result = cossmng.destroyModules(params.getUserId());
					
				} catch (NamingException e) {
					LOGGER.error(e);
					result.setDeleted(false);
					result.setMessage("> Internal Server Error");
				}
		} else {
			LOGGER.error("Not found o Empty userId Error");
			result.setDeleted(false);
			result.setMessage("> Not found o Empty userId Error");
		}
	
		return result;
	}
	
	public InitUserArchitectureSessionResult initUserArchitecture(InitUserArchitectureSessionParams params) {
		InitUserArchitectureSessionResult result = new InitUserArchitectureSessionResult();
		
		if (params.getUserId() != null && params.getUserId().compareTo("") != 0) {
			if (params.getInteraction().getDeviceType() != null && params.getInteraction().getInteractionType() != null 
					&& params.getInteraction().getLatitude() != null && params.getInteraction().getLongitude() != null) {
				try	{
					
					Context initialContext = new InitialContext();
					COSSessionMM cossmng = (COSSessionMM)initialContext.lookup("java:app/cos/COSSessionMM");
					result = cossmng.initModelforUsers(params.getUserId(),params.getInteraction());
					
				} catch (NamingException e) {
					LOGGER.error(e);
					result.setInit(false);
					result.setComponentData(null);
					result.setMessage("> Internal Server Error");
				}
			} else {
				LOGGER.error("Not found Interaction Information Error");
				result.setInit(false);
				result.setComponentData(null);
				result.setMessage("> Not found Interaction Information Error");
			}
		} else {
			LOGGER.error("Not found o Empty UserId Error");
			result.setInit(false);
			result.setComponentData(null);
			result.setMessage("> Not found o Empty UserId Error");

		}
		return result;
	}
	public DefaultInitSessionResult defaultInit(){
		DefaultInitSessionResult result = new DefaultInitSessionResult();
		
  		COSSessionMM cossmng = null;
		Context initialContext;
		
		try	{
			initialContext = new InitialContext();
			cossmng = (COSSessionMM)initialContext.lookup("java:app/cos/COSSessionMM");

			result = cossmng.initAnonimous();
			
		} catch (NamingException e) {
			LOGGER.error(e);
			result.setInit(false);
			result.setAnonimousId("-1");
			result.setComponentData(null);
			result.setMessage("> Internal Server Error");
		}
		
			return result;	
	}
}
