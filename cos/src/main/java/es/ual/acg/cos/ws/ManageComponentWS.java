/*
 * ManageComponentWS.java -- Interfaz del web service Manage Component.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * ManageComponentWS.java is part of COScore Community.
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
import javax.jws.WebService;

@WebService(name="ManageComponentWS")
public interface ManageComponentWS {
	
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

