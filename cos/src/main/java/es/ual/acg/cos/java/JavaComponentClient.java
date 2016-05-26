/*
 * JavaComponentClient.java -- ...
 * Copyright (C) 2016  Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * JavaComponentClient.java is part of COScore Community.
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
 *  Authors: Javier Criado Rodríguez   Doctor/Researcher/Software Developer
 *           Jesús Vallecillos Ruíz    Pre-doctoral scholarship holders/Researcher/Software Developer
 *    Group: ACG 		               Applied Computing Group
 * Internet: http://acg.ual.es/        
 *   E-mail: acg.tic211@ual.es        
 *   Adress: Edif. Científico Técnico, CITE-III
 *           Universidad de Almería
 *           Almeria, España
 *           04120
*/
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
