package es.ual.acg.cos.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

import es.ual.acg.cos.ws.types.GetLinksParams;
import es.ual.acg.cos.ws.types.GetLinksResult;


@WebService(name="CommunicationWS")
public interface CommunicationWS {
	@WebMethod(operationName="getLinksComponents", action="getLinksComponents")
	@WebResult(name="result", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true)
	public GetLinksResult getLinksComponents(
			@WebParam(name="params", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) GetLinksParams params);
}

