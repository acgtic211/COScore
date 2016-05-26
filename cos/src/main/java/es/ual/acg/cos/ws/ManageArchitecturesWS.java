/*
 * ManageArchitecturesWS.java -- Interfaz del web service Manage Architecture.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * ManageArchitecturesWS.java is part of COScore Community.
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

