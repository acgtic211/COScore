package es.ual.acg.cos.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

import es.ual.acg.cos.ws.types.DefaultInitSessionResult;
import es.ual.acg.cos.ws.types.InitUserArchitectureSessionParams;
import es.ual.acg.cos.ws.types.InitUserArchitectureSessionResult;
import es.ual.acg.cos.ws.types.LoginSessionParams;
import es.ual.acg.cos.ws.types.LoginSessionResult;
import es.ual.acg.cos.ws.types.LogoutSessionParams;
import es.ual.acg.cos.ws.types.LogoutSessionResult;

@WebService(name="SessionWS")
public interface SessionWS {
	
	@WebMethod(operationName="login", action="login")
	@WebResult(name="result", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true)
	public LoginSessionResult login(
			@WebParam(name="params", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) LoginSessionParams params);
	
	@WebMethod(operationName="logout", action="logout")
	@WebResult(name="result", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true)
	public LogoutSessionResult logout(
			@WebParam(name="params", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) LogoutSessionParams params);

	@WebMethod(operationName="initUserArchitecture", action="initUserArchitecture")
	@WebResult(name="result", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true)
	public InitUserArchitectureSessionResult initUserArchitecture(
			@WebParam(name="params", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) InitUserArchitectureSessionParams params);	
	
	@WebMethod(operationName="defaultInit", action="defaultInit")
	@WebResult(name="result", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true)
	public DefaultInitSessionResult defaultInit();
	
}
