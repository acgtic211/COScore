/*
 * ManageArchitecturesWSImpl.java -- Web service Manage Architectures - Servicio encargado de manejar los modelos de arquitectura de las aplicaciones.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * ManageArchitecturesWSImpl.java is part of COScore Community.
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

import es.ual.acg.cos.controllers.ManageArchitectures;

@WebService(endpointInterface = "es.ual.acg.cos.ws.ManageArchitecturesWS")
public class ManageArchitecturesWSImpl implements ManageArchitecturesWS {
	private static final Logger log = Logger.getLogger(ManageArchitecturesWSImpl.class);

	public String exportCAMFromString(String camFileType, String camFileString)	{
		String result = "No results obtained";

		ManageArchitectures mngArch = null;
		try {
			Context initialContext = new InitialContext();
			mngArch = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
			//mngArch = (es.ual.acg.cos.beans.ManageArchitecturesBean)initialContext.lookup("java:global/cos/ManageArchitecturesBean");
			result = mngArch.exportCAMFromString(camFileType, camFileString);
		}
		catch (NamingException e) {
			log.error(e.getMessage());
		}
		
		log.info("[ManageArchitectures - exportCAMFromString] result: " + result);

		return result;
	}
	
	public String exportAAMFromString(String aamFileType, String aamFileString)
	{
		String result = "No results obtained";

		es.ual.acg.cos.controllers.ManageArchitectures mngArch = null;
		try
		{
			Context initialContext = new InitialContext();
			mngArch = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
			result = mngArch.exportAAMFromString(aamFileType, aamFileString);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info("[ManageArchitectures - exportAAMFromString] result: " + result);

		return result;
	}
	
	public String withdrawCAM(String camID)
	{
		String result = "No results obtained";

		es.ual.acg.cos.controllers.ManageArchitectures mngArch = null;
		try
		{
			Context initialContext = new InitialContext();
			mngArch = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
			result = mngArch.withdrawCAM(camID);
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
		
		log.info("[ManageArchitectures - withdrawCAM] result: " + result);

		return result;
	}

}

