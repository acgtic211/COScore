package es.ual.acg.cos.controllers;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
/*import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;*/
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import org.apache.xerces.parsers.DOMParser;
//import org.glassfish.jersey.client.ClientConfig;
//import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
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
	/*private WookieConnectorService connectorService = null;
	private String url = "http://acg.ual.es/wookie";
	private String apiKey = "ENIATEST";
	private String dataKey = "";
	
	private String user = "admin";
	private String pass = "3nia" ;*/
	
	private static final Logger LOGGER = Logger.getLogger(ManageWookie.class);
	
	@PostConstruct
	public void initManageWookie() {
		LOGGER.info("[ManageWookie] EJB initializated (Singleton mode)");
	}
	
	/*public WidgetData getOrCreateWidgetInstance(String userID, String widgetID, String  widgetName)	{
LOGGER.info("USERID -> " + userID);
LOGGER.info("WIDGETID -> " + widgetID);
LOGGER.info("WIDGETNAME -> " + widgetName);

	  Client client = ClientBuilder.newClient();
	  WebTarget target = client.target("http://150.214.150.169/wookie/widgetinstances");


	  Form formData = new Form();
	  formData.param("api_key", "TEST");
      formData.param("userid", userID);
      formData.param("widgetid", widgetID);
      formData.param("shareddatakey", widgetName);

	  String response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);

	  WidgetData widgetData = null;
      
      String xml = response;
      DOMParser parser = new DOMParser();
      try {
          parser.parse(new InputSource(new java.io.StringReader(xml)));
          Document doc = parser.getDocument();
LOGGER.info("44444444444");
          //log.info("URL: " + doc.getElementsByTagName("url").item(0).getTextContent());
          //log.info("ID: " + doc.getElementsByTagName("identifier").item(0).getTextContent());
          //log.info("TITLE: " + doc.getElementsByTagName("title").item(0).getTextContent());
          
          String url = doc.getElementsByTagName("url").item(0).getTextContent();
          String identifier = doc.getElementsByTagName("identifier").item(0).getTextContent();
          String title = doc.getElementsByTagName("title").item(0).getTextContent();
          int height = Integer.parseInt(doc.getElementsByTagName("height").item(0).getTextContent());
          int width = Integer.parseInt(doc.getElementsByTagName("width").item(0).getTextContent());
LOGGER.info("5555555555");
          widgetData = new WidgetData(url, identifier, title, height, width);
          //log.info("WidgetData: " + widgetData);
          //String message = doc.getDocumentElement().getTextContent();
          //log.info("XERCES: " + message);
          

      } catch (SAXException e) {
          e.printStackTrace(); 
      } catch (IOException e) {
          e.printStackTrace();
      }
	  return widgetData;
		
	}*/
	
	@SuppressWarnings("unchecked")
  public WidgetData getOrCreateWidgetInstance(String userID, String widgetID, String widgetName) throws Exception {
		
    String result = "[ManageWookie - getOrCreateWidgetInstance] Error";

    //Invoke REST service to create new widget instances    
    ClientConfig config = new DefaultClientConfig();
    Client client = Client.create(config);
    WebResource webResource = client.resource(UriBuilder.fromUri("http://150.214.150.169/wookie/widgetinstances").build());        
    @SuppressWarnings("rawtypes")
    MultivaluedMap formData = new MultivaluedMapImpl();
    formData.add("api_key", "TEST");
    formData.add("userid", userID);
    formData.add("widgetid", widgetID);
    formData.add("shareddatakey", widgetName);
    ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, formData);
    result = response.getEntity(String.class);
        
    WidgetData widgetData = null;
    
    String xml = result;
    DOMParser parser = new DOMParser();
    //try {
    parser.parse(new InputSource(new java.io.StringReader(xml)));
    Document doc = parser.getDocument();
    
    String url = doc.getElementsByTagName("url").item(0).getTextContent();
    String identifier = doc.getElementsByTagName("identifier").item(0).getTextContent();
    String title = doc.getElementsByTagName("title").item(0).getTextContent();
    int height = Integer.parseInt(doc.getElementsByTagName("height").item(0).getTextContent());
    int width = Integer.parseInt(doc.getElementsByTagName("width").item(0).getTextContent());
    
    widgetData = new WidgetData(url, identifier, title, height, width);
    LOGGER.info("WidgetData: " + widgetData + " - url -> " + url);          

    /*} catch (SAXException e) {
        LOGGER.error(e); 
    } catch (IOException e) {
        LOGGER.error(e); 
    }*/
            
    return widgetData;
    
  }
	

}
