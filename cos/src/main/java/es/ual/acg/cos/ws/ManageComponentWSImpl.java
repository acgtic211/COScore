/*
 * ManageComponentWSImpl.java -- Web service Manage Component - Servicio encargado de manejar las especificaciones de componentes que constituyen las aplicaciones mashup.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * ManageComponentWSImpl.java is part of COScore Community.
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

import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import es.ual.acg.cos.controllers.ManageComponentSpecifications;


@WebService(endpointInterface = "es.ual.acg.cos.ws.ManageComponentWS")
public class ManageComponentWSImpl implements ManageComponentWS
{
	private static final Logger LOGGER = Logger.getLogger(ManageComponentWSImpl.class);

	public String exportCCFromURI(String ccFileType, String ccFileURI)
	{
		String result = "No results obtained";

		ManageComponentSpecifications register = null;
		try
		{
			Context initialContext = new InitialContext();
			register = (ManageComponentSpecifications)initialContext.lookup("java:app/cos/ManageComponentSpecification");
			result = register.exportCCFromURI(ccFileType, ccFileURI);
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
		
		LOGGER.info("[Register - exportFromURI] result: " + result);

		return result;
	}
	
	public String exportCCFromString(String ccFileType, String ccFileString)
	{
		String result = "No results obtained";

		ManageComponentSpecifications register = null;
		try
		{
			Context initialContext = new InitialContext();
			register = (ManageComponentSpecifications)initialContext.lookup("java:app/cos/ManageComponentSpecification");
			result = register.exportCCFromString(ccFileType, ccFileString);
		}
		catch (NamingException e) {
			LOGGER.error(e.getStackTrace());
		}
		
		LOGGER.info("[Register - exportFromString] result: " + result);

		return result;
	}
	
	public String exportACFromString(String acFileType, String acFileString)
	{
		String result = "No results obtained";

		ManageComponentSpecifications register = null;
		try
		{
			Context initialContext = new InitialContext();
			register = (ManageComponentSpecifications)initialContext.lookup("java:app/cos/ManageComponentSpecification");
			result = register.exportACFromString(acFileType, acFileString);
		}
		catch (NamingException e) {
			LOGGER.error(e.getStackTrace());
			LOGGER.error(e);
		}
		
		LOGGER.info("[Register - exportACFromString] result: " + result);

		return result;
	}	

	public String withdrawCC(String ccID)
	{
		String result = "No results obtained";

		ManageComponentSpecifications register = null;
		try
		{
			Context initialContext = new InitialContext();
			register = (ManageComponentSpecifications)initialContext.lookup("java:app/cos/ManageComponentSpecification");
			result = register.withdrawCC(ccID);
		}
		catch (NamingException e) {
			LOGGER.error(e.getStackTrace());
			LOGGER.error(e);
		}
		
		LOGGER.info("[Register - withdrawCC] result: " + result);

		return result;
	}
	
	public String withdrawAC(String acID)
	{
		String result = "No results obtained";

		ManageComponentSpecifications register = null;
		try
		{
			Context initialContext = new InitialContext();
			register = (ManageComponentSpecifications)initialContext.lookup("java:app/cos/ManageComponentSpecification");
			result = register.withdrawAC(acID);
		}
		catch (NamingException e) {
			LOGGER.error(e.getStackTrace());
		}
		
		LOGGER.info("[Register - withdrawAC] result: " + result);

		return result;
	}
	
	public void registerExampleComponents()
	{
		ManageComponentSpecifications register = null;
		try
		{
			Context initialContext = new InitialContext();
			register = (ManageComponentSpecifications)initialContext.lookup("java:app/cos/ManageComponentSpecification");
			register.registerExampleComponents();
		}
		catch (NamingException e) {
			LOGGER.error(e.getStackTrace());
		}
		
		LOGGER.info("[Register - registerExampleComponents] OK");
	}

	@Override
	public String exportCCFromParams(String componentName, String componentAlias,
			String componentDescription, String entityId, String entityName,
			String entityDescription, String contactDescription,
			String personName, String email, String phone, String address,
			String versionId, String versionDate, String programmingLanguage,
			String platformType, String repositoryId, String repositoryType,
			String repositoryURI, String componentURI, String[] propertyId,
			String[] propertyValue, boolean[] isEditable,
			String dependencyInterfaceId, String[] requiredProvided, String[] interfaceId,
			String[] interfaceDescription, String[] anyUri) {
		
		ManageComponentSpecifications register = null;
		try
		{

			Context initialContext = new InitialContext();
			register = (ManageComponentSpecifications)initialContext.lookup("java:app/cos/ManageComponentSpecification");

			register.exportCCFromParams(componentName, componentAlias, componentDescription, entityId, entityName, entityDescription, contactDescription,
					personName, email, phone, address,
					versionId, versionDate, programmingLanguage,
					platformType, repositoryId, repositoryType,
					repositoryURI, componentURI, propertyId,
					propertyValue, isEditable,
					dependencyInterfaceId, requiredProvided, interfaceId,
					interfaceDescription, anyUri);
		}
		catch (NamingException e) {
			LOGGER.error(e.getStackTrace());
		}
		
		LOGGER.info("[Register - registerExampleComponents] OK");
		
		return null;
	}

}

