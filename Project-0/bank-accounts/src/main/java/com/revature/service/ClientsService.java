package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import com.revature.dao.ClientsDAO;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.models.Clients;

public class ClientsService {
	
	public ClientsDAO clientsDao;
	
	public ClientsService () {
		this.clientsDao = new ClientsDAO();
	}

	public String addNewClient(AddOrUpdateClientDTO addDto) throws SQLException {
		
	
		return this.clientsDao.insertClients(addDto);
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

	public boolean modifyClientsById(int clientId, AddOrUpdateClientDTO dto) throws SQLException {
		
		//int client_id = Integer.parseInt(id);
		
		Clients id = this.clientsDao.selectClientsById(clientId);
		
		if (id == null) {
			throw new SQLException("Client id of " +id+ " does not exist!");
		}
	
		//Clients modifyClient = clientsDao.selectClientsById(client_id);
		//AddOrUpdateClientDTO updateClient = new AddOrUpdateClientDTO(firstName, lastName);
		
		return clientsDao.updateClientsById(clientId, dto);

	}

}
