package es.ual.acg.cos.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import es.ual.acg.cos.users.SessionControl;
import es.ual.acg.cos.users.UserEJBs;
import es.ual.acg.cos.modules.DMM;
import es.ual.acg.cos.modules.IMM;
import es.ual.acg.cos.modules.TMM;
import es.ual.acg.cos.types.InterModulesData;
import es.ual.acg.cos.types.UserInteractionData;
import es.ual.acg.cos.ws.types.CreateUserParams;
import es.ual.acg.cos.ws.types.CreateUserResult;
import es.ual.acg.cos.ws.types.DefaultInitSessionResult;
import es.ual.acg.cos.ws.types.LoginSessionResult;
import es.ual.acg.cos.ws.types.LogoutSessionResult;
import es.ual.acg.cos.ws.types.QueryUserParams;
import es.ual.acg.cos.ws.types.QueryUserResult;
import es.ual.acg.cos.ws.types.InitUserArchitectureSessionResult;


@Singleton
@Startup
@Lock(LockType.READ)
public class COSSessionMM {
	private static final Logger LOGGER = Logger.getLogger(COSSessionMM.class);
	
	// Contiene los beans para cada usuario
	private Map<String, UserEJBs> userEJBMap;
	
	// Contiene tiempos del último uso
	private Map<String, Long> userTime;
	
	SessionControl sc;
	
	@PostConstruct
	public void initContexts() {
		userEJBMap = new HashMap<String, UserEJBs>();
		userTime = new HashMap<String, Long>();

		sc = new SessionControl(userEJBMap, userTime);
		sc.start();
	}
	
	/**
	 * Crear los beans asociados a un usuario tras un login
	 * @param userID
	 * @return
	 */
	public LoginSessionResult initializeModules(String user, String password){
		
		LoginSessionResult result = new LoginSessionResult();
		
		try {
			Context	initialContext = new InitialContext();
			
			UIM uim = (UIM)initialContext.lookup("java:app/cos/UIM");
			
			QueryUserParams qup = new QueryUserParams();
			qup.setUserName(user);
			qup.setUserPassword(password);
			QueryUserResult qur = uim.queryUser(qup);
			
			//Realizo queryuser y compruebo valiacion correcta
			if(qur.isValidation() == true){
				
				//Inicializo las variables de sesión (los EJBS para el usuario validado)
				if(userEJBMap.containsKey(qur.getIduser()+"") == false){
					//UserEJBs uejb = new UserEJBs(qur.getIduser()+"");
					UserEJBs uejb = new UserEJBs();
					userEJBMap.put(qur.getIduser()+"", uejb);
					userTime.put(qur.getIduser()+"", System.currentTimeMillis());
					
					result.setValidation(true);
					result.setMessage("> Modules initialized");
					result.setUserId(qur.getIduser()+"");
				} else {
					result.setValidation(true); //El usuario es valido, pero no se puede inicializar 2 veces los modulos
					result.setMessage("> Error Modules previously initialized");
					result.setUserId(qur.getIduser()+"");
				}
			} else {
				result.setValidation(qur.isValidation());
				result.setMessage(qur.getMessage());
				result.setUserId(qur.getIduser()+"");
			}

		} catch (NamingException e) {
			LOGGER.error(e);
			result.setValidation(false);
			result.setUserId("-1");
			result.setMessage("> Internal Server Error");
		}
				
		return result;
	}
	
	/**
	 * Destruir los beans asociados a un usuario tras un login
	 * @param userID
	 * @return
	 */
	@Lock(LockType.READ)
	public LogoutSessionResult destroyModules(String userID){
		
		LogoutSessionResult result = new LogoutSessionResult();
		
		//Compruebo la existencia del ID de session
		if(userEJBMap.containsKey(userID) == true){
			userEJBMap.remove(userID);
			
			result.setDeleted(true);
			result.setMessage("> Modules deleted");

		} else {
			result.setDeleted(false);
			result.setMessage("> Error Delete Modules");
		}
		
		return result;
	}
	
	public InitUserArchitectureSessionResult initModelforUsers(String userID, UserInteractionData interaction) {

		InitUserArchitectureSessionResult result = new InitUserArchitectureSessionResult();
		InterModulesData resultDMM = new InterModulesData();
		InterModulesData resultUIM = new InterModulesData();
		
		Context initialContext;
		try	{
			initialContext = new InitialContext();

			if(userEJBMap.containsKey(userID) == true){
				DMM dmm = userEJBMap.get(userID).getDMMS().get(0);

				//Obtengo el cam o el error producido
				UIM uim = (es.ual.acg.cos.modules.UIM)initialContext.lookup("java:app/cos/UIM");
				resultUIM = uim.queryCamUser(userID);
				if (!resultUIM.getValue().equalsIgnoreCase("-1")){ //Sino hay error en cam
					String camId = resultUIM.getValue();
					resultDMM = dmm.getCurrentModelforUser(userID, camId);
					if(!resultDMM.getValue().equalsIgnoreCase("-1")){ //Sino hay error en dmm

						result.setInit(true);
						result.setComponentData(resultDMM.getModel());
						result.setMessage(resultDMM.getMessage());
						
						if(!interaction.getDeviceType().equalsIgnoreCase("-1")){ //Si el usuario no es anonimo registro la interaccion

							/*******************************************************
							 * //	Registrar interaccion - Iniciar sesion
							 /*******************************************************/
							//Fecha y hora
							Calendar calendar = Calendar.getInstance();
							 
							String dateTime = calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.MONTH)
									   				  +"/"+calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.HOUR)
									   				  +":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND)
									   				  +":"+calendar.get(Calendar.MILLISECOND);
							
							try{
								IMM imm = (es.ual.acg.cos.modules.IMM)initialContext.lookup("java:app/cos/IMM"); //Este proceso no es necesario ya se tiene el cam, lo dejamos por si acaso
								imm.registerinteraction("1", interaction.getDeviceType(), interaction.getInteractionType(), dateTime, userID,
										interaction.getLatitude(), interaction.getLongitude(), "", "", null, null, resultDMM.getModel());
	
							} catch (Exception e) {
								LOGGER.error(e);
								result.setInit(false);
								result.setMessage("> Error in Register Interaction " + e);
								//result.setOldComponentData(this.getCurrentModelforUser(userID,null).getModel());
							}
						}
					}else{ //Errores producidos en DMM, ManageWookie y ManageArchitectures
						result.setInit(false);
						result.setComponentData(null);
						result.setMessage(resultDMM.getMessage());
					}
				}else{ //Errores producidos en UIM y en ManagerUSer
					result.setInit(false);
					result.setComponentData(null);
					result.setMessage(resultUIM.getMessage());
				}
			} else {
				result.setInit(false);
				result.setComponentData(null);
				result.setMessage("> Internal Server Error");
			}
		} catch (NamingException e) {
			LOGGER.error(e);
			result.setInit(false);
			result.setComponentData(null);
			result.setMessage("> Internal Server Error");
		}
		
		return result;
	}
	
public DefaultInitSessionResult initAnonimous(){
		
	DefaultInitSessionResult result = new DefaultInitSessionResult();
		
		try {
			Context	initialContext = new InitialContext();
			
			//CREAR USUARIO ANONIMO
			UIM uim = (UIM)initialContext.lookup("java:app/cos/UIM");

			CreateUserParams paramsCreateAnonimous = new CreateUserParams();
			Calendar dateTime = Calendar.getInstance();
			 
			String anonymousname = "anonymous-"+dateTime.get(Calendar.DATE)+"/"+dateTime.get(Calendar.MONTH)
					   				  +"/"+dateTime.get(Calendar.YEAR)+"-"+dateTime.get(Calendar.HOUR)
					   				  +":"+dateTime.get(Calendar.MINUTE)+":"+dateTime.get(Calendar.SECOND)
					   				  +":"+dateTime.get(Calendar.MILLISECOND);
			
			paramsCreateAnonimous.setUserName(anonymousname);
			paramsCreateAnonimous.setUserPassword("anonymous");
			paramsCreateAnonimous.setUserProfile("anonymousprofile"); //Este perfil debe existir en la base de datos
			
			CreateUserResult resultCreateAnonimous = uim.createUser(paramsCreateAnonimous);
			
			if(resultCreateAnonimous.isCreated() == true){ //Comprobamos que se ha creado
				
				//LOGIN USUARIO ANONIMO
				LoginSessionResult resultLogin = this.initializeModules(anonymousname, "anonymous");

				if(resultLogin.isValidation()==true){ //Comprobamos Login
	
					//INICIAR ARQUITECTURA DE USUARIO ANONIMO
					
					//Controlamos que no se registre la interaccion
					UserInteractionData noInteraction = new UserInteractionData();
					noInteraction.setDeviceType("-1");
					
					InitUserArchitectureSessionResult resultInitArchitecture = this.initModelforUsers(resultLogin.getUserId(),noInteraction);

					if(resultInitArchitecture.isInit()==true){ //Comprobamos Init
						result.setInit(resultInitArchitecture.isInit());
						result.setAnonimousId(anonymousname);
						result.setComponentData(resultInitArchitecture.getComponentData());
						result.setMessage(resultInitArchitecture.getMessage());
						
					} else { //si falla init
						LOGGER.info("fallo al iniciar login anonimo");
						result.setInit(resultInitArchitecture.isInit());
						result.setAnonimousId(anonymousname);
						result.setComponentData(null);
						result.setMessage(resultInitArchitecture.getMessage());
						
					}
				}else{ //Si falla Login
					LOGGER.info("fallo en el login anonimo");
					result.setInit(resultLogin.isValidation());
					result.setAnonimousId(resultLogin.getUserId());
					result.setComponentData(null);
					result.setMessage(resultLogin.getMessage());
				}
						
			} else { //Si falla crear el usuario anonimo
				LOGGER.info("fallo al crear usuario anonimo");
				result.setInit(resultCreateAnonimous.isCreated());
				result.setAnonimousId("-1");
				result.setComponentData(null);
				result.setMessage(resultCreateAnonimous.getMessage());
			}

		} catch (NamingException e) {
			LOGGER.error(e);
			result.setInit(false);
			result.setAnonimousId("-1");
			result.setComponentData(null);
			result.setMessage("> Internal Server Error");
		}
				
		return result;
	}
	/**
	 * Obtener el conjunto de Beans asociados a un usuario
	 * @param userID
	 * @return
	 */
	@Lock(LockType.READ)
	public UserEJBs getUserEJB(String userID) {
		return userEJBMap.get(userID);
	}
	
	@Lock(LockType.READ)
	public boolean isUserOnUserEJBMaps(String userID) {
		boolean result= false;
		if(userEJBMap.containsKey(userID) == true)
			result= true;
		return result;
	}
	public void setTime(String userID){
		//sc.userTime.replace(userID, System.currentTimeMillis());
		sc.userTime.remove(userID);
		sc.userTime.put(userID, System.currentTimeMillis());
	}

}
