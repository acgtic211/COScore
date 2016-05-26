/*
 * SessionWS.java -- Interfaz del web service Session.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * SessionWS.java is part of COScore Community.
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
