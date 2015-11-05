package es.ual.acg.cos.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="RegisterWS")
public interface RegisterWS {
	
	@WebMethod(operationName="exportCCFromURI", action="exportCCFromURI")
	public String exportCCFromURI(
		@WebParam(name="ccFileType", targetNamespace="http://ws.cos.acg.ual.es/") String ccFileType,	
		@WebParam(name="ccFileURI", targetNamespace="http://ws.cos.acg.ual.es/") String ccFileURI);
	
	@WebMethod(operationName="exportCCFromString", action="exportCCFromString")
	public String exportCCFromString(
		@WebParam(name="ccFileType", targetNamespace="http://ws.cos.acg.ual.es/") String ccFileType,	
		@WebParam(name="ccFileString", targetNamespace="http://ws.cos.acg.ual.es/") String ccFileString);
	
	@WebMethod(operationName="exportCCFromParams", action="exportCCFromParams")
	public String exportCCFromParams(
		@WebParam(name="componentName", targetNamespace="http://ws.cos.acg.ual.es/") String componentName,
		@WebParam(name="componentAlias", targetNamespace="http://ws.cos.acg.ual.es/") String componentAlias,
		@WebParam(name="componentDescription", targetNamespace="http://ws.cos.acg.ual.es/") String componentDescription,
		@WebParam(name="entityId", targetNamespace="http://ws.cos.acg.ual.es/") String entityId,
		@WebParam(name="entityName", targetNamespace="http://ws.cos.acg.ual.es/") String entityName,
		@WebParam(name="entityDescription", targetNamespace="http://ws.cos.acg.ual.es/") String entityDescription,
		@WebParam(name="contactDescription", targetNamespace="http://ws.cos.acg.ual.es/") String contactDescription,
		@WebParam(name="personName", targetNamespace="http://ws.cos.acg.ual.es/") String personName,
		@WebParam(name="email", targetNamespace="http://ws.cos.acg.ual.es/") String email,
		@WebParam(name="phone", targetNamespace="http://ws.cos.acg.ual.es/") String phone,
		@WebParam(name="address", targetNamespace="http://ws.cos.acg.ual.es/") String address,
		@WebParam(name="versionId", targetNamespace="http://ws.cos.acg.ual.es/") String versionId,
		@WebParam(name="versionDate", targetNamespace="http://ws.cos.acg.ual.es/") String versionDate,
		@WebParam(name="programmingLanguage", targetNamespace="http://ws.cos.acg.ual.es/") String programmingLanguage,
		@WebParam(name="platformType", targetNamespace="http://ws.cos.acg.ual.es/") String platformType,
		@WebParam(name="repositoryId", targetNamespace="http://ws.cos.acg.ual.es/") String repositoryId,
		@WebParam(name="repositoryType", targetNamespace="http://ws.cos.acg.ual.es/") String repositoryType,
		@WebParam(name="repositoryURI", targetNamespace="http://ws.cos.acg.ual.es/") String repositoryURI,
		@WebParam(name="componentURI", targetNamespace="http://ws.cos.acg.ual.es/") String componentURI,
		@WebParam(name="propertyId", targetNamespace="http://ws.cos.acg.ual.es/") String[] propertyId,
		@WebParam(name="propertyValue", targetNamespace="http://ws.cos.acg.ual.es/") String[] propertyValue,
		@WebParam(name="isEditable", targetNamespace="http://ws.cos.acg.ual.es/") boolean[] isEditable,
		@WebParam(name="dependencyInterfaceId", targetNamespace="http://ws.cos.acg.ual.es/") String dependencyInterfaceId,
		@WebParam(name="requiredProvided", targetNamespace="http://ws.cos.acg.ual.es/") String[] requiredProvided,
		@WebParam(name="interfaceId", targetNamespace="http://ws.cos.acg.ual.es/") String[] interfaceId,
		@WebParam(name="interfaceDescription", targetNamespace="http://ws.cos.acg.ual.es/") String[] interfaceDescription,
		@WebParam(name="anyUri", targetNamespace="http://ws.cos.acg.ual.es/") String[] anyUri
	);
	
	@WebMethod(operationName="exportACFromString", action="exportACFromString")
	public String exportACFromString(
		@WebParam(name="acFileType", targetNamespace="http://ws.cos.acg.ual.es/") String acFileType,	
		@WebParam(name="acFileString", targetNamespace="http://ws.cos.acg.ual.es/") String acFileString);
	
	@WebMethod(operationName="withdrawCC", action="withdrawCC")
	public String withdrawCC(
		@WebParam(name="ccID", targetNamespace="http://ws.cos.acg.ual.es/") String ccID);
	
	@WebMethod(operationName="withdrawAC", action="withdrawAC")
	public String withdrawAC(
		@WebParam(name="acID", targetNamespace="http://ws.cos.acg.ual.es/") String acID);
	
	@WebMethod(operationName="registerExampleComponents", action="registerExampleComponents")
	public void registerExampleComponents();

	
}

