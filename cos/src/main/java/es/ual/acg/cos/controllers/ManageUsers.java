/*
 * ManageUsers.java -- Gestiona la base de datos de usuarios.
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * ManageUsers.java is part of COScore Community.
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
package es.ual.acg.cos.controllers;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ManageUsers {
	
	private static final Logger LOGGER = Logger.getLogger(ManageUsers.class);

	// Variable para conectar con la BD
	Connection conn = null;

	// @PostConstruct
	public void initialize() throws SQLException, ClassNotFoundException {
		
		// Establecimiento de conexión con la base de datos
		String url = "jdbc:postgresql://150.214.150.116:5432/architecturalmodelsjesus33";
		String login = "postgres";
		String password = "root";
		conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = (Connection) DriverManager.getConnection(url, login,	password);
			if (conn != null) {
			}
		} catch (SQLException ex) {
			LOGGER.info(ex);
			throw new SQLException(ex);
		} catch (ClassNotFoundException ex) {
			LOGGER.info(ex);
			throw new ClassNotFoundException();
		}
	}

	public int queryUser(String userName, String userPassword) throws ClassNotFoundException, SQLException {
		int id_resultado;
		ResultSet rs;
		initialize();
		Statement s = (Statement) conn.createStatement();
		rs = s.executeQuery("Select user_id From coscoreuser Where user_name = '" + userName 
											+ "' and user_password = '" + userPassword + "'");
		if (rs.next()) {
			id_resultado = rs.getInt(1);
		} else {
			id_resultado = -1;
		}
		s.close();
		conn.close();
		
		return id_resultado;
	}


	public String queryCamUser(String userId) throws ClassNotFoundException, SQLException{
			String id_resultado = "-1";
			initialize();
			ResultSet rs;
			Statement s = (Statement) conn.createStatement();

			rs = s.executeQuery("Select camid From coscoreuser Where user_id = '"+ userId + "'");
			if (rs.next()) {
				id_resultado = rs.getString(1);
			} 

			rs.close();
			conn.close();

			return id_resultado;
	}
	public String queryCamProfile(String Profilename) throws ClassNotFoundException, SQLException{
		String camid_resultado = "-1";
		initialize();
		ResultSet rs;
		Statement s = (Statement) conn.createStatement();

		rs = s.executeQuery("Select camid From coscoreprofile Where profile_name = '"+ Profilename + "'");
		if (rs.next()) {
			camid_resultado = rs.getString(1);
		} 

		rs.close();
		conn.close();

		return camid_resultado;
}
	
	public List<String> queryProfile() throws ClassNotFoundException, SQLException{
		List<String> perfiles = new ArrayList<String>();

		initialize();
		ResultSet rs;
		Statement s = (Statement) conn.createStatement();

		rs = s.executeQuery("Select profile_name From coscoreprofile");
		if (rs.next()) {
			perfiles.add(rs.getString("profile_name"));
			while (rs.next())
			{
				perfiles.add(rs.getString("profile_name"));
			}
		}else{
			perfiles.set(0, null);
		}
		rs.close();
		conn.close();

		return perfiles;
}
	
	public boolean deleteUser(String userId) throws Exception{
		boolean response;
		
		initialize();
		Statement s = (Statement) conn.createStatement();
		int x = s.executeUpdate("DELETE FROM coscoreuser WHERE user_id = '"+ userId + "'");
		s.close();
		conn.close();
		
		if (x == 0)
			response = false;
		else
			response =true;
			
		return response;
	}

	public void createUser(String userName, String userPassword, String userProfile, String camid) throws ClassNotFoundException, SQLException {
		initialize();
		Statement s = (Statement) conn.createStatement();
		s.executeUpdate("Insert Into coscoreuser (user_name, user_password, user_profile, camid) Values ('"
						+ userName
						+ "', '"
						+ userPassword
						+ "', '"
						+ userProfile
						+ "', '"
						+camid
						+"')");
		s.close();
		conn.close();
	}

	public boolean updateUser(String userId, String userNameNew,
			String userPassword, String userProfile) throws Exception, SQLException {
			
			boolean response;
			initialize();
			Statement s = (Statement) conn.createStatement();

			int x = s.executeUpdate("UPDATE coscoreuser " + "SET user_name = '"
					+ userNameNew + "'," + "user_password = '" + userPassword
					+ "'," + "user_profile = '" + userProfile + "' "
					+ "WHERE user_id = '" + userId + "'");
			s.close();
			conn.close();
						
			if (x == 0)
				response = false;
			else
				response =true;
				
			return response;
	}
}
