package es.ual.acg.cos.java;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ServiceFactory;
import javax.xml.rpc.Service;
import javax.xml.rpc.encoding.XMLType;

import org.jboss.logging.Logger;


public class JavaComponentClient {
	
	private static final Logger LOGGER = Logger.getLogger(JavaComponentClient.class);
	
	public JavaComponentResponse getOrCreateJavaInstance(String userID, String componentJavaID, String componentJavaName){
		JavaComponentResponse jcr = null;
		try{
			URL url = new URL("http://localhost:8080/javacomponentsserver/JavaComponentsWS?wsdl");
			String namespace = "http://ws.cos.acg.ual.es/";
		    String serviceName = "JavaComponentsWSImplService";
LOGGER.info("1111111111111111111");
			QName serviceQN = new QName(namespace, serviceName);
			ServiceFactory serviceFactory = ServiceFactory.newInstance();
	        Service service = serviceFactory.createService(url, serviceQN);
if(service != null)
LOGGER.info("2222222222222222222");
	        QName port = new QName("http://ws.cos.acg.ual.es/", "JavaComponentsWSImplPort");
	        Call call = service.createCall(port);
if(call != null)
LOGGER.info("3333333333333333333");
	        QName operation = new QName("http://ws.cos.acg.ual.es/", "getJavaComponent");
	        call.setOperationName(operation);
if(call != null)
LOGGER.info("4444444444444444444");
	        call.addParameter("userID", XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
	        call.addParameter("applicationId", XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
	        call.addParameter("javaComponentID", XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
	        call.addParameter("javaComponentName", XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);

	        String[] params={userID, "app2", componentJavaID, componentJavaName};
	        jcr = (JavaComponentResponse) call.invoke(params);
if(jcr != null)
LOGGER.info("5555555555555555555");
		}catch (Exception e){
			e.printStackTrace();
		}

		return jcr;
	}
}
