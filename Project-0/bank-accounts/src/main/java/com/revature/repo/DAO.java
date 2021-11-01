package com.revature.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// import org.postgresql.Driver;

public class DAO {
	
	public static final String server = "localhost";
	public static final String url = "jdbc:postgresql://" + server + "/postgress";
	public static final String username = "postgres";
	public static final String password = "p4ssw0rd";

	public static void main(String[] args) {

		getConnection();
		
	}

	public static void getConnection() {

		try (Connection con = DriverManager.getConnection(url, username, password)) {
			
			String query = "SELECT * FROM students";
			
			PreparedStatement ps = con.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
			}

		} catch (SQLException e) {

		}
	}

}