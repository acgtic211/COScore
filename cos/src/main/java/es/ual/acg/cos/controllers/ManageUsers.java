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

	/**
	 * Query users from Data Base coscoreuser
	 * 
	 * @param userName
	 * @param userPassword
	 * @return boolean
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */

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

			rs = s.executeQuery("Select camid From coscoreuser Where user_id = '"
					+ userId + "'");
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

		rs = s.executeQuery("Select camid From coscoreprofile Where profile_name = '"
				+ Profilename + "'");
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

	/**
	 * Create an user
	 * 
	 * @param userName
	 * @param userPassword
	 * @param userProfile
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	/*public boolean createUser(String userName, String userPassword, String userProfile) {
		try {
			initialize();
			Statement s = (Statement) conn.createStatement();
			int x = s
					.executeUpdate("Insert Into coscoreuser (user_name, user_password, user_profile) Values ('"
							+ userName
							+ "', '"
							+ userPassword
							+ "', '"
							+ userProfile + "')");

			s.close();
			conn.close();
			if (x == 0)
				return false;
		} catch (SQLException ex) {
			LOGGER.error(ex);
		} catch (InstantiationException e) {
			LOGGER.error(e);
		} catch (IllegalAccessException e) {
			LOGGER.error(e);
		}
		return true;
	}*/
	

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

	/**
	 * Update an user from coscoreuser
	 * 
	 * @param userName
	 * @param userPassword
	 * @param userProfile
	 * @return
	 */
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
