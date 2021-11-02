package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Clients;
import com.revature.util.JDBCUtility;

public class ClientsDAO {

	public void insertClients(Clients clients) throws SQLException {
		
		//boolean success = false;

		try (Connection con = JDBCUtility.getConnection()) {
			
			String sql = "INSERT INTO clients (client_first_name, client_last_name)"
					+ "VALUES (?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, clients.getFirstName());
			ps.setString(2, clients.getLastName());
			
			ps.execute();	
			
			//success = true;

		}
		
		//return success;

	}

	public List<Clients> gettAllClients() throws SQLException {

		List<Clients> listOfClients = new ArrayList<>();

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM clients";
			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				listOfClients.add(new Clients(rs.getInt("client_id"), rs.getString("client_first_name"),
						rs.getString("client_last_name")));

//				int id = rs.getInt("client_id");
//				String firstName = rs.getString("client_first_name");
//				String lastName = rs.getString("client_last_name");
//				
//				Clients clients = new Clients(id, firstName, lastName);
//				
//				listOfClients.add(clients);
			}

		}
		return listOfClients;

	}

	public Clients getClientsById(int id) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM clients WHERE client_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new Clients(rs.getInt("client_id"), rs.getString("client_first_name"),
						rs.getString("client_last_name"));
			} else {
				return null;
			}
		}
	}

}
