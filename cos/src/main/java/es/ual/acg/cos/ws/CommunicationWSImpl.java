/*
 * CommunicationWSImpl.java -- Web service Communication - Servicio encargado de controlar los procesos de comunicación entre los componentes.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * CommunicationWSImpl.java is part of COScore Community.
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

import org.jboss.logging.Logger;

import es.ual.acg.cos.modules.TMM;
import es.ual.acg.cos.ws.types.GetLinksParams;
import es.ual.acg.cos.ws.types.GetLinksResult;


@WebService(endpointInterface = "es.ual.acg.cos.ws.CommunicationWS")
public class CommunicationWSImpl implements CommunicationWS{
	
	private static final Logger LOGGER = Logger.getLogger(CommunicationWSImpl.class);
	
	public GetLinksResult getLinksComponents(GetLinksParams params) {
		
		GetLinksResult result = new GetLinksResult();
		result.setGotten(false);
		result.setPortList(null);

		if (params.getUserId() != null
				&& params.getUserId().compareTo("") != 0) {
			if (params.getComponentInstance() != null
					&& params.getComponentInstance().compareTo("") != 0) {
				if (params.getPortId() != null
						&& params.getPortId().compareTo("") != 0) {
					
					TMM tmm = null;
					Context initialContext;

					try {
						initialContext = new InitialContext();
						tmm = (TMM) initialContext.lookup("java:app/cos/TMM");

						result = tmm.calculateConnectedPorts(params.getUserId(), params.getComponentInstance(), params.getPortId());
					
					} catch (Exception e) {
						LOGGER.error(e);
						result.setMessage("> Internal Server Error");
					}
				} else {
					LOGGER.error("Not found o Empty Port Error");
					result.setMessage("> Not found o Empty Port Error");
				}
			} else {
				LOGGER.error("Not found o Empty Component Instance Error");
				result.setMessage("> Not found o Empty Component Instance Error");
			}
		} else {
			LOGGER.error("Not found o Empty userid Error");
			result.setMessage("> Not found o Empty username Error");
		}
		return result;
	}

}
	