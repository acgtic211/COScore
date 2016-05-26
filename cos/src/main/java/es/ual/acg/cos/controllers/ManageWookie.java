/*
 * ManageWookie.java -- Gestiona la base de datos de componentes e instancias de tipo widget.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * ManageWookie.java is part of COScore Community.
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
package es.ual.acg.cos.controllers;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import org.apache.xerces.parsers.DOMParser;
import org.jboss.logging.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import es.ual.acg.cos.wookie.WidgetData;

@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Singleton
@Lock(LockType.READ)
public class ManageWookie
{
	private static final Logger LOGGER = Logger.getLogger(ManageWookie.class);
	
	@PostConstruct
	public void initManageWookie() {
		LOGGER.info("[ManageWookie] EJB initializated (Singleton mode)");
	}
	
	@SuppressWarnings("unchecked")
	public WidgetData getOrCreateWidgetInstance(String userID, String componentName, String componentAlias) throws Exception {
			
	    String result = "[ManageWookie - getOrCreateWidgetInstance] Error";
	
	    //Invoke REST service to create new widget instances    
	    ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    WebResource webResource = client.resource(UriBuilder.fromUri("http://150.214.150.169/wookie/widgetinstances").build());        
	    @SuppressWarnings("rawtypes")
	    MultivaluedMap formData = new MultivaluedMapImpl();
	    formData.add("api_key", "TEST");
	    formData.add("userid", userID);
	    formData.add("widgetid", componentName);
	    
		Calendar dateTime = Calendar.getInstance();
	    String aux = componentAlias+"/"+dateTime.get(Calendar.DATE)+"/"+dateTime.get(Calendar.MONTH)
 				  +"/"+dateTime.get(Calendar.YEAR)+"-"+dateTime.get(Calendar.HOUR)
 				  +":"+dateTime.get(Calendar.MINUTE)+":"+dateTime.get(Calendar.SECOND)
 				  +":"+dateTime.get(Calendar.MILLISECOND);

	    formData.add("shareddatakey", aux);
	    ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, formData);
	    result = response.getEntity(String.class);
	        
	    WidgetData widgetData = null;
	    
	    String xml = result;
	    DOMParser parser = new DOMParser();
	
	    parser.parse(new InputSource(new java.io.StringReader(xml)));
	    Document doc = parser.getDocument();
	    
	    String url = doc.getElementsByTagName("url").item(0).getTextContent();
	    String identifier = doc.getElementsByTagName("identifier").item(0).getTextContent();
	    String title = doc.getElementsByTagName("title").item(0).getTextContent();
	    int height = Integer.parseInt(doc.getElementsByTagName("height").item(0).getTextContent());
	    int width = Integer.parseInt(doc.getElementsByTagName("width").item(0).getTextContent());
	    
	    widgetData = new WidgetData(url, identifier, title, height, width);
	    LOGGER.info("WidgetData: " + widgetData + " - url -> " + url);          
	
	    return widgetData;
	}
}
