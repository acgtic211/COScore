package es.ual.acg.cos.users;

import java.util.Map;
import org.jboss.logging.Logger;

public class SessionControl extends Thread {

	private static final Logger LOGGER = Logger.getLogger(SessionControl.class);
	
	private static int segundosTieneUnDia = 86400;
	
	// Contiene los beans para cada usuario
	public Map<String, UserEJBs> userEJBMap;
		
	// Contiene tiempos del último uso
	public Map<String, Long> userTime;
	
	public SessionControl(Map<String, UserEJBs> userEJBMap, Map<String, Long> userTime) {
		this.userEJBMap = userEJBMap;
		this.userTime = userTime;
	}

	@Override
	public void run() {
		while(true) {
			for (int i = 0; i < this.userEJBMap.size(); i++) {
				String user = (String)userEJBMap.keySet().toArray()[i];
				Long time = userTime.get(user);
				// Delete if time without to use is...
				if((System.currentTimeMillis() - time) > (segundosTieneUnDia * 1000)){
					deleteUser(user);
				}
			}
			waitSeconds(60);
		}
	}
	
	private void deleteUser(String user) {
		LOGGER.info("SessionControl - User deleted - Id user = " + user + " (Thread " + this.getId() + ")");
		
		userEJBMap.remove(user);
		userTime.remove(user);
	}

	private void waitSeconds(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

}
