package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.models.Clients;
import com.revature.util.JDBCUtility;

public class ClientsDAO {

	public String insertClients(AddOrUpdateClientDTO dto) throws SQLException {
		// boolean success = false;
		String result = "";

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "INSERT INTO \r\n"
					+ "	clients (client_first_name, client_last_name, street_no, street_name, city, state, zip_code, email, phone_number)\r\n"
					+ "VALUES"
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, dto.getFirstName());
			ps.setString(2, dto.getLastName());
			ps.setString(3, dto.getStreetNo());
			ps.setString(4, dto.getStreetName());
			ps.setString(5, dto.getCity());
			ps.setString(6, dto.getState());
			ps.setString(7, dto.getZipCode());
			ps.setString(8, dto.getEmail());
			ps.setString(9, dto.getPhoneNumber());
			

			ps.execute();

			// success = true;

			result = "success";

		}

		return result;

	}

	public List<Clients> selectAllClients() throws SQLException {

		List<Clients> listOfClients = new ArrayList<>();

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM clients";
			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				listOfClients.add(new Clients(rs.getInt("client_id"), rs.getString("client_first_name"),
						rs.getString("client_last_name"), rs.getString("street_no"), rs.getString("street_name"),
						rs.getString("city"), rs.getString("state"), rs.getString("zip_code"), rs.getString("email"),
						rs.getString("phone_number")));

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

	public Clients selectClientsById(int id) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM clients WHERE client_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new Clients(rs.getInt("client_id"), rs.getString("client_first_name"),
						rs.getString("client_last_name"), rs.getString("street_no"), rs.getString("street_name"),
						rs.getString("city"), rs.getString("state"), rs.getString("zip_code"), rs.getString("email"),
						rs.getString("phone_number"));
			} else {
				return null;
			}
		}
	}

	public boolean updateClientsById(int id, AddOrUpdateClientDTO clients) throws SQLException {

		boolean success = false;

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "UPDATE clients\r\n"
					+ "SET client_first_name = ?, client_last_name = ?, street_no = ?, street_name = ?, "
					+ "city = ?, state = ?, zip_code = ?, email = ?, phone_number = ?\r\n"
					+ "WHERE\r\n"
					+ "client_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, clients.getFirstName());
			ps.setString(2, clients.getLastName());
			ps.setString(3, clients.getStreetNo());
			ps.setString(4, clients.getStreetName());
			ps.setString(5, clients.getCity());
			ps.setString(6, clients.getState());
			ps.setString(7, clients.getZipCode());
			ps.setString(8, clients.getEmail());
			ps.setString(9, clients.getPhoneNumber());
			ps.setInt(10, id);

			int i = ps.executeUpdate();

			if (i != 1) {
				throw new SQLException("Update unsuccessful!");
			} else {
				success = true;
			}

		}
		return success;
	}

	public boolean deleteClientsById(int id) throws SQLException {

		boolean success = false;

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "DELETE FROM clients WHERE client_id = ?;";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, id);

			int i = ps.executeUpdate();

			if (i != 1) {
				throw new SQLException("Delete unsuccessful!");
			} else {
				success = true;
			}

		}

		return success;
	}

}
