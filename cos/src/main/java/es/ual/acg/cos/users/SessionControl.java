/*
 * SessionControl.java -- Controla que las sesiones de usuario expiren o no, para borrar los modulos asoaciados a un usuario.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * SessionControl.java is part of COScore Community.
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
