package es.ual.acg.cos.ws;

import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.logging.Logger;

import es.ual.acg.cos.modules.TMM;
import es.ual.acg.cos.ws.types.GetLinksParams;
import es.ual.acg.cos.ws.types.GetLinksResult;


@WebService(endpointInterface = "es.ual.acg.cos.ws.CommunicationWS")
public class CommunicationWSImpl implements CommunicationWS{
	
	private static final Logger LOGGER = Logger.getLogger(CommunicationWSImpl.class);
	
	public GetLinksResult getLinksComponents(GetLinksParams params) {
		
		GetLinksResult result = new GetLinksResult();
		result.setGotten(false);
		result.setPortList(null);

		if (params.getUserId() != null
				&& params.getUserId().compareTo("") != 0) {
			if (params.getComponentInstance() != null
					&& params.getComponentInstance().compareTo("") != 0) {
				if (params.getPortId() != null
						&& params.getPortId().compareTo("") != 0) {
					
					TMM tmm = null;
					Context initialContext;

					try {
						initialContext = new InitialContext();
						tmm = (TMM) initialContext.lookup("java:app/cos/TMM");

						result = tmm.calculateConnectedPorts(params.getUserId(), params.getComponentInstance(), params.getPortId());
					
					} catch (Exception e) {
						LOGGER.error(e);
						result.setMessage("> Internal Server Error");
					}
				} else {
					LOGGER.error("Not found o Empty Port Error");
					result.setMessage("> Not found o Empty Port Error");
				}
			} else {
				LOGGER.error("Not found o Empty Component Instance Error");
				result.setMessage("> Not found o Empty Component Instance Error");
			}
		} else {
			LOGGER.error("Not found o Empty userid Error");
			result.setMessage("> Not found o Empty username Error");
		}
		return result;
	}

}
	