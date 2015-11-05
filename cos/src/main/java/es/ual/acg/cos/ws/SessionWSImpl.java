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
