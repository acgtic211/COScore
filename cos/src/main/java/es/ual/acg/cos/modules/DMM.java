/*
 * DMM.java -- Módulo de Visualización de Componentes.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * DMM.java is part of COScore Community.
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
package es.ual.acg.cos.modules;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import es.ual.acg.cos.controllers.ManageArchitectures;
import es.ual.acg.cos.controllers.ManageComponentSpecifications;
import es.ual.acg.cos.controllers.ManageWookie;
import es.ual.acg.cos.types.ComponentData;
import es.ual.acg.cos.types.InterModulesData;
import es.ual.acg.cos.types.ServicesData;
import es.ual.acg.cos.types.UserInteractionData;
import es.ual.acg.cos.wookie.WidgetData;
import es.ual.acg.cos.ws.types.UpdateArchitectureResult;
import architectural_metamodel.Architectural_metamodelFactory;
import architectural_metamodel.ComponentType;
import architectural_metamodel.ConcreteArchitecturalModel;
import architectural_metamodel.ConcreteComponent;
import architectural_metamodel.InputPort;
import architectural_metamodel.RuntimeProperty;
import architectural_metamodel.impl.RuntimePropertyImpl;
 
@Stateful 
public class DMM {
	
	private ConcreteArchitecturalModel cam;

	private static final Logger LOGGER = Logger.getLogger(DMM.class);

	private String queryComponentPlatform(String componentID) {
		String platform = null;
		try {
			InitialContext initialContext = new InitialContext();
			ManageComponentSpecifications mr = (es.ual.acg.cos.controllers.ManageComponentSpecifications)initialContext.lookup("java:app/cos/ManageRegister");
			platform = mr.queryComponentPlatform(componentID);

		} catch (Exception e) {
			LOGGER.error(e);
		}
		
		return platform;
	}
	
	public InterModulesData getCurrentModelforUser(String userID, String camID) {
		
		List<ComponentData> resultList = new ArrayList<ComponentData>();
		WidgetData widgetData = null;
		InterModulesData resultUIM = new InterModulesData();
		InterModulesData interModulesData = new InterModulesData();
		interModulesData.setValue("");
		interModulesData.setModel(null);
		interModulesData.setMessage("> Component list OK");
		
		try {
			InitialContext initialContext = new InitialContext();
	
			if (camID == null){ //Si no disponemos del camID (llamamos desde un Web Service directamente)pedimos el cam a UIM.
				
				//Obtengo el cam o el error producido
				UIM uim = (es.ual.acg.cos.modules.UIM)initialContext.lookup("java:app/cos/UIM");
				resultUIM = uim.queryCamUser(userID);
				if (!resultUIM.getValue().equalsIgnoreCase("-1")){ //Sino hay error en cam
					camID = resultUIM.getValue();
				}else{ //Errores producidos en UIM y en ManagerUSer
					interModulesData.setValue("-1");
					interModulesData.setMessage(resultUIM.getMessage());
				}
			}

			if (interModulesData.getValue().compareTo("-1") != 0){
			    ManageArchitectures manageArchitectures = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
	
			    cam = manageArchitectures.readModel(camID);
			    if(cam != null){ //Si el cam existe
		
				for(int i = 0; i < cam.getConcreteComponent().size(); i++) {
					String platform = queryComponentPlatform(cam.getConcreteComponent().get(i).getComponentName());
					
					if(platform.equalsIgnoreCase("Web")) {
						String componentName = cam.getConcreteComponent().get(i).getComponentName();
						String componentAlias = cam.getConcreteComponent().get(i).getComponentAlias();
						String componentInstance = cam.getConcreteComponent().get(i).getComponentInstance();

						ConcreteComponent cc = cam.getConcreteComponent().get(i);
						
						try {
						
							if(componentInstance == null ||	componentInstance.equalsIgnoreCase("")){

								ManageWookie wookie = (es.ual.acg.cos.controllers.ManageWookie)initialContext.lookup("java:app/cos/ManageWookie");
								

								widgetData = wookie.getOrCreateWidgetInstance(userID, componentName, componentAlias);
			
								String widgetTitle = widgetData.getTitle();
								LOGGER.info("[DMM - initGUI] title: " + widgetTitle);
								
								String instanceID = widgetData.getIdentifier();
								LOGGER.info("[DMM - initGUI] instance ID: " + instanceID);

								
								//Update the information of this component
								cam.getConcreteComponent().get(i).setComponentInstance(instanceID);
								componentInstance = cam.getConcreteComponent().get(i).getComponentInstance();

								try {
									//Store the instance identifier into the table		
									manageArchitectures.saveComponentInstance(componentName,componentAlias,componentInstance);
						
								} catch (Exception e) {
									LOGGER.error(e);
									interModulesData.setMessage("> Error in Architectural Models DB " + e);
									interModulesData.setValue("-1");
								} 
							}

							String codeHTML = composeDiv(componentName, componentInstance, userID);
	
							String maximizableService = readRuntimeProperty(cc.getRuntimeProperty(), "servicio_maximizable");
							String groupableService = readRuntimeProperty(cc.getRuntimeProperty(), "servicio_agrupable");
							
							int nServices = Integer.parseInt(readRuntimeProperty(cc.getRuntimeProperty(), "numero_servicios"));

							resultList.add(new ComponentData(platform, componentName, componentAlias, componentInstance, 
									codeHTML, "", "", readRuntimeProperty(cc.getRuntimeProperty(), "id_html"),
									Integer.parseInt(readRuntimeProperty(cc.getRuntimeProperty(), "posx")),
									Integer.parseInt(readRuntimeProperty(cc.getRuntimeProperty(), "posy")), 
									Integer.parseInt(readRuntimeProperty(cc.getRuntimeProperty(), "tamanox")), 
									Integer.parseInt(readRuntimeProperty(cc.getRuntimeProperty(), "tamanoy")),
									maximizableService, groupableService, nServices,
									listServicesData(nServices, cc.getRuntimeProperty())));
							
							RuntimeProperty runtimePropertyCodeHTML = new RuntimePropertyImpl();
							runtimePropertyCodeHTML.setPropertyID("codeHTML");
							runtimePropertyCodeHTML.setPropertyValue(codeHTML);
							manageArchitectures.changeComponentRuntimeProperties(componentInstance, runtimePropertyCodeHTML);
							
							RuntimeProperty runtimePropertyPlatform = new RuntimePropertyImpl();
							runtimePropertyPlatform.setPropertyID("platform");
							runtimePropertyPlatform.setPropertyValue("web");
							manageArchitectures.changeComponentRuntimeProperties(componentInstance, runtimePropertyPlatform);

						} catch (Exception e) {
							LOGGER.error(e);
							interModulesData.setMessage("> Error in Wookie " + e);
							interModulesData.setValue("-1");
						}
					}//fin caso Web
				}//fin for
			}//fin if
			else{
				LOGGER.error("> Error in Architectural Models BD2");
				interModulesData.setMessage("> Error in Architectural Models BD2");
				interModulesData.setValue("-1");	
			}
			}//fin if de cam correcto
		} catch (Exception e) {
			LOGGER.error(e);
			interModulesData.setMessage("> Error in Architectural Models BD " + e);
			interModulesData.setValue("-1");
		} 
		interModulesData.setModel(resultList);

		//Return the code of the components that must be inserted
		return interModulesData;
	}
	
	private String readRuntimeProperty(List<RuntimeProperty> propertyList, String property){
		String value = "";
		boolean end = false;
		for(int i = 0; i < propertyList.size() && end == false; i++){
			if(propertyList.get(i).getPropertyID().equalsIgnoreCase(property)){
				value = propertyList.get(i).getPropertyValue();
				end = true;
			}
		}
		
		return value;
	}

	private List<ServicesData> listServicesData(int nServices, List<RuntimeProperty> propertyList) {
		List<ServicesData> listServicesData = new ArrayList<ServicesData>();

		int nServic = 0;
		boolean findService = false;
		
		for(int i = 0; i < propertyList.size() && nServic < nServices; i++) {

			//Localizar servicioN
			String property = propertyList.get(i).getPropertyID();
			String service = "";
			String componentname = null;
			String componentalias = null;
			String instanceId = null;
			String mapaKML = null;
			String capa = null;
			
			if(property.length() > 9)
				service = property.substring(0, 9);
			
			if(service.equalsIgnoreCase("servicio"+nServic))
				for(int j = 0; j < propertyList.size() || (componentname == null &&
														  componentalias == null &&
														  instanceId == null &&
														  mapaKML == null &&
														  capa == null); j++) {		
					findService = true;
					property = propertyList.get(j).getPropertyID();
					if(property.equalsIgnoreCase("servicio"+nServic+".name_componente")){
						componentname = propertyList.get(j).getPropertyValue();
					}
					if(property.equalsIgnoreCase("servicio"+nServic+".alias_componente")){
						componentalias = propertyList.get(j).getPropertyValue();
					}
					if(property.equalsIgnoreCase("servicio"+nServic+".instancia")){
						instanceId = propertyList.get(j).getPropertyValue();
					}
					if(property.equalsIgnoreCase("servicio"+nServic+".mapa_KML")){
						mapaKML = propertyList.get(j).getPropertyValue();
					}
					if(property.equalsIgnoreCase("servicio"+nServic+".capa")){
						capa = propertyList.get(j).getPropertyValue();
					}
				}
			
			if(findService == true){
				listServicesData.add(new ServicesData(componentname, componentalias, instanceId, mapaKML, capa));
				findService = false;
				nServic ++;
			}else
				findService = false;
		}

		return listServicesData;
	}
	
	public UpdateArchitectureResult updateArchitectureforUser(String userID, String componentInstance, String actionDone,
															  List<ComponentData> newComponentData, UserInteractionData interaction) {

		WidgetData widgetData = null;
		UpdateArchitectureResult result = new UpdateArchitectureResult();
		
		InterModulesData resultUIM = new InterModulesData();
		
		//Fecha y hora
		Calendar calendar = Calendar.getInstance();
		 
		String dateTime = calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.MONTH)
				   				  +"/"+calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.HOUR)
				   				  +":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND)
				   				  +":"+calendar.get(Calendar.MILLISECOND);
		
		ArrayList<String> groupComponent = new ArrayList<String>();
		ArrayList<String> ungroupComponent  = new ArrayList<String>();
		
		Context initialContext;
		try	{
			initialContext = new InitialContext();
			COSSessionMM cossmng = (COSSessionMM)initialContext.lookup("java:app/cos/COSSessionMM");

			if(cossmng.isUserOnUserEJBMaps(userID) == true){
			
				// Update the used time last
				cossmng.setTime(userID);
				
				//Antes de realizar cualquier cambio obtenemos copia del modelo correcto por si hay algun fallo
				result.setOldComponentData(this.getCurrentModelforUser(userID,null).getModel());
				
				//Compruebo action Done
				switch (actionDone){
					case "add": 	//Accion añadir
									//Compruebo que en los parametros haya un nuevo componente
									result.setAllowed(false);
									result.setMessage("> Not found Component");
									
									for(int i = 0; i < newComponentData.size(); i++) {
										String newComponentInstance = newComponentData.get(i).getInstanceId();
										if (newComponentInstance == null || newComponentInstance.equalsIgnoreCase("")) {
											result.setAllowed(true);
											result.setMessage("> Successfully Added");
										}
									}
									if(result.isAllowed()==true){ //Si existe un nuevo componente en los parametros de entrada
										
					            		//1.-Identifico en la lista de componentes el nuevo componente (No ComponentInstace )
										for(int i = 0; i < newComponentData.size(); i++) {
											String newComponentInstance = newComponentData.get(i).getInstanceId();
											
											//Si es el nuevo componente
											if (newComponentInstance == null || newComponentInstance.equalsIgnoreCase("")) {
	
												String newcomponentName = newComponentData.get(i).getComponentname();
												String newcomponentAlias = newComponentData.get(i).getComponentAlias();
												
												/*******************************************************
												 * //2.- Comprueba que la accion sea posible
												 * //	 Sino devuelvo lista de componentes de la base de datos (getCurrentModel...) 
												 * 
												 */
												
												try {
													//3.- Consigo una instancia a partir de su nombre alias y usuario
													ManageWookie wookie = (es.ual.acg.cos.controllers.ManageWookie)initialContext.lookup("java:app/cos/ManageWookie");
													widgetData = wookie.getOrCreateWidgetInstance(userID, newcomponentName, newcomponentAlias);
													newComponentInstance = widgetData.getIdentifier();
												} catch (Exception e) {
													LOGGER.error(e);
													result.setAllowed(false);
													result.setMessage("> Error in Wookie " + e);
													//result.setOldComponentData(this.getCurrentModelforUser(userID,null).getModel());
												}
												try {
													//4.- Completo el resto de campos que no me viene por parametros del nuevo componente
													ManageComponentSpecifications manageRegister = (es.ual.acg.cos.controllers.ManageComponentSpecifications)initialContext.lookup("java:app/cos/ManageRegister");
													List<RuntimeProperty> runtimePropertyList = manageRegister.readComponentProperty(newcomponentName);
														
													//codeHTML
													RuntimeProperty runtimePropertyCodeHTML =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
													runtimePropertyCodeHTML.setPropertyID("codeHTML");
													runtimePropertyCodeHTML.setPropertyValue(composeDiv(newcomponentName, newComponentInstance, userID));
													runtimePropertyList.add(runtimePropertyCodeHTML);
													
													//objectJava
													RuntimeProperty runtimePropertyObjectJava =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
													runtimePropertyObjectJava.setPropertyID("objectJava");
													runtimePropertyObjectJava.setPropertyValue("");
													runtimePropertyList.add(runtimePropertyObjectJava);
												
													//jarJava
													RuntimeProperty runtimePropertyJarJava =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
													runtimePropertyJarJava.setPropertyID("jarJava");
													runtimePropertyJarJava.setPropertyValue("");
													runtimePropertyList.add(runtimePropertyJarJava);
													
													//id_html
													RuntimeProperty runtimePropertyIdHTML =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
													runtimePropertyIdHTML.setPropertyID("id_html");
													runtimePropertyIdHTML.setPropertyValue(newComponentData.get(i).getIdHtml());
													runtimePropertyList.add(runtimePropertyIdHTML);
										
													//PosX
													RuntimeProperty runtimePropertyPosX =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
													runtimePropertyPosX.setPropertyID("posX");
													runtimePropertyPosX.setPropertyValue(newComponentData.get(i).getPosx()+"");
													runtimePropertyList.add(runtimePropertyPosX);
								
													//PosY
													RuntimeProperty runtimePropertyPosY =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
													runtimePropertyPosY.setPropertyID("posY");
													runtimePropertyPosY.setPropertyValue(newComponentData.get(i).getPosy()+"");
													runtimePropertyList.add(runtimePropertyPosY);
										
													//Num_servicios
													RuntimeProperty runtimePropertyNumSer =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
													runtimePropertyNumSer.setPropertyID("numero_servicios");
													runtimePropertyNumSer.setPropertyValue("1");
													runtimePropertyList.add(runtimePropertyNumSer);
						
																	
													// Instancia
													RuntimeProperty runtimePropertyInst =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
													runtimePropertyInst.setPropertyID("servicio0.instancia");
													runtimePropertyInst.setPropertyValue(newComponentInstance);
													runtimePropertyList.add(runtimePropertyInst);
					
													// name_componente
													RuntimeProperty runtimePropertyIdCom =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
													runtimePropertyIdCom.setPropertyID("servicio0.name_componente");
													runtimePropertyIdCom.setPropertyValue(newcomponentName);
													runtimePropertyList.add(runtimePropertyIdCom);
						
													// alias_componente
													RuntimeProperty runtimePropertyAlias =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
													runtimePropertyAlias.setPropertyID("servicio0.alias_componente");
													runtimePropertyAlias.setPropertyValue(newcomponentAlias);
													runtimePropertyList.add(runtimePropertyAlias);
											
													// mapa_KML y capa se deben leer de la especificación del componente
													
													//4.1 Creo el nuevo componente concreto						
													ConcreteComponent concreteComponent = Architectural_metamodelFactory.eINSTANCE.createConcreteComponent();
													
													concreteComponent.setComponentName(newcomponentName);
													concreteComponent.setComponentAlias(newcomponentAlias);
													concreteComponent.setComponentInstance(newComponentInstance);
													concreteComponent.setComponentType(ComponentType.USER_INTERACTION);
	
													//Initialize the port
													InputPort inputPort = Architectural_metamodelFactory.eINSTANCE.createInputPort();
													inputPort.setPortID("inputport");
													inputPort.setCc(concreteComponent);
	
													//4.2 Añadir el nuevo componente al modelo en la base de datos
													UIM uim = (es.ual.acg.cos.modules.UIM)initialContext.lookup("java:app/cos/UIM"); //Este proceso no es necesario ya se tiene el cam, lo dejamos por si acaso
													resultUIM = uim.queryCamUser(userID);
													if (!resultUIM.getValue().equalsIgnoreCase("-1")){ //Sino hay error en cam
														String camID = resultUIM.getValue();
														try{
															ManageArchitectures manageArchitectures = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
															manageArchitectures.addComponent(camID, concreteComponent, inputPort, runtimePropertyList);
															
															/*******************************************************
															 * //	Registrar interaccion
															 /*******************************************************/
															try{
																IMM imm = (es.ual.acg.cos.modules.IMM)initialContext.lookup("java:app/cos/IMM"); //Este proceso no es necesario ya se tiene el cam, lo dejamos por si acaso
																imm.registerinteraction("0", interaction.getDeviceType(), interaction.getInteractionType(), dateTime, userID,
																		interaction.getLatitude(), interaction.getLongitude(), actionDone, componentInstance, null, null, 
																		this.getCurrentModelforUser(userID,null).getModel());
																result.setOldComponentData(null);

															} catch (Exception e) {
																LOGGER.error(e);
																result.setAllowed(false);
															result.setMessage("> Error in Register Interaction " + e);

															}
															 
														} catch (Exception e) {
															LOGGER.error(e);
															result.setAllowed(false);
															result.setMessage("> Error in Architectural Models DB " + e);
												
														}
													}else{ //Errores producidos en UIM y en ManagerUSer
														result.setAllowed(false);
														result.setMessage(resultUIM.getMessage());
												
													}
												} catch (Exception e) {
													LOGGER.error(e);
													result.setAllowed(false);
													result.setMessage("> Error in Component specification BD: " + e);

												}
											}else{//Si no es el nuevo componente 

												//5.- Actualizo el cam y a la base de datos con la lista de componentes recibida
					            				//    (La info que cambia es posx,posY)
												try {
													ManageArchitectures manageArchitectures = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
													RuntimeProperty runtimePropertyX = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
													runtimePropertyX.setPropertyID("posX");
													runtimePropertyX.setPropertyValue(newComponentData.get(i).getPosx()+"");
													manageArchitectures.changeComponentRuntimeProperties(newComponentData.get(i).getInstanceId(), runtimePropertyX);												
													
													RuntimeProperty runtimePropertyY = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
													runtimePropertyY.setPropertyID("posY");
													runtimePropertyY.setPropertyValue(newComponentData.get(i).getPosy()+"");
													manageArchitectures.changeComponentRuntimeProperties(newComponentData.get(i).getInstanceId(), runtimePropertyY);

													
												} catch (Exception e) {
													LOGGER.error(e);
													result.setAllowed(false);
													result.setMessage("> Error in Architectural Models DB " + e);
													//result.setOldComponentData(this.getCurrentModelforUser(userID,null).getModel());
												}
											}														
										}//fin for
									}else{
										result.setAllowed(false);
										result.setMessage("> Not found Component");

									}			
			                		break;
			                		
		            case "delete": 		//Accion borrar
										/*******************************************************
										 * //2.- Comprueba que la accion sea posible
										 * //	 Sino devuelvo lista de componentes de la base de datos (getCurrentModel...) 
										 * 
										 */
										result.setAllowed(false);
										result.setMessage("> Not found Component");
										
										UIM uim = (es.ual.acg.cos.modules.UIM)initialContext.lookup("java:app/cos/UIM"); //Este proceso no es necesario ya se tiene el cam, lo dejamos por si acaso
										resultUIM = uim.queryCamUser(userID);
										if (!resultUIM.getValue().equalsIgnoreCase("-1")){ //Sino hay error en cam
											String camID = resultUIM.getValue();
											try{
												ManageArchitectures manageArchitectures = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
												manageArchitectures.deleteComponent(camID, componentInstance);
												result.setAllowed(true);
												result.setMessage("> Successfully Deleted");
												
												/*******************************************************
												 * //	Registrar interaccion
												 /*******************************************************/
												try{
													IMM imm = (es.ual.acg.cos.modules.IMM)initialContext.lookup("java:app/cos/IMM"); //Este proceso no es necesario ya se tiene el cam, lo dejamos por si acaso
													imm.registerinteraction("0", interaction.getDeviceType(), interaction.getInteractionType(), dateTime, userID,
															interaction.getLatitude(), interaction.getLongitude(), actionDone, componentInstance, null, null, 
															this.getCurrentModelforUser(userID,null).getModel());
													result.setOldComponentData(null);
												} catch (Exception e) {
													LOGGER.error(e);
													result.setAllowed(false);
													result.setMessage("> Error in Register Interaction " + e);
											
												}
												

											} catch (Exception e) {
												LOGGER.error(e);
												result.setAllowed(false);
												result.setMessage("> Error in Architectural Models DB " + e);
									
											}
										}else{ //Errores producidos en UIM y en ManagerUSer
											result.setAllowed(false);
											result.setMessage(resultUIM.getMessage());
										
										}
										//cambiamos las propidades
										for(int i = 0; i < newComponentData.size(); i++) {
											try {
												ManageArchitectures manageArchitectures = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
												RuntimeProperty runtimePropertyX = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
												runtimePropertyX.setPropertyID("posX");
												runtimePropertyX.setPropertyValue(newComponentData.get(i).getPosx()+"");
												manageArchitectures.changeComponentRuntimeProperties(newComponentData.get(i).getInstanceId(), runtimePropertyX);												
												
												RuntimeProperty runtimePropertyY = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
												runtimePropertyY.setPropertyID("posY");
												runtimePropertyY.setPropertyValue(newComponentData.get(i).getPosy()+"");
												manageArchitectures.changeComponentRuntimeProperties(newComponentData.get(i).getInstanceId(), runtimePropertyY);
											} catch (Exception e) {
												LOGGER.error(e);
												result.setAllowed(false);
												result.setMessage("> Error in Architectural Models DB " + e);
										
											}	
										}//fin for
	                					break;
	                						
			        case "changeproperty": 	//Otras acciones (mover,redimensionar, Agrupar desde Panel,Desagrupar-Borrar,Desagrupar-Agrupar)
					        				
			        						//1.-Identifico el tipo de subaccion
											result.setAllowed(false);
											result.setMessage("> Not found Component");
											String subaccion="";
											String option="";
											boolean group = false;
											boolean resize = false;
											int componenteagrupado=0;
											int servicionuevoagrupado=0;
											for(int i = 0; i < newComponentData.size(); i++) {
												if (newComponentData.get(i).getPosx() == 0 || newComponentData.get(i).getPosy() == 0 || newComponentData.get(i).getPosx() == -1 || newComponentData.get(i).getPosy() == -1) {
													for(int j = 0; j < newComponentData.get(i).getServicios().size(); j++) {
														if (newComponentData.get(i).getServicios().get(j).getInstanceId() == null || newComponentData.get(i).getServicios().get(j).getInstanceId().equalsIgnoreCase("")) {
															group = true;
															componenteagrupado=i;
															servicionuevoagrupado=j;
														}else{
															subaccion="ungroup";
															if (componentInstance.equalsIgnoreCase("")){
																//Desagrupar-Borrar
																option = "ungroupdelete"; 
															}else{
																//Desagrupar-Agrupar
																option = "ungroupgroup"; 
															}
														}
													}
												}else{
													if (newComponentData.get(i).getTamanox() == 0 || newComponentData.get(i).getTamanoy() == 0 || newComponentData.get(i).getTamanox() == -1 || newComponentData.get(i).getTamanoy() == -1) {
														subaccion="move";//Mover
													}else{
														resize = true;
													}
												}
											}
											if(group){
												subaccion="group";//Agrupar desde Panel,
											}
											if(resize){
												subaccion="resize";//Redimensionar
											}
											/*******************************************************
											 * //2.- Comprueba que la accion sea posible
											 * //	 Sino devuelvo lista de componentes de la base de datos (getCurrentModel...) 
											 * 
											 */
											switch (subaccion){
												case "group": 
													result.setAllowed(true);
													result.setMessage("> Successfully Grouped");
													int ultimaposicion=0;
													int indice=0;
													String newcomponentServinstance = "";
													try {
														List<RuntimeProperty> runtimePropertyList = new ArrayList<RuntimeProperty>();
														ManageArchitectures manageArchitectures = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
														for(int j = 0; j < newComponentData.get(componenteagrupado).getServicios().size(); j++) {
															if (j==servicionuevoagrupado) { //El servicio con la instancia vacia
																// Calculamos la nueva instancia de ese servicio para despues añadirla junto al servicio al final de la lista
																String newcomponentServName = newComponentData.get(componenteagrupado).getServicios().get(servicionuevoagrupado).getComponentname();
																String newcomponentServAlias = newComponentData.get(componenteagrupado).getServicios().get(servicionuevoagrupado).getComponentalias();
																try {
																	ManageWookie wookie = (es.ual.acg.cos.controllers.ManageWookie)initialContext.lookup("java:app/cos/ManageWookie");
																	widgetData = wookie.getOrCreateWidgetInstance(userID, newcomponentServName, newcomponentServAlias);
																	newcomponentServinstance = widgetData.getIdentifier();
																} catch (Exception e) {
																	LOGGER.error(e);
																	result.setAllowed(false);
																	result.setMessage("> Error in Wookie " + e);
																	
																}
															}else{ //resto de servicios de ese componente que no es el nuevo
																RuntimeProperty runtimePropertyServInsta =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();																		
																runtimePropertyServInsta.setPropertyID("servicio"+indice+".instancia");
																runtimePropertyServInsta.setPropertyValue(newComponentData.get(componenteagrupado).getServicios().get(j).getInstanceId());
																runtimePropertyList.add(runtimePropertyServInsta);
																
																RuntimeProperty runtimePropertyServName =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
																runtimePropertyServName.setPropertyID("servicio"+indice+".name_componente");
																runtimePropertyServName.setPropertyValue(newComponentData.get(componenteagrupado).getServicios().get(j).getComponentname());
																runtimePropertyList.add(runtimePropertyServName);								
																
																RuntimeProperty runtimePropertyServAlias =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
																runtimePropertyServAlias.setPropertyID("servicio"+indice+".alias_componente");
																runtimePropertyServAlias.setPropertyValue(newComponentData.get(componenteagrupado).getServicios().get(j).getComponentalias());
																runtimePropertyList.add(runtimePropertyServAlias);
																
																RuntimeProperty runtimePropertyServMapaKML =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
																runtimePropertyServMapaKML.setPropertyID("servicio"+indice+".mapa_KML");
																runtimePropertyServMapaKML.setPropertyValue(newComponentData.get(componenteagrupado).getServicios().get(j).getMapaKML());
																runtimePropertyList.add(runtimePropertyServMapaKML);
																
																RuntimeProperty runtimePropertyServCapa =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
																runtimePropertyServCapa.setPropertyID("servicio"+indice+".capa");
																runtimePropertyServCapa.setPropertyValue(newComponentData.get(componenteagrupado).getServicios().get(j).getCapa());
																runtimePropertyList.add(runtimePropertyServCapa);
																
																indice++;
															}
															ultimaposicion=j;
														}
														//Metemos al final el nuevo servicio
														RuntimeProperty runtimePropertyServInsta =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();																		
														runtimePropertyServInsta.setPropertyID("servicio"+ultimaposicion+".instancia");
														runtimePropertyServInsta.setPropertyValue(newcomponentServinstance);
														runtimePropertyList.add(runtimePropertyServInsta);
														
														RuntimeProperty runtimePropertyServName =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
														runtimePropertyServName.setPropertyID("servicio"+ultimaposicion+".name_componente");
														runtimePropertyServName.setPropertyValue(newComponentData.get(componenteagrupado).getServicios().get(servicionuevoagrupado).getComponentname());
														runtimePropertyList.add(runtimePropertyServName);								
														
														RuntimeProperty runtimePropertyServAlias =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
														runtimePropertyServAlias.setPropertyID("servicio"+ultimaposicion+".alias_componente");
														runtimePropertyServAlias.setPropertyValue(newComponentData.get(componenteagrupado).getServicios().get(servicionuevoagrupado).getComponentalias());
														runtimePropertyList.add(runtimePropertyServAlias);
														
														RuntimeProperty runtimePropertyServMapaKML =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
														runtimePropertyServMapaKML.setPropertyID("servicio"+ultimaposicion+".mapa_KML");
														runtimePropertyServMapaKML.setPropertyValue(newComponentData.get(componenteagrupado).getServicios().get(servicionuevoagrupado).getMapaKML());
														runtimePropertyList.add(runtimePropertyServMapaKML);
														
														RuntimeProperty runtimePropertyServCapa =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
														runtimePropertyServCapa.setPropertyID("servicio"+ultimaposicion+".capa");
														runtimePropertyServCapa.setPropertyValue(newComponentData.get(componenteagrupado).getServicios().get(servicionuevoagrupado).getCapa());
														runtimePropertyList.add(runtimePropertyServCapa);
														
														//Cambiamos la lista de servicios
														manageArchitectures.changeComponentService(newComponentData.get(componenteagrupado).getInstanceId(), newComponentData.get(componenteagrupado).getNumero_servicios(), runtimePropertyList);
														
														/*******************************************************
														 * //	Registrar interaccion
														 /*******************************************************/
														try{
															List<ComponentData> nuevomodelo = this.getCurrentModelforUser(userID,null).getModel();
															//Rescatamos la lista de servicios del componente que ha recibido el nuevo servicio
															for(int i = 0; i < nuevomodelo.size(); i++) {
																if(nuevomodelo.get(i).getInstanceId().equalsIgnoreCase(componentInstance)){
																	for(int j = 0; j < nuevomodelo.get(i).getServicios().size(); j++) {
																		groupComponent.add(nuevomodelo.get(i).getServicios().get(j).getInstanceId());
																	}
																}
															}
															IMM imm = (es.ual.acg.cos.modules.IMM)initialContext.lookup("java:app/cos/IMM"); //Este proceso no es necesario ya se tiene el cam, lo dejamos por si acaso
															imm.registerinteraction("0", interaction.getDeviceType(), interaction.getInteractionType(), dateTime, userID,
																	interaction.getLatitude(), interaction.getLongitude(), "addgroup", componentInstance, groupComponent, null, 
																	this.getCurrentModelforUser(userID,null).getModel());
															result.setOldComponentData(null);
														} catch (Exception e) {
															LOGGER.error(e);
															result.setAllowed(false);
														result.setMessage("> Error in Register Interaction " + e);
														
														}
													} catch (Exception e) {
														LOGGER.error(e);
														result.setAllowed(false);
														result.setMessage("> Error in Architectural Models DB " + e);
														
														
									            	}
																break;
												case "ungroup": 
																result.setAllowed(true);
																result.setMessage("> Successfully Ungrouped");

																try {

																	
																	ManageArchitectures manageArchitectures = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
																	for(int i = 0; i < newComponentData.size(); i++) {
																		List<RuntimeProperty> runtimePropertyList = new ArrayList<RuntimeProperty>();
																		//Cogemos la nueva lista de servicios la lista de Servicios
																		for(int j = 0; j < newComponentData.get(i).getServicios().size(); j++) {
																			
																			RuntimeProperty runtimePropertyServInsta =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();																		
																			runtimePropertyServInsta.setPropertyID("servicio"+j+".instancia");
																			runtimePropertyServInsta.setPropertyValue(newComponentData.get(i).getServicios().get(j).getInstanceId());
																			runtimePropertyList.add(runtimePropertyServInsta);
																			
																			RuntimeProperty runtimePropertyServName =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
																			runtimePropertyServName.setPropertyID("servicio"+j+".name_componente");
																			runtimePropertyServName.setPropertyValue(newComponentData.get(i).getServicios().get(j).getComponentname());
																			runtimePropertyList.add(runtimePropertyServName);								
																			
																			RuntimeProperty runtimePropertyServAlias =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
																			runtimePropertyServAlias.setPropertyID("servicio"+j+".alias_componente");
																			runtimePropertyServAlias.setPropertyValue(newComponentData.get(i).getServicios().get(j).getComponentalias());
																			runtimePropertyList.add(runtimePropertyServAlias);
																			
																			RuntimeProperty runtimePropertyServMapaKML =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
																			runtimePropertyServMapaKML.setPropertyID("servicio"+j+".mapa_KML");
																			runtimePropertyServMapaKML.setPropertyValue(newComponentData.get(i).getServicios().get(j).getMapaKML());
																			runtimePropertyList.add(runtimePropertyServMapaKML);
																			
																			RuntimeProperty runtimePropertyServCapa =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
																			runtimePropertyServCapa.setPropertyID("servicio"+j+".capa");
																			runtimePropertyServCapa.setPropertyValue(newComponentData.get(i).getServicios().get(j).getCapa());
																			runtimePropertyList.add(runtimePropertyServCapa);
																		}
																		//Cambiamos la lista de servicios
																		manageArchitectures.changeComponentService(newComponentData.get(i).getInstanceId(), newComponentData.get(i).getNumero_servicios(), runtimePropertyList);
																	}
																	for(int i = 0; i < newComponentData.size(); i++) { //Si el servicio base es distinto que los datos del componente lo cambiamos
																			if(!newComponentData.get(i).getServicios().get(0).getInstanceId().equals(newComponentData.get(i).getInstanceId())){
																				manageArchitectures.changeComponentProperties(newComponentData.get(i).getInstanceId(),
																													newComponentData.get(i).getServicios().get(0).getInstanceId(),
																													newComponentData.get(i).getServicios().get(0).getComponentname(),
																													newComponentData.get(i).getServicios().get(0).getComponentalias());
																			}
																	}
																	
																	/*******************************************************
																	 * //	Registrar interaccion
																	 /*******************************************************/
																	try{
																		String cotgsgetIdfromUngroup="";
																		List<ComponentData> nuevomodelo = this.getCurrentModelforUser(userID,null).getModel();
																		//Buscamos el Cotsget del cual se ha quitado un servicio
																		for(int i = 0; i < nuevomodelo.size(); i++) {
																			for(int j = 0; j < result.getOldComponentData().size(); j++) {
																				if(nuevomodelo.get(i).getInstanceId().equalsIgnoreCase(result.getOldComponentData().get(j).getInstanceId())){
																					if(nuevomodelo.get(i).getNumero_servicios() < result.getOldComponentData().get(j).getNumero_servicios()){
																						cotgsgetIdfromUngroup = nuevomodelo.get(i).getInstanceId();
																					}
																				}	
																			}
																		}
																		//Rescatamos la lista de servicios actual del Cotsget al que se le ha quitado el servicio
																		for(int i = 0; i < nuevomodelo.size(); i++) {
																			if(nuevomodelo.get(i).getInstanceId().equalsIgnoreCase(cotgsgetIdfromUngroup)){
																				for(int j = 0; j < nuevomodelo.get(i).getServicios().size(); j++) {
																					ungroupComponent.add(nuevomodelo.get(i).getServicios().get(j).getInstanceId());
																				}
																			}
																		}
																		//Rescatamos la lista de servicios del componente que ha recibido el servicio de la accion desagrupar-agrupar
																		if (option.equalsIgnoreCase("ungroupgroup")){
																			for(int i = 0; i < nuevomodelo.size(); i++) {
																				if(nuevomodelo.get(i).getInstanceId().equalsIgnoreCase(componentInstance)){
																					for(int j = 0; j < nuevomodelo.get(i).getServicios().size(); j++) {
																						groupComponent.add(nuevomodelo.get(i).getServicios().get(j).getInstanceId());
																					}
																				}
																			}
																			
																		}
																																				
																		IMM imm = (es.ual.acg.cos.modules.IMM)initialContext.lookup("java:app/cos/IMM"); //Este proceso no es necesario ya se tiene el cam, lo dejamos por si acaso
																		imm.registerinteraction("0", interaction.getDeviceType(), interaction.getInteractionType(), dateTime, userID,
																				interaction.getLatitude(), interaction.getLongitude(), option, componentInstance, groupComponent, ungroupComponent, 
																				this.getCurrentModelforUser(userID,null).getModel());
																		result.setOldComponentData(null);
																	} catch (Exception e) {
																		LOGGER.error(e);
																		result.setAllowed(false);
																		result.setMessage("> Error in Register Interaction " + e);
																	}
																} catch (Exception e) {
																	LOGGER.error(e);
																	result.setAllowed(false);
																	result.setMessage("> Error in Architectural Models DB " + e);
													
																	
												            	}

																break;
												case "move": 	
																result.setAllowed(true);
																result.setMessage("> Successfully Moved");
																try {
																	for(int i = 0; i < newComponentData.size(); i++) {
																		ManageArchitectures manageArchitectures = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
																		RuntimeProperty runtimePropertyX = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
																		runtimePropertyX.setPropertyID("posX");
																		runtimePropertyX.setPropertyValue(newComponentData.get(i).getPosx()+"");
																		manageArchitectures.changeComponentRuntimeProperties(newComponentData.get(i).getInstanceId(), runtimePropertyX);												
																		
																		RuntimeProperty runtimePropertyY = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
																		runtimePropertyY.setPropertyID("posY");
																		runtimePropertyY.setPropertyValue(newComponentData.get(i).getPosy()+"");
																		manageArchitectures.changeComponentRuntimeProperties(newComponentData.get(i).getInstanceId(), runtimePropertyY);
																	}
																	
																	/*******************************************************
																	 * //	Registrar interaccion
																	 /*******************************************************/
																	try{
																		IMM imm = (es.ual.acg.cos.modules.IMM)initialContext.lookup("java:app/cos/IMM"); //Este proceso no es necesario ya se tiene el cam, lo dejamos por si acaso
																		imm.registerinteraction("0", interaction.getDeviceType(), interaction.getInteractionType(), dateTime, userID,
																				interaction.getLatitude(), interaction.getLongitude(), "move", componentInstance, null, null, 
																				this.getCurrentModelforUser(userID,null).getModel());
																		result.setOldComponentData(null);
																	} catch (Exception e) {
																		LOGGER.error(e);
																		result.setAllowed(false);
																	result.setMessage("> Error in Register Interaction " + e);
																
																	}
																} catch (Exception e) {
																	LOGGER.error(e);
																	result.setAllowed(false);
																	result.setMessage("> Error in Architectural Models DB " + e);
											
																	
												            	}

																break;
												case "resize": 							
																result.setAllowed(true);
																result.setMessage("> Successfully Resized");
																try {
																	int newArea =0;
																	int oldArea=0;
																	String optionres = "resizeshape"; 
																	
																	for(int i = 0; i < newComponentData.size(); i++) {
														        	
																		ManageArchitectures manageArchitectures = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
																		RuntimeProperty runtimePropertyX = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
																		runtimePropertyX.setPropertyID("posX");
																		runtimePropertyX.setPropertyValue(newComponentData.get(i).getPosx()+"");
																		manageArchitectures.changeComponentRuntimeProperties(newComponentData.get(i).getInstanceId(), runtimePropertyX);												
																		
																		RuntimeProperty runtimePropertyY = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
																		runtimePropertyY.setPropertyID("posY");
																		runtimePropertyY.setPropertyValue(newComponentData.get(i).getPosy()+"");
																		manageArchitectures.changeComponentRuntimeProperties(newComponentData.get(i).getInstanceId(), runtimePropertyY);
														        		if (newComponentData.get(i).getInstanceId().equals(componentInstance)){
																			RuntimeProperty runtimePropertyTamanoX = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
																			runtimePropertyTamanoX.setPropertyID("tamanoX");
																			runtimePropertyTamanoX.setPropertyValue(newComponentData.get(i).getTamanox()+"");
																			manageArchitectures.changeComponentRuntimeProperties(newComponentData.get(i).getInstanceId(), runtimePropertyTamanoX);												
																			
																			RuntimeProperty runtimePropertyTamanoY = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
																			runtimePropertyTamanoY.setPropertyID("tamanoY");
																			runtimePropertyTamanoY.setPropertyValue(newComponentData.get(i).getTamanoy()+"");
																			manageArchitectures.changeComponentRuntimeProperties(newComponentData.get(i).getInstanceId(), runtimePropertyTamanoY);
														        		}
																	}
																	
																	/*******************************************************
																	 * //	Registrar interaccion
																	 /*******************************************************/
																	for(int i = 0; i < newComponentData.size(); i++) {
																		if(newComponentData.get(i).getInstanceId().equalsIgnoreCase(componentInstance)){
																			newArea = newComponentData.get(i).getTamanox() * newComponentData.get(i).getTamanoy();
																		}
																	}
																	for(int i = 0; i < result.getOldComponentData().size(); i++) {
																		if(result.getOldComponentData().get(i).getInstanceId().equalsIgnoreCase(componentInstance)){
																			oldArea = result.getOldComponentData().get(i).getTamanox() * result.getOldComponentData().get(i).getTamanoy();
																		}
																	}
																	if(newArea>oldArea){//No se ha añadido, se ha borrado
																		optionres="resizebigger";
																	}else{
																		if(newArea<oldArea){
																			optionres="resizesmaller";
																		}
																	}
																	try{
																		IMM imm = (es.ual.acg.cos.modules.IMM)initialContext.lookup("java:app/cos/IMM"); //Este proceso no es necesario ya se tiene el cam, lo dejamos por si acaso
																		imm.registerinteraction("0", interaction.getDeviceType(), interaction.getInteractionType(), dateTime, userID,
																				interaction.getLatitude(), interaction.getLongitude(), optionres, componentInstance, null, null, 
																				this.getCurrentModelforUser(userID,null).getModel());
																		result.setOldComponentData(null);
																	} catch (Exception e) {
																		LOGGER.error(e);
																		result.setAllowed(false);
																	result.setMessage("> Error in Register Interaction " + e);
														
																	}
																} catch (Exception e) {
																	LOGGER.error(e);
																	result.setAllowed(false);
																	result.setMessage("> Error in Architectural Models DB " + e);
												
																
												            	}

																break;
																
										        default: 		result.setAllowed(false);
																result.setMessage("> Not found Subaction to Do Error");
										        				break;
											}

			                    			break;
			        case "groupfromdesktop":
								        	//borrar componente agrupar nuevo servicio
											/*******************************************************
											 * //2.- Comprueba que la accion sea posible
											 * //	 Sino devuelvo lista de componentes de la base de datos (getCurrentModel...) 
											 * 
											 */
											result.setAllowed(false);
											result.setMessage("> Not found Component");
											
											UIM uim1 = (es.ual.acg.cos.modules.UIM)initialContext.lookup("java:app/cos/UIM"); //Este proceso no es necesario ya se tiene el cam, lo dejamos por si acaso
											resultUIM = uim1.queryCamUser(userID);
											if (!resultUIM.getValue().equalsIgnoreCase("-1")){ //Sino hay error en cam
												String camID = resultUIM.getValue();
												try{
													ManageArchitectures manageArchitectures = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
													
													//cambiamos las propiedades
													for(int i = 0; i < newComponentData.size(); i++) {
														if (!newComponentData.get(i).getInstanceId().equalsIgnoreCase(componentInstance)){
														RuntimeProperty runtimePropertyX = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
														runtimePropertyX.setPropertyID("posX");
														runtimePropertyX.setPropertyValue(newComponentData.get(i).getPosx()+"");
														manageArchitectures.changeComponentRuntimeProperties(newComponentData.get(i).getInstanceId(), runtimePropertyX);												
														
														RuntimeProperty runtimePropertyY = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
														runtimePropertyY.setPropertyID("posY");
														runtimePropertyY.setPropertyValue(newComponentData.get(i).getPosy()+"");
														manageArchitectures.changeComponentRuntimeProperties(newComponentData.get(i).getInstanceId(), runtimePropertyY);
														
														
														//cambia numero servicios y lista de servicios
														for(int j = 0; j < newComponentData.get(i).getServicios().size(); j++) {
															List<RuntimeProperty> runtimePropertyList = new ArrayList<RuntimeProperty>();
															
															RuntimeProperty runtimePropertyServInsta =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();																		
															runtimePropertyServInsta.setPropertyID("servicio"+j+".instancia");
															runtimePropertyServInsta.setPropertyValue(newComponentData.get(i).getServicios().get(j).getInstanceId());
															runtimePropertyList.add(runtimePropertyServInsta);
															
															RuntimeProperty runtimePropertyServName =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyServName.setPropertyID("servicio"+j+".name_componente");
															runtimePropertyServName.setPropertyValue(newComponentData.get(i).getServicios().get(j).getComponentname());
															runtimePropertyList.add(runtimePropertyServName);								
															
															RuntimeProperty runtimePropertyServAlias =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyServAlias.setPropertyID("servicio"+j+".alias_componente");
															runtimePropertyServAlias.setPropertyValue(newComponentData.get(i).getServicios().get(j).getComponentalias());
															runtimePropertyList.add(runtimePropertyServAlias);
															
															RuntimeProperty runtimePropertyServMapaKML =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyServMapaKML.setPropertyID("servicio"+j+".mapa_KML");
															runtimePropertyServMapaKML.setPropertyValue(newComponentData.get(i).getServicios().get(j).getMapaKML());
															runtimePropertyList.add(runtimePropertyServMapaKML);
															
															RuntimeProperty runtimePropertyServCapa =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyServCapa.setPropertyID("servicio"+j+".capa");
															runtimePropertyServCapa.setPropertyValue(newComponentData.get(i).getServicios().get(j).getCapa());
															runtimePropertyList.add(runtimePropertyServCapa);

															//Cambiamos la lista de servicios
															manageArchitectures.changeComponentService(newComponentData.get(i).getInstanceId(), newComponentData.get(i).getNumero_servicios(), runtimePropertyList);
															
														} //for para servicios
														}//fin if
													}//for para componentes
													
													//Borro el componente
													//LOGGER.info("*****Borrado********************************************************************************");
													manageArchitectures.deleteComponent(camID, componentInstance);
													result.setAllowed(true);
													result.setMessage("> Successfully Grouped from desktop");
													
													/*******************************************************
													 * //	Registrar interaccion
													 /*******************************************************/
													
													String cotgsgetIdGroup="";
													//Buscamos el Cotsget que recibe el nuevo servicio 
													for(int i = 0; i < newComponentData.size(); i++) {
														for(int j = 0; j < newComponentData.get(i).getServicios().size(); j++) {
															if(newComponentData.get(i).getServicios().get(j).getInstanceId().equalsIgnoreCase(componentInstance)){
																	cotgsgetIdGroup = newComponentData.get(i).getInstanceId();
															}
														}
													}
													//Rescatamos la lista de servicios actual del Cotsget al que se le ha agrupado el servicio
													for(int i = 0; i < newComponentData.size(); i++) {
														if(newComponentData.get(i).getInstanceId().equalsIgnoreCase(cotgsgetIdGroup)){
															for(int j = 0; j < newComponentData.get(i).getServicios().size(); j++) {
																groupComponent.add(newComponentData.get(i).getServicios().get(j).getInstanceId());
															}
														}
													}
													
													try{
														IMM imm = (es.ual.acg.cos.modules.IMM)initialContext.lookup("java:app/cos/IMM"); //Este proceso no es necesario ya se tiene el cam, lo dejamos por si acaso
														imm.registerinteraction("0", interaction.getDeviceType(), interaction.getInteractionType(), dateTime, userID,
																interaction.getLatitude(), interaction.getLongitude(), "group", componentInstance, groupComponent, null, 
																this.getCurrentModelforUser(userID,null).getModel());
														result.setOldComponentData(null);
													} catch (Exception e) {
														LOGGER.error(e);
														result.setAllowed(false);
														result.setMessage("> Error in Register Interaction " + e);
													
													}
												} catch (Exception e) {
													LOGGER.error(e);
													result.setAllowed(false);
													result.setMessage("> Error in Architectural Models DB " + e);
											
												}
											}else{ //Errores producidos en UIM y en ManagerUSer
												result.setAllowed(false);
												result.setMessage(resultUIM.getMessage());
												//result.setOldComponentData(this.getCurrentModelforUser(userID,null).getModel());
											}
					    					break;
			        case "ungroupfornew":
								        	/*******************************************************
											 * //2.- Comprueba que la accion sea posible
											 * //	 Sino devuelvo lista de componentes de la base de datos (getCurrentModel...) 
											 * 
											 */
											result.setAllowed(false);
											result.setMessage("> Not found Component");
											try {
												ManageArchitectures manageArchitectures = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
												for(int i = 0; i < newComponentData.size(); i++) {
													if (newComponentData.get(i).getInstanceId().equalsIgnoreCase(componentInstance)){
														//Si el componente es el nuevo desagrupado
														
														List<RuntimeProperty> runtimePropertyList = new ArrayList<RuntimeProperty>();
											        	try {
															//4.- Completo el resto de campos que no me viene por parametros del nuevo componente
															ManageComponentSpecifications manageRegister = (es.ual.acg.cos.controllers.ManageComponentSpecifications)initialContext.lookup("java:app/cos/ManageRegister");
															runtimePropertyList = manageRegister.readComponentProperty(newComponentData.get(i).getServicios().get(0).getComponentname());

											        	} catch (Exception e) {

											        		LOGGER.error(e);
															result.setAllowed(false);
															result.setMessage("> Error in Component specification BD: " + e);
														
														}	

															//codeHTML
															RuntimeProperty runtimePropertyCodeHTML =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyCodeHTML.setPropertyID("codeHTML");
															runtimePropertyCodeHTML.setPropertyValue(composeDiv(newComponentData.get(i).getServicios().get(0).getComponentname(), newComponentData.get(i).getInstanceId(), userID));
															runtimePropertyList.add(runtimePropertyCodeHTML);

															//objectJava
															RuntimeProperty runtimePropertyObjectJava =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyObjectJava.setPropertyID("objectJava");
															runtimePropertyObjectJava.setPropertyValue("");
															runtimePropertyList.add(runtimePropertyObjectJava);
														
															//jarJava
															RuntimeProperty runtimePropertyJarJava =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyJarJava.setPropertyID("jarJava");
															runtimePropertyJarJava.setPropertyValue("");
															runtimePropertyList.add(runtimePropertyJarJava);
															
															//id_html
															RuntimeProperty runtimePropertyIdHTML =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyIdHTML.setPropertyID("id_html");
															runtimePropertyIdHTML.setPropertyValue(newComponentData.get(i).getIdHtml());
															runtimePropertyList.add(runtimePropertyIdHTML);
												
															//PosX
															RuntimeProperty runtimePropertyPosX =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyPosX.setPropertyID("posX");
															runtimePropertyPosX.setPropertyValue(newComponentData.get(i).getPosx()+"");
															runtimePropertyList.add(runtimePropertyPosX);
										
															//PosY
															RuntimeProperty runtimePropertyPosY =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyPosY.setPropertyID("posY");
															runtimePropertyPosY.setPropertyValue(newComponentData.get(i).getPosy()+"");
															runtimePropertyList.add(runtimePropertyPosY);
												
															//Num_servicios
															RuntimeProperty runtimePropertyNumSer =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyNumSer.setPropertyID("numero_servicios");
															runtimePropertyNumSer.setPropertyValue("1");
															runtimePropertyList.add(runtimePropertyNumSer);
								
															// Instancia
															RuntimeProperty runtimePropertyInst =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyInst.setPropertyID("servicio0.instancia");
															runtimePropertyInst.setPropertyValue(newComponentData.get(i).getInstanceId());
															runtimePropertyList.add(runtimePropertyInst);
								
															// name_componente
															RuntimeProperty runtimePropertyIdCom =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyIdCom.setPropertyID("servicio0.name_componente");
															runtimePropertyIdCom.setPropertyValue(newComponentData.get(i).getServicios().get(0).getComponentname());
															runtimePropertyList.add(runtimePropertyIdCom);
								
															// alias_componente
															RuntimeProperty runtimePropertyAlias =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyAlias.setPropertyID("servicio0.alias_componente");
															runtimePropertyAlias.setPropertyValue(newComponentData.get(i).getServicios().get(0).getComponentalias());
															runtimePropertyList.add(runtimePropertyAlias);
															//LOGGER.info("---terminados los runtime-----------------------------------------------------------------");
															// mapa_KML y capa se deben leer de la especificación del componente
															
															//4.1 Creo el nuevo componente concreto						
															ConcreteComponent concreteComponent = Architectural_metamodelFactory.eINSTANCE.createConcreteComponent();
															
															concreteComponent.setComponentName(newComponentData.get(i).getServicios().get(0).getComponentname());
															concreteComponent.setComponentAlias(newComponentData.get(i).getServicios().get(0).getComponentalias());
															concreteComponent.setComponentInstance(newComponentData.get(i).getInstanceId());
															concreteComponent.setComponentType(ComponentType.USER_INTERACTION);
								
															//Initialize the port
															InputPort inputPort = Architectural_metamodelFactory.eINSTANCE.createInputPort();
															inputPort.setPortID("inputport");
															inputPort.setCc(concreteComponent);
								
															//4.2 Añadir el nuevo componente al modelo en la base de datos
															UIM uim2 = (es.ual.acg.cos.modules.UIM)initialContext.lookup("java:app/cos/UIM"); //Este proceso no es necesario ya se tiene el cam, lo dejamos por si acaso
															resultUIM = uim2.queryCamUser(userID);
															if (!resultUIM.getValue().equalsIgnoreCase("-1")){ //Sino hay error en cam
																String camID = resultUIM.getValue();
																manageArchitectures.addComponent(camID, concreteComponent, inputPort, runtimePropertyList);
															}else{ //Errores producidos en UIM y en ManagerUSer
																result.setAllowed(false);
																result.setMessage(resultUIM.getMessage());
															
															}
													}else{ //Si no es el nuevo que se crea se modifican las posiciones
														RuntimeProperty runtimePropertyX = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
														runtimePropertyX.setPropertyID("posX");
														runtimePropertyX.setPropertyValue(newComponentData.get(i).getPosx()+"");
														manageArchitectures.changeComponentRuntimeProperties(newComponentData.get(i).getInstanceId(), runtimePropertyX);												
														
														RuntimeProperty runtimePropertyY = Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
														runtimePropertyY.setPropertyID("posY");
														runtimePropertyY.setPropertyValue(newComponentData.get(i).getPosy()+"");
														manageArchitectures.changeComponentRuntimeProperties(newComponentData.get(i).getInstanceId(), runtimePropertyY);
														
														//cambia numero servicios y lista de servicios
														for(int j = 0; j < newComponentData.get(i).getServicios().size(); j++) {
															List<RuntimeProperty> runtimePropertyList2 = new ArrayList<RuntimeProperty>();
															
															RuntimeProperty runtimePropertyServInsta =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();																		
															runtimePropertyServInsta.setPropertyID("servicio"+j+".instancia");
															runtimePropertyServInsta.setPropertyValue(newComponentData.get(i).getServicios().get(j).getInstanceId());
															runtimePropertyList2.add(runtimePropertyServInsta);
															
															RuntimeProperty runtimePropertyServName =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyServName.setPropertyID("servicio"+j+".name_componente");
															runtimePropertyServName.setPropertyValue(newComponentData.get(i).getServicios().get(j).getComponentname());
															runtimePropertyList2.add(runtimePropertyServName);								
															
															RuntimeProperty runtimePropertyServAlias =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyServAlias.setPropertyID("servicio"+j+".alias_componente");
															runtimePropertyServAlias.setPropertyValue(newComponentData.get(i).getServicios().get(j).getComponentalias());
															runtimePropertyList2.add(runtimePropertyServAlias);
															
															RuntimeProperty runtimePropertyServMapaKML =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyServMapaKML.setPropertyID("servicio"+j+".mapa_KML");
															runtimePropertyServMapaKML.setPropertyValue(newComponentData.get(i).getServicios().get(j).getMapaKML());
															runtimePropertyList2.add(runtimePropertyServMapaKML);
															
															RuntimeProperty runtimePropertyServCapa =  Architectural_metamodelFactory.eINSTANCE.createRuntimeProperty();
															runtimePropertyServCapa.setPropertyID("servicio"+j+".capa");
															runtimePropertyServCapa.setPropertyValue(newComponentData.get(i).getServicios().get(j).getCapa());
															runtimePropertyList2.add(runtimePropertyServCapa);

															//Cambiamos la lista de servicios
															manageArchitectures.changeComponentService(newComponentData.get(i).getInstanceId(), newComponentData.get(i).getNumero_servicios(), runtimePropertyList2);
															result.setAllowed(true);
															result.setMessage("> Successfully Ungrouped for new");
														}	
													}
													
												}//fin for
												
												//cambiamos los datos del componente para que sean iguales que los del servicio base en caso de haber borrado este
												for(int i = 0; i < newComponentData.size(); i++) { //Si el servicio base es distinto que los datos del componente lo cambiamos
													if(!newComponentData.get(i).getServicios().get(0).getInstanceId().equals(newComponentData.get(i).getInstanceId())){
														manageArchitectures.changeComponentProperties(newComponentData.get(i).getInstanceId(),
																							newComponentData.get(i).getServicios().get(0).getInstanceId(),
																							newComponentData.get(i).getServicios().get(0).getComponentname(),
																							newComponentData.get(i).getServicios().get(0).getComponentalias());
													}
												}
												/*******************************************************
												 * //	Registrar interaccion
												 /*******************************************************/
												try{
													String cotgsgetIdUngroup="";
													//Buscamos en la antiguo modelo el Cotsget al que se le ha desagrupado el servicio
													for(int i = 0; i < result.getOldComponentData().size(); i++) {
														for(int j = 0; j < result.getOldComponentData().get(i).getServicios().size(); j++) {	
															if(result.getOldComponentData().get(i).getServicios().get(j).getInstanceId().equalsIgnoreCase(componentInstance)){
																cotgsgetIdUngroup = result.getOldComponentData().get(i).getInstanceId();
															}
														}
													}
													List<ComponentData> nuevomodelo = this.getCurrentModelforUser(userID,null).getModel();
													//Rescatamos la lista de servicios actual del Cotsget al que se le ha desagrupado el servicio
													for(int i = 0; i < nuevomodelo.size(); i++) {
														if(nuevomodelo.get(i).getInstanceId().equalsIgnoreCase(cotgsgetIdUngroup)){
															for(int j = 0; j < nuevomodelo.get(i).getServicios().size(); j++) {
																ungroupComponent.add(nuevomodelo.get(i).getServicios().get(j).getInstanceId());
															}
														}
													}
													IMM imm = (es.ual.acg.cos.modules.IMM)initialContext.lookup("java:app/cos/IMM"); //Este proceso no es necesario ya se tiene el cam, lo dejamos por si acaso
														imm.registerinteraction("0", interaction.getDeviceType(), interaction.getInteractionType(), dateTime, userID,
																interaction.getLatitude(), interaction.getLongitude(), "ungroup", componentInstance, null, ungroupComponent, 
															this.getCurrentModelforUser(userID,null).getModel());
														result.setOldComponentData(null);
												} catch (Exception e) {
													LOGGER.error(e);
													result.setAllowed(false);
													result.setMessage("> Error in Register Interaction " + e);
												}
											} catch (Exception e) {
												LOGGER.error(e);
												result.setAllowed(false);
												result.setMessage("> Error in Architectural Models DB " + e);
												
							            	}
			                    			break;
			        default: 				break;
				}
			}else{
				result.setAllowed(false);
				result.setMessage("> Internal Server Error");
			}
		} catch (Exception e) {
			LOGGER.error(e);
			result.setMessage("> Internal Server Error");
			result.setAllowed(false);
		}
		return result;
	}
			    
	
	
	private String composeDiv(String componentName, String componentInstance, String userId) {
		String subStr = "http://150.214.150.164/wookie/deploy/" + componentName.substring(7, componentName.length()-1) + 
				"/index.html?idkey=" + componentInstance + 
				"&proxy=http://150.214.150.164:80/wookie/proxy&st=&nodejsURL=150.214.150.116:6969&userID=" + userId;		
		
		String divString = "<iframe allowfullscreen src=\"" + subStr + "\"></iframe>";

		return divString;
	}
	public InterModulesData ReadModelforcamId(String camID) {
		
		InterModulesData interModulesData = new InterModulesData();
		interModulesData.setValue("");
		interModulesData.setModel(null);
		interModulesData.setCam(null);
		interModulesData.setWidget(null);
		interModulesData.setMessage("> Cam OK");
		
		try {
			Context initialContext;
			initialContext = new InitialContext();
			ManageArchitectures ma = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
			
			interModulesData.setCam( ma.readModel(camID) );

		} catch (Exception e) {
			LOGGER.error(e);
			interModulesData.setMessage("> Error in Architectural Models BD " + e);
			interModulesData.setValue("-1");
		} 

		return interModulesData;
	}
	public InterModulesData SaveModelforcamId(ConcreteArchitecturalModel cam) {
		
		InterModulesData interModulesData = new InterModulesData();
		interModulesData.setValue("");
		interModulesData.setModel(null);
		interModulesData.setCam(null);
		interModulesData.setWidget(null);
		interModulesData.setMessage("> Cam OK");
		
		try {
			Context initialContext;
			initialContext = new InitialContext();
			ManageArchitectures ma = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
			ma.saveModel(cam);

		} catch (Exception e) {
			LOGGER.error(e);
			interModulesData.setMessage("> Error in Architectural Models BD " + e);
			interModulesData.setValue("-1");
		} 

		return interModulesData;
	}
	public InterModulesData DeleteModelforcamId(String cam) {
		
		InterModulesData interModulesData = new InterModulesData();
		interModulesData.setValue("");
		interModulesData.setModel(null);
		interModulesData.setCam(null);
		interModulesData.setWidget(null);
		interModulesData.setMessage("> Cam OK");
		
		try {
			Context initialContext;
			initialContext = new InitialContext();
			ManageArchitectures ma = (es.ual.acg.cos.controllers.ManageArchitectures)initialContext.lookup("java:app/cos/ManageArchitectures");
			ma.withdrawCAM(cam);

		} catch (Exception e) {
			LOGGER.error(e);
			interModulesData.setMessage("> Error in Architectural Models BD " + e);
			interModulesData.setValue("-1");
		} 

		return interModulesData;
	}
	public InterModulesData getWidget(String userID, String componentName, String componentAlias) {
		InterModulesData interModulesData = new InterModulesData();
		interModulesData.setValue("");
		interModulesData.setModel(null);
		interModulesData.setCam(null);
		interModulesData.setWidget(null);
		interModulesData.setMessage("> Widget OK");
		try {
			Context initialContext;
			initialContext = new InitialContext();
			ManageWookie wookie = (es.ual.acg.cos.controllers.ManageWookie)initialContext.lookup("java:app/cos/ManageWookie");
			interModulesData.setWidget(wookie.getOrCreateWidgetInstance(userID, componentName, componentAlias));

		} catch (Exception e) {
			LOGGER.error(e);
			interModulesData.setMessage("> Error in Wookie " + e);
			interModulesData.setValue("-1");
		}
		return interModulesData;
	}
	
	public ConcreteArchitecturalModel getCAM() {
		return cam;
	}

	public void setCAM(ConcreteArchitecturalModel cam) {
		this.cam = cam;
	}

}
