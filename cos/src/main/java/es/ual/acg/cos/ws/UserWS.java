/*
 * UserWS.java -- Interfaz del web service User.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * UserWS.java is part of COScore Community.
 * 
 * COScore Community is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/gpl.html> or
 * see  <https://github.com/acgtic211/COScore-Community>.  Or write to
 * the Free Software Foundation, I51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1335, USA.
 *
 *  Authors: Alfredo Valero Rodríguez  Software Developer 
 *           Javier Criado Rodríguez   Doctor/Researcher/Software Developer
 *           Jesús Vallecillos Ruíz    Pre-doctoral scholarship holders/Researcher/Software Developer
 *    Group: ACG 		               Applied Computing Group
 * Internet: http://acg.ual.es/        
 *   E-mail: acg.tic211@ual.es        
 *   Adress: Edif. Científico Técnico, CITE-III
 *           Universidad de Almería
 *           Almeria, España
 *           04120
*/
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
import es.ual.acg.cos.ws.types.QueryProfileResult;
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

	
	@WebMethod(operationName="queryProfile", action="queryProfile")
	@WebResult(name="result", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) 
	public QueryProfileResult queryProfile(
		@WebParam(name="privatekey", targetNamespace="http://ws.cos.acg.ual.es/") @XmlElement(required=true) String privatekey);

}
