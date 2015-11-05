
package es.ual.acg.cos.ws;

import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import es.ual.acg.cos.controllers.ManageRegister;


@WebService(endpointInterface = "es.ual.acg.cos.ws.RegisterWS")
public class RegisterWSImpl implements RegisterWS
{
	private static final Logger LOGGER = Logger.getLogger(RegisterWSImpl.class);

	public String exportCCFromURI(String ccFileType, String ccFileURI)
	{
		String result = "No results obtained";

		ManageRegister register = null;
		try
		{
			Context initialContext = new InitialContext();
			register = (ManageRegister)initialContext.lookup("java:app/cos/ManageRegister");
			result = register.exportCCFromURI(ccFileType, ccFileURI);
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
		
		LOGGER.info("[Register - exportFromURI] result: " + result);

		return result;
	}
	
	public String exportCCFromString(String ccFileType, String ccFileString)
	{
		String result = "No results obtained";

		ManageRegister register = null;
		try
		{
			Context initialContext = new InitialContext();
			register = (ManageRegister)initialContext.lookup("java:app/cos/ManageRegister");
			result = register.exportCCFromString(ccFileType, ccFileString);
		}
		catch (NamingException e) {
			LOGGER.error(e.getStackTrace());
		}
		
		LOGGER.info("[Register - exportFromString] result: " + result);

		return result;
	}
	
	public String exportACFromString(String acFileType, String acFileString)
	{
		String result = "No results obtained";

		ManageRegister register = null;
		try
		{
			Context initialContext = new InitialContext();
			register = (ManageRegister)initialContext.lookup("java:app/cos/ManageRegister");
			result = register.exportACFromString(acFileType, acFileString);
		}
		catch (NamingException e) {
			LOGGER.error(e.getStackTrace());
			LOGGER.error(e);
		}
		
		LOGGER.info("[Register - exportACFromString] result: " + result);

		return result;
	}	

	public String withdrawCC(String ccID)
	{
		String result = "No results obtained";

		ManageRegister register = null;
		try
		{
			Context initialContext = new InitialContext();
			register = (ManageRegister)initialContext.lookup("java:app/cos/ManageRegister");
			result = register.withdrawCC(ccID);
		}
		catch (NamingException e) {
			LOGGER.error(e.getStackTrace());
			LOGGER.error(e);
		}
		
		LOGGER.info("[Register - withdrawCC] result: " + result);

		return result;
	}
	
	public String withdrawAC(String acID)
	{
		String result = "No results obtained";

		ManageRegister register = null;
		try
		{
			Context initialContext = new InitialContext();
			register = (ManageRegister)initialContext.lookup("java:app/cos/ManageRegister");
			result = register.withdrawAC(acID);
		}
		catch (NamingException e) {
			LOGGER.error(e.getStackTrace());
		}
		
		LOGGER.info("[Register - withdrawAC] result: " + result);

		return result;
	}
	
	public void registerExampleComponents()
	{
		ManageRegister register = null;
		try
		{
			Context initialContext = new InitialContext();
			register = (ManageRegister)initialContext.lookup("java:app/cos/ManageRegister");
			register.registerExampleComponents();
		}
		catch (NamingException e) {
			LOGGER.error(e.getStackTrace());
		}
		
		LOGGER.info("[Register - registerExampleComponents] OK");
	}

	@Override
	public String exportCCFromParams(String componentName, String componentAlias,
			String componentDescription, String entityId, String entityName,
			String entityDescription, String contactDescription,
			String personName, String email, String phone, String address,
			String versionId, String versionDate, String programmingLanguage,
			String platformType, String repositoryId, String repositoryType,
			String repositoryURI, String componentURI, String[] propertyId,
			String[] propertyValue, boolean[] isEditable,
			String dependencyInterfaceId, String[] requiredProvided, String[] interfaceId,
			String[] interfaceDescription, String[] anyUri) {
		
		ManageRegister register = null;
		try
		{

			Context initialContext = new InitialContext();
			register = (ManageRegister)initialContext.lookup("java:app/cos/ManageRegister");

			register.exportCCFromParams(componentName, componentAlias, componentDescription, entityId, entityName, entityDescription, contactDescription,
					personName, email, phone, address,
					versionId, versionDate, programmingLanguage,
					platformType, repositoryId, repositoryType,
					repositoryURI, componentURI, propertyId,
					propertyValue, isEditable,
					dependencyInterfaceId, requiredProvided, interfaceId,
					interfaceDescription, anyUri);
		}
		catch (NamingException e) {
			LOGGER.error(e.getStackTrace());
		}
		
		LOGGER.info("[Register - registerExampleComponents] OK");
		
		return null;
	}

}

