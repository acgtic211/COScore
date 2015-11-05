
package es.ual.acg.cos.ws;

import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import es.ual.acg.cos.controllers.ManageArchitectures;

@WebService(endpointInterface = "es.ual.acg.cos.ws.ManageArchitecturesWS")
public class ManageArchitecturesWSImpl implements ManageArchitecturesWS {
	private static final Logger log = Logger.getLogger(ManageArchitecturesWSImpl.class);

	public String exportCAMFromString(String camFileType, String camFileString)	{
		String result = "No results obtained";

		ManageArchitectures mngArch = null;
		try {
			Context initialContext = new InitialContext();
			mngArch = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
			//mngArch = (es.ual.acg.cos.beans.ManageArchitecturesBean)initialContext.lookup("java:global/cos/ManageArchitecturesBean");
			result = mngArch.exportCAMFromString(camFileType, camFileString);
		}
		catch (NamingException e) {
			log.error(e.getMessage());
		}
		
		log.info("[ManageArchitectures - exportCAMFromString] result: " + result);

		return result;
	}
	
	public String exportAAMFromString(String aamFileType, String aamFileString)
	{
		String result = "No results obtained";

		es.ual.acg.cos.controllers.ManageArchitectures mngArch = null;
		try
		{
			Context initialContext = new InitialContext();
			mngArch = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
			result = mngArch.exportAAMFromString(aamFileType, aamFileString);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info("[ManageArchitectures - exportAAMFromString] result: " + result);

		return result;
	}
	
	public String withdrawCAM(String camID)
	{
		String result = "No results obtained";

		es.ual.acg.cos.controllers.ManageArchitectures mngArch = null;
		try
		{
			Context initialContext = new InitialContext();
			mngArch = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
			result = mngArch.withdrawCAM(camID);
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
		
		log.info("[ManageArchitectures - withdrawCAM] result: " + result);

		return result;
	}

}

