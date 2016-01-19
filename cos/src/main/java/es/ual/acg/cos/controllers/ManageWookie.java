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
