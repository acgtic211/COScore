package es.ual.acg.cos.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


@WebService(name="ManageArchitectures")
public interface ManageArchitecturesWS
{
	@WebMethod(operationName="exportAAMFromString", action="exportAAMFromString")
	public String exportAAMFromString(
		@WebParam(name="aamFileType", targetNamespace="http://ws.cos.acg.ual.es/") String aamFileType,	
		@WebParam(name="aamFileString", targetNamespace="http://ws.cos.acg.ual.es/") String aamFileString);
	
	@WebMethod(operationName="exportCAMFromString", action="exportCAMFromString")
	public String exportCAMFromString(
		@WebParam(name="camFileType", targetNamespace="http://ws.cos.acg.ual.es/") String camFileType,	
		@WebParam(name="camFileString", targetNamespace="http://ws.cos.acg.ual.es/") String camFileString);
	
	@WebMethod(operationName="withdrawCAM", action="withdrawCAM")
	public String withdrawCAM(
		@WebParam(name="camID", targetNamespace="http://ws.cos.acg.ual.es/") String camID);	
}

