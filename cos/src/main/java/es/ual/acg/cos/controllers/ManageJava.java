package es.ual.acg.cos.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import org.jboss.logging.Logger;

import es.ual.acg.cos.java.JavaComponentResponse;
import es.ual.acg.cos.java.JavaComponentsApplication;

@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Singleton
@Lock(LockType.READ)
public class ManageJava {

	private static final Logger LOGGER = Logger.getLogger(ManageJava.class);
	
	@PostConstruct
	public void initManageJava() {
		LOGGER.info("[ManageJava] EJB initializated (Singleton mode)");
	}
	
	public JavaComponentResponse getOrCreateJavaInstance(String userID, String componentJavaID, String componentJavaName) {
	  JavaComponentsApplication jca = new JavaComponentsApplication(); 

	  JavaComponentResponse o = jca.getComponent("localhost", 4445, "localhost", 8888, userID, "applicationId", 
			  																		componentJavaID, componentJavaName);
	  
	  /*JavaComponentClient jcr = new JavaComponentClient();
	  JavaComponentResponse o = jcr.getOrCreateJavaInstance(userID, componentJavaID, componentJavaName);*/
	  
	  return o;
	}
	
	/*private String makeInstanceId(String userId, String componentId, String componentName){
	String platform = queryComponentPlatform(componentId);
	String instanceId = null;
	try{
		if(platform.equalsIgnoreCase("Web")){
			ManageWookie wookie = new ManageWookie();

			WidgetData widgetData = wookie.getOrCreateWidgetInstance(userId, componentId, componentName);
			
			instanceId = widgetData.getIdentifier();
			
		}else{
			if(platform.equalsIgnoreCase("Java")){
				ManageJava manageJava = new ManageJava();

				JavaComponentResponse r = manageJava.getOrCreateJavaInstance(userId, componentId, componentName);

				instanceId = r.getComponentInstanceName();
			}
			
		}
	} catch (Exception e) {
		LOGGER.error(e);
	}
	return instanceId;
	}*/
}
