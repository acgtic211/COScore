package es.ual.acg.cos.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

import es.ual.acg.cos.ws.types.RegisterInteractionParams;
import es.ual.acg.cos.ws.types.RegisterInteractionResult;


@WebService(name="InteractionWS")
public interface InteractionWS {
	
	@WebMethod(operationName="registerInteraction", action="registerInteraction")
	@WebResult(name="result", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true)	
	public RegisterInteractionResult registerInteraction(
			@WebParam(name="params", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) RegisterInteractionParams params);
}