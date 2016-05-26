/*
 * ComponentWSImpl.java -- Web service Component - Servicio encargado de gestionar las actualizaciones sobre los componentes.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * ComponentWSImpl.java is part of COScore Community.
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
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import es.ual.acg.cos.modules.DMM;
import es.ual.acg.cos.types.InterModulesData;
import es.ual.acg.cos.ws.types.UpdateArchitectureParams;
import es.ual.acg.cos.ws.types.UpdateArchitectureResult;

@WebService(endpointInterface = "es.ual.acg.cos.ws.ComponentWS")
public class ComponentWSImpl implements ComponentWS{
	
	private static final Logger LOGGER = Logger.getLogger(ComponentWSImpl.class);
	
	public UpdateArchitectureResult updateArchitecture(UpdateArchitectureParams params){

		UpdateArchitectureResult result = new UpdateArchitectureResult();
		InterModulesData resultDMMforError = new InterModulesData();
		
		try	{
			Context initialContext = new InitialContext();
			DMM dmm = (DMM)initialContext.lookup("java:app/cos/DMM");
			boolean parametrosOk = true;
			//Obtener la antigua lista de componentes
			resultDMMforError = dmm.getCurrentModelforUser(params.getUserId(),null);
			result.setOldComponentData(resultDMMforError.getModel());
			result.setAllowed(false);
			
			if (params.getUserId() != null && params.getUserId().compareTo("") != 0) { //Si el usuario esta vacio
				if (params.getActionDone() != null){ //Si no se encuentra actionDone
				if (params.getNewsComponentData() != null){
				//Si la informacion de interaccion es distinta de nulo
				if (params.getInteraction().getDeviceType() != null && params.getInteraction().getInteractionType() != null 
						&& params.getInteraction().getLatitude() != null && params.getInteraction().getLongitude() != null) { 
				switch (params.getActionDone()){
					case "add":				for(int i = 0; i < params.getNewsComponentData().size(); i++) {
												if (params.getNewsComponentData().get(i).getComponentname() != null && params.getNewsComponentData().get(i).getComponentname().compareTo("") != 0) {
													if (params.getNewsComponentData().get(i).getComponentAlias() != null && params.getNewsComponentData().get(i).getComponentAlias().compareTo("") != 0) {
														if (params.getNewsComponentData().get(i).getIdHtml() != null && params.getNewsComponentData().get(i).getIdHtml().compareTo("") != 0) {
															if (params.getNewsComponentData().get(i).getPosx() == 0 || params.getNewsComponentData().get(i).getPosy() == 0 || params.getNewsComponentData().get(i).getPosx() == -1 || params.getNewsComponentData().get(i).getPosy() == -1) {
																LOGGER.error("Not found o Empty News Components.Posx or Posy List Error");
																result.setMessage("> Not found o Empty News Components.Posx or Posy List Error");
																parametrosOk = false;
															}
														}else{
															LOGGER.error("Not found o Empty News Components.idHtml List Error");
															result.setMessage("> Not found o Empty News Components.idHtml List Error");
															parametrosOk = false;
														}
													}else{
														LOGGER.error("Not found o Empty News Components.Alias List Error");
														result.setMessage("> Not found o Empty News Components.componentAlias List Error");
														parametrosOk = false;
													}
												}else{
													LOGGER.error("Not found o Empty News Components.componentname List Error");
													result.setMessage("> Not found o Empty News Components.componentname List Error");
													parametrosOk = false;
												}
											}//fin for
											if(parametrosOk==true){
												result = dmm.updateArchitectureforUser(	params.getUserId(), params.getComponentInstance(), 
																						params.getActionDone(),
																						params.getNewsComponentData(), 
																						params.getInteraction());
											}
											break;
											
				    case "delete":			if (params.getComponentInstance() != null && params.getComponentInstance().compareTo("") != 0) { //Si el usuario esta vacio
					    						for(int i = 0; i < params.getNewsComponentData().size(); i++) {
													if (params.getNewsComponentData().get(i).getInstanceId() != null && params.getNewsComponentData().get(i).getInstanceId().compareTo("") != 0) {
														if (params.getNewsComponentData().get(i).getPosx() == 0 || params.getNewsComponentData().get(i).getPosy() == 0 || params.getNewsComponentData().get(i).getPosx() == -1 || params.getNewsComponentData().get(i).getPosy() == -1) {
															LOGGER.error("Not found o Empty News Components.Posx or Posy List Error");
															result.setMessage("> Not found o Empty News Components.Posx or Posy List Error");
															parametrosOk = false;
														}
													}else{
														LOGGER.error("Not found o Empty News Components.instanceID List Error");
														result.setMessage("> Not found o Empty News Components.instanceID List Error");
														parametrosOk = false;
													}
												}//fin for
										    }else{
												LOGGER.error("Not found o Empty ComponentsInstance Error");
												result.setMessage("> Not found o Empty ComponentsInstance Error");
												parametrosOk = false;
											}
											if(parametrosOk==true){
												result = dmm.updateArchitectureforUser(	params.getUserId(), params.getComponentInstance(), 
																						params.getActionDone(),
																						params.getNewsComponentData(), 
																						params.getInteraction());
											}
											break;

					case "changeproperty":	
											if (params.getComponentInstance() != null) { //Si el usuario esta vacio
												for(int i = 0; i < params.getNewsComponentData().size(); i++) {
													if (params.getNewsComponentData().get(i).getInstanceId() == null || params.getNewsComponentData().get(i).getInstanceId().compareTo("") == 0) {
														LOGGER.error("Not found o Empty News Components.instanceID List Error");
														result.setMessage("> Not found o Empty News Components.instanceID List Error");
														parametrosOk = false;
													}
												}//fin for
										    }else{
												LOGGER.error("Not found o Empty ComponentsInstance Error");
												result.setMessage("> Not found o Empty ComponentsInstance Error");
												parametrosOk = false;
											}
											if(parametrosOk==true){
												result = dmm.updateArchitectureforUser(	params.getUserId(), params.getComponentInstance(), 
																						params.getActionDone(),
																						params.getNewsComponentData(), 
																						params.getInteraction());
											}
											break;
					case "groupfromdesktop":if (params.getComponentInstance() != null && params.getComponentInstance().compareTo("") != 0) { //Si el usuario esta vacio
												for(int i = 0; i < params.getNewsComponentData().size(); i++) {
													if (params.getNewsComponentData().get(i).getInstanceId() != null && params.getNewsComponentData().get(i).getInstanceId().compareTo("") != 0) {
														if (params.getNewsComponentData().get(i).getNumero_servicios() != 0 && params.getNewsComponentData().get(i).getNumero_servicios() != -1) {
															if (params.getNewsComponentData().get(i).getPosx() == 0 || params.getNewsComponentData().get(i).getPosy() == 0 || params.getNewsComponentData().get(i).getPosx() == -1 || params.getNewsComponentData().get(i).getPosy() == -1) {
																LOGGER.error("Not found o Empty News Components.Posx or Posy List Error");
																result.setMessage("> Not found o Empty News Components.Posx or Posy List Error");
																parametrosOk = false;
															}else{
																for(int j = 0; j <  params.getNewsComponentData().get(i).getServicios().size(); j++) {
																	if ( params.getNewsComponentData().get(i).getServicios().get(j).getInstanceId() != null && params.getNewsComponentData().get(i).getServicios().get(j).getInstanceId().compareTo("") != 0) {
																		if ( params.getNewsComponentData().get(i).getServicios().get(j).getComponentname() != null && params.getNewsComponentData().get(i).getServicios().get(j).getComponentname().compareTo("") != 0) {
																			if ( params.getNewsComponentData().get(i).getServicios().get(j).getComponentalias() == null || params.getNewsComponentData().get(i).getServicios().get(j).getComponentalias().compareTo("") == 0) {
																				LOGGER.error("Not found o Empty News Components.Servicios.componentalias List Error");
																				result.setMessage("> Not found o Empty News Components.Servicios.componentalias List Error");
																				parametrosOk = false;
																			}
																		}
																		else{
																			LOGGER.error("Not found o Empty News Components.Servicios.componentname List Error");
																			result.setMessage("> Not found o Empty News Components.Servicios.componentname List Error");
																			parametrosOk = false;
																		}
																	}
																	else{
																		LOGGER.error("Not found o Empty News Components.Servicios.instanceId List Error");
																		result.setMessage("> Not found o Empty News Components.Servicios.instanceId List Error");
																		parametrosOk = false;
																	}
																}
															}
														}else{
															LOGGER.error("Not found o Empty News Components.Numero_servicios List Error");
															result.setMessage("> Not found o Empty News Components.Numero_servicios List Error");
															parametrosOk = false;
														}
													}else{
														LOGGER.error("Not found o Empty News Components.InstanceID List Error");
														result.setMessage("> Not found o Empty News Components.InstanceID List Error");
														parametrosOk = false;
													}
												}//fin for
										    }else{
												LOGGER.error("Not found o Empty ComponentsInstance Error");
												result.setMessage("> Not found o Empty ComponentsInstance Error");
												parametrosOk = false;
											}
											if(parametrosOk==true){
												result = dmm.updateArchitectureforUser(	params.getUserId(), params.getComponentInstance(), 
																						params.getActionDone(),
																						params.getNewsComponentData(), 
																						params.getInteraction());
											}
											break;
					case "ungroupfornew":	if (params.getComponentInstance() != null && params.getComponentInstance().compareTo("") != 0) { //Si el usuario esta vacio
												for(int i = 0; i < params.getNewsComponentData().size(); i++) {
													if (params.getNewsComponentData().get(i).getInstanceId() != null && params.getNewsComponentData().get(i).getInstanceId().compareTo("") != 0) {
														if (params.getNewsComponentData().get(i).getPosx() != 0 && params.getNewsComponentData().get(i).getPosy() != 0 && params.getNewsComponentData().get(i).getPosx() != -1 && params.getNewsComponentData().get(i).getPosy() != -1) {
															if (params.getNewsComponentData().get(i).getNumero_servicios() != 0 && params.getNewsComponentData().get(i).getNumero_servicios() != -1) {
																for(int j = 0; j <  params.getNewsComponentData().get(i).getServicios().size(); j++) {
																	if ( params.getNewsComponentData().get(i).getServicios().get(j).getInstanceId() != null && params.getNewsComponentData().get(i).getServicios().get(j).getInstanceId().compareTo("") != 0) {
																		if ( params.getNewsComponentData().get(i).getServicios().get(j).getComponentname() != null && params.getNewsComponentData().get(i).getServicios().get(j).getComponentname().compareTo("") != 0) {
																			if ( params.getNewsComponentData().get(i).getServicios().get(j).getComponentalias() == null || params.getNewsComponentData().get(i).getServicios().get(j).getComponentalias().compareTo("") == 0) {
																				LOGGER.error("Not found o Empty News Components.Servicios.componentalias List Error");
																				result.setMessage("> Not found o Empty News Components.Servicios.componentalias List Error");
																				parametrosOk = false;
																			}
																		}
																		else{
																			LOGGER.error("Not found o Empty News Components.Servicios.componentname List Error");
																			result.setMessage("> Not found o Empty News Components.Servicios.componentname List Error");
																			parametrosOk = false;
																		}
																	}
																	else{
																		LOGGER.error("Not found o Empty News Components.Servicios.instanceId List Error");
																		result.setMessage("> Not found o Empty News Components.Servicios.instanceId List Error");
																		parametrosOk = false;
																	}
																}
															}else{
																LOGGER.error("Not found o Empty News Components.Numero_servicios List Error");
																result.setMessage("> Not found o Empty News Components.Numero_servicios List Error");
																parametrosOk = false;
															}
														}else{
															LOGGER.error("Not found o Empty News Components.Posx or Posy List Error");
															result.setMessage("> Not found o Empty News Components.Posx or Posy List Error");
															parametrosOk = false;
														}
													}else{
														LOGGER.error("Not found o Empty News Components.instanceID List Error");
														result.setMessage("> Not found o Empty News Components.instanceID List Error");
														parametrosOk = false;
													}
												}//fin for
										    }else{
												LOGGER.error("Not found o Empty ComponentsInstance Error");
												result.setMessage("> Not found o Empty ComponentsInstance Error");
												parametrosOk = false;
											}
											if(parametrosOk==true){
												result = dmm.updateArchitectureforUser(	params.getUserId(), params.getComponentInstance(), 
																						params.getActionDone(),
																						params.getNewsComponentData(), 
																						params.getInteraction());
											}
											break;
					default: 				LOGGER.error("Not found o Empty Action Done Error");
											result.setMessage("> Not found o Empty Action Done Error");	
											break;
				}//fin switch
				
				} else {
					LOGGER.error("Not found Interaction Information Error");
					result.setMessage("> Not found Interaction Information Error");
				}
			} else {
					LOGGER.error("Not found Component");
					result.setMessage("> Not found Component");	
			}
			} else {
				LOGGER.error("Not found o Empty Action Done Error");
				result.setMessage("> Not found o Empty Action Done Error");	
			}
			} else {
				LOGGER.error("Not found o Empty UserId Error");
				result.setMessage("> Not found o Empty UserId Error");
			}
		} catch (NamingException e) {
			LOGGER.error(e);
			result.setOldComponentData(null);
			result.setAllowed(false);
			result.setMessage("> Internal Server Error");
		}
		return result;
	}
}
