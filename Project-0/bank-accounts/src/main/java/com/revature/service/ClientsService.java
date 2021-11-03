package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import com.revature.dao.ClientsDAO;
import com.revature.dto.UpdateClientDTO;
import com.revature.models.Clients;

public class ClientsService {
	
	public ClientsDAO clientsDao;
	
	public ClientsService () {
		this.clientsDao = new ClientsDAO();
	}

	public String addNewClient(String firstName, String lastName) throws SQLException {
				
		Clients newClient = new Clients(firstName, lastName);
		
		return this.clientsDao.insertClients(newClient);
	}

	public List<Clients> getAllClients() throws SQLException {
		
		return this.clientsDao.selectAllClients();
	}

	public Clients getClientById(String id) throws SQLException {
		
		int client_id = Integer.parseInt(id);
		
		return this.clientsDao.selectClientsById(client_id);
	}

	public boolean removeClientById(String id) throws SQLException {

		int client_id = Integer.parseInt(id);
		
		return clientsDao.deleteClientsById(client_id);
		
	}

	public boolean modifyClientsById(String id, String firstName, String lastName) throws SQLException {
		
		int client_id = Integer.parseInt(id);
		
		//Clients modifyClient = clientsDao.selectClientsById(client_id);
		UpdateClientDTO updateClient = new UpdateClientDTO(firstName, lastName);
		
		return clientsDao.updateClientsById(client_id, updateClient);

	}

}
