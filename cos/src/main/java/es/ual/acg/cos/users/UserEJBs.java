/*
 * UserEJBs.java -- Crea los módulos y los asocia a un usuario.
 * Copyright (C) 2016  Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * UserEJBs.java is part of COScore Community.
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
package es.ual.acg.cos.users;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import es.ual.acg.cos.modules.DMM;
import es.ual.acg.cos.modules.IMM;
import es.ual.acg.cos.modules.LRMM;
import es.ual.acg.cos.modules.TMM;

import javax.naming.Context;

import org.jboss.logging.Logger;


public class UserEJBs {
	
	private LRMM lrmm = null;
	private TMM tmm = null;	
	private List<DMM> dmms = null;
	private IMM imm = null;
	
	private static final Logger LOGGER = Logger.getLogger(UserEJBs.class);
	
	public UserEJBs(){
		Context initialContext;
		try
		{
			initialContext = new InitialContext();

			DMM dmm = null;			
			
			try {
				lrmm = (LRMM)initialContext.lookup("java:module/LRMM");
				tmm = (TMM)initialContext.lookup("java:module/TMM");
				dmms = new ArrayList<DMM>();
				dmm = (DMM)initialContext.lookup("java:module/DMM");
				imm = (IMM)initialContext.lookup("java:module/IMM");
				dmms.add(dmm);
			} catch (NamingException e) {
				LOGGER.error(e.getMessage());
			} 
		}catch (NamingException e){
			e.printStackTrace();
		}
	}
	
	public LRMM getLRMM(){
		return lrmm;
	}
	
	public TMM getTMM(){
		return tmm;
	}
	
	public List<DMM> getDMMS(){
		return dmms;
	}
	
	public IMM getIMM(){
		return imm;
	}
	
}
