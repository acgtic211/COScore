package es.ual.acg.cos.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

import es.ual.acg.cos.ws.types.CreateUserParams;
import es.ual.acg.cos.ws.types.CreateUserResult;
import es.ual.acg.cos.ws.types.DeleteUserParams;
import es.ual.acg.cos.ws.types.DeleteUserResult;
import es.ual.acg.cos.ws.types.QueryUserParams;
import es.ual.acg.cos.ws.types.QueryUserResult;
import es.ual.acg.cos.ws.types.UpdateUserParams;
import es.ual.acg.cos.ws.types.UpdateUserResult;

@WebService(name="UserWS")
public interface UserWS {
	
	@WebMethod(operationName="queryUser", action="queryUser")
	@WebResult(name="result", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) 
	public QueryUserResult queryUser(
		@WebParam(name="params", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) QueryUserParams params,
		@WebParam(name="privatekey", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) String privatekey);

  @WebMethod(operationName="createUser", action="createUser")
  @WebResult(name="result", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) 
  public CreateUserResult createUser(
    @WebParam(name="params", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) CreateUserParams params,
    @WebParam(name="privatekey", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) String privatekey);
	
	@WebMethod(operationName="deleteUser", action="deleteUser")
	@WebResult(name="result", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) 
	public DeleteUserResult deleteUser(
		@WebParam(name="params", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) DeleteUserParams params,
		@WebParam(name="privatekey", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) String privatekey);
	
	@WebMethod(operationName="updateUser", action="updateUser")
	@WebResult(name="result", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) 
	public UpdateUserResult updateUser(
		@WebParam(name="params", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) UpdateUserParams params,
		@WebParam(name="privatekey", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) String privatekey);

}
