package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import com.revature.dao.ClientsDAO;
import com.revature.dto.UpdateClientDTO;
import com.revature.models.Clients;

public class ClientsService {
	
	public ClientsDAO clientsDao;
	
	public ClientsService (ClientsDAO clientsDao) {
		this.clientsDao = clientsDao;
	}

	public String addNewClient(String firstName, String lastName) throws SQLException {
				
		Clients newClient = new Clients(firstName, lastName);
		
		return clientsDao.insertClients(newClient);
	}

	public List<Clients> getAllClients() throws SQLException {
		
		return clientsDao.selectAllClients();
	}

	public Clients getClientById(String id) throws SQLException {
		
		int client_id = Integer.parseInt(id);
		
		return clientsDao.selectClientsById(client_id);
	}

	public boolean removeClientById(String id) throws SQLException {

		int client_id = Integer.parseInt(id);
		
		return clientsDao.deleteClientsById(client_id);
		
	}

	public boolean modifyClientsById(String id, UpdateClientDTO client) throws SQLException {
		
		int client_id = Integer.parseInt(id);
		
		//Clients modifyClient = clientsDao.selectClientsById(client_id);
		
		return clientsDao.updateClientsById(client_id, client);

	}

}
