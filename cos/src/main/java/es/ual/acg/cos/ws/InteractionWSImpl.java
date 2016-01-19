package es.ual.acg.cos.ws;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.logging.Logger;

import es.ual.acg.cos.modules.IMM;
import es.ual.acg.cos.types.InterModulesData;
import es.ual.acg.cos.ws.types.RegisterInteractionParams;
import es.ual.acg.cos.ws.types.RegisterInteractionResult;

@WebService(endpointInterface = "es.ual.acg.cos.ws.InteractionWS")
public class InteractionWSImpl implements InteractionWS{

	
	private static final Logger LOGGER = Logger.getLogger(InteractionWSImpl.class);
	
	public RegisterInteractionResult registerInteraction(RegisterInteractionParams params){
		
		RegisterInteractionResult result = new RegisterInteractionResult();
		InterModulesData resultIMMforError = new InterModulesData();

		try{
			Context initialContext = new InitialContext();
			IMM imm = (es.ual.acg.cos.modules.IMM)initialContext.lookup("java:app/cos/IMM"); //Este proceso no es necesario ya se tiene el cam, lo dejamos por si acaso
			
			//Fecha y hora
			Calendar calendar = Calendar.getInstance();
			 
			String dateTime = calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.MONTH)
					   				  +"/"+calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.HOUR)
					   				  +":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND)
					   				  +":"+calendar.get(Calendar.MILLISECOND);

			//Se puede producir un error al dejar las etiquetas vacias de los parametros de entrada del servicio web
			//Corregimos este fallo ponniendo a null los que corresponda
			if(params.getGroupComponent() != null){
				if(params.getGroupComponent().get(0) == null){
					params.setGroupComponent(null);
				}else{
					if(params.getGroupComponent().get(0) != null) {
						if(params.getGroupComponent().get(0).equalsIgnoreCase("")){
							params.setGroupComponent(null);
						}
					}
				}
			}
			if(params.getUngroupComponent() != null){
				if(params.getUngroupComponent().get(0) == null){
					params.setUngroupComponent(null);
				}else{
					if(params.getUngroupComponent().get(0) != null) {
						if(params.getUngroupComponent().get(0).equalsIgnoreCase("")){
							params.setUngroupComponent(null);
						}
					}
				}
			}
			if(params.getCotsget()!= null) {
				if(params.getCotsget().get(0).getInstanceId() == null) {
					params.setCotsget(null);
				}
			}
			//-----------	
			List<String> groupComponent = new ArrayList<String>();
			List<String> ungroupComponent = new ArrayList<String>();
			
			if(params.getUngroupComponent() != null){
				for(int i = 0; i < params.getUngroupComponent().size(); i++) {
					ungroupComponent.add(params.getUngroupComponent().get(i));
				}
			}else{
				ungroupComponent = null;
			}
			if(params.getGroupComponent() != null){
				for(int i = 0; i < params.getGroupComponent().size(); i++) {
					groupComponent.add(params.getGroupComponent().get(i));
				}
			}else{
				groupComponent = null;
			}

			//------
			if (params.getUserId() != null && params.getUserId().compareTo("") != 0) {
				if (params.getOperationPerformed() != null && params.getOperationPerformed().compareTo("") != 0) {
					resultIMMforError = imm.registerinteraction(params.getNewSession(), params.getInteraction().getDeviceType(), 
											params.getInteraction().getInteractionType(), dateTime, params.getUserId(),
											params.getInteraction().getLatitude(), params.getInteraction().getLongitude(),
											params.getOperationPerformed(), params.getComponentId(), groupComponent,
											ungroupComponent, params.getCotsget()); 
					if(resultIMMforError.getValue().equalsIgnoreCase("-1")){
						result.setRegistered(false);
						result.setMessage(resultIMMforError.getMessage());
					}else{
						result.setRegistered(true);
						result.setMessage(resultIMMforError.getMessage());
					}
				}else{
					LOGGER.error("Not found o Empty Operation Performed");
					result.setMessage("> Not found o Empty Operation Performed");
					result.setRegistered(false);

				}
			}else{
				LOGGER.error("Not found o Empty userId Error");
				result.setMessage("> Not found o Empty userId Error");
				result.setRegistered(false);
			}
		} catch (Exception e) {
			LOGGER.error(e);
			result.setRegistered(false);
			result.setMessage("> Error in Register Interaction " + e);
		}
		
		return result;
	}
}
