package es.ual.acg.cos.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

import es.ual.acg.cos.ws.types.UpdateArchitectureParams;
import es.ual.acg.cos.ws.types.UpdateArchitectureResult;

@WebService(name="ComponentWS")
public interface ComponentWS {
	
//	@WebMethod(operationName="deleteComponent", action="deleteComponent")
//	@WebResult(name="result", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true)
//	public DeleteComponentResult deleteComponent(
//			@WebParam(name="params", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) DeleteComponentParams params);

	@WebMethod(operationName="updateArchitecture", action="updateArchitecture")
	@WebResult(name="result", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true)
	public UpdateArchitectureResult updateArchitecture(
			@WebParam(name="params", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) UpdateArchitectureParams params);
}