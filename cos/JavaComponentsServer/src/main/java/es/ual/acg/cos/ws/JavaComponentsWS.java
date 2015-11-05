package es.ual.acg.cos.ws;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import es.ual.acg.cos.java.JavaComponentResponse;

@WebService(name="JavaComponentsWS")
public interface JavaComponentsWS {
		
	@WebMethod(operationName="getJavaComponent", action="getJavaComponent")
	public JavaComponentResponse getJavaComponent(
		@WebParam(name="userID", targetNamespace="http://ws.cos.acg.ual.es/") String userID,
		@WebParam(name="applicationId", targetNamespace="http://ws.cos.acg.ual.es/") String applicationId,
		@WebParam(name="javaComponentID", targetNamespace="http://ws.cos.acg.ual.es/") String javaComponentID,
		@WebParam(name="javaComponentName", targetNamespace="http://ws.cos.acg.ual.es/") String javaComponentName
	);	
	
	@WebMethod(operationName="deleteJavaComponent", action="deleteJavaComponent")
	public boolean deleteJavaComponent(
		@WebParam(name="javaComponentID", targetNamespace="http://ws.cos.acg.ual.es/") String javaComponentID
	);	
	
	@WebMethod(operationName="addJavaComponent", action="addJavaComponent")
	public boolean addJavaComponent(
		@WebParam(name="javaComponentID", targetNamespace="http://ws.cos.acg.ual.es/") String javaComponentID,
		@WebParam(name="jar", targetNamespace="http://ws.cos.acg.ual.es/") DataHandler jar
	);
	
	/*@WebMethod(operationName="hola", action="hola")
	public String hola(
		@WebParam(name="nombre", targetNamespace="http://ws.cos.acg.ual.es/") String nombre
	);*/
	
}

