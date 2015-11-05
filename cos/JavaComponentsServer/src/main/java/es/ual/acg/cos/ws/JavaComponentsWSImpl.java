package es.ual.acg.cos.ws;

import java.net.URL;
import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.jboss.logging.Logger;
import es.ual.acg.cos.java.JavaComponentResponse;
import es.ual.acg.cos.java.JavaComponentsRepository;

@WebService(endpointInterface = "es.ual.acg.cos.ws.JavaComponentsWS")
public class JavaComponentsWSImpl implements JavaComponentsWS {
	
	
	private JavaComponentsRepository jcr;

	public JavaComponentResponse getJavaComponent(String userID, String applicationId, String javaComponentID, String javaComponentName) {
		
		jcr = new JavaComponentsRepository(new URL[]{});
		
		return jcr.handleRequests(userID, applicationId, javaComponentID, javaComponentName);
	}
	
	public boolean deleteJavaComponent(String componentId) {
		
		jcr = new JavaComponentsRepository(new URL[]{});
		
		return jcr.deleteComponentJava(componentId);
	}

	public boolean addJavaComponent(String componentId, DataHandler jar) {

		jcr = new JavaComponentsRepository(new URL[]{});
		
		return jcr.addComponentJava(componentId, jar);
	}

}


/*
@WebService(endpointInterface = "es.ual.acg.cos.ws.JavaComponentsWS")
@Path("/")
public class JavaComponentsWSImpl implements JavaComponentsWS {

	private static final Logger LOGGER = Logger.getLogger(JavaComponentsRepository.class);
	
	private JavaComponentsRepository jcr;
	
	@GET	
	@Path("{hola}")
	public String hola(@PathParam("nombre") String nombre) {
LOGGER.info("ENTRAMOSSSS");
		return "HOLA " + nombre + "!!!!";
	}
	
	@POST
	//@Path("{javacomponents}")
	public JavaComponentResponse getJavaComponent(@PathParam("userid") String userID, @PathParam("applicationdd") String applicationId,
			@PathParam("javacomponentid") String javaComponentID,	@PathParam("javacomponentname") String javaComponentName) {

		jcr = new JavaComponentsRepository(new URL[]{});
		
		return jcr.handleRequests(userID, applicationId, javaComponentID, javaComponentName);
	}

	@DELETE	
	//@Path("{javacomponents}")
	public boolean deleteJavaComponent(@PathParam("componentid") String componentId) {

		jcr = new JavaComponentsRepository(new URL[]{});
		
		return jcr.deleteComponentJava(componentId);
	}

	@POST
	//@Path("{javacomponents}")
	public boolean addJavaComponent(@PathParam("javacomponentid") String javaComponentID, @PathParam("jar") DataHandler jar) {

		jcr = new JavaComponentsRepository(new URL[]{});
		
		return jcr.addComponentJava(javaComponentID, jar);
	}
	
}*/

