package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import com.revature.dao.ClientsDAO;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.Clients;

public class ClientsService {

	public ClientsDAO clientsDao;

	public ClientsService() {
		this.clientsDao = new ClientsDAO();
	}

	public Clients addNewClient(AddOrUpdateClientDTO addDto) throws SQLException, InvalidInputException {
		
		if (addDto.getFirstName().trim().equals("") || addDto.getLastName().trim().equals("") || addDto.getStreetAddress().trim().equals("") ||
				addDto.getCity().trim().equals("") || addDto.getState().trim().equals("") || addDto.getZipCode().trim().equals("") || 
				addDto.getEmail().trim().equals("") || addDto.getPhoneNumber().trim().equals("")) {
			throw new InvalidInputException ("All fields cannot be empty!");
		}
	
		Clients insertClients = this.clientsDao.insertClients(addDto);

		return insertClients;
	}

	public List<Clients> getAllClients() throws SQLException {

		return this.clientsDao.selectAllClients();
	}

	public Clients getClientById(String id) throws SQLException, InvalidInputException, ClientNotFoundException {

		try {
			int clientsId = Integer.parseInt(id);

			Clients c = this.clientsDao.selectClientsById(clientsId);

			if (c == null) {
				throw new ClientNotFoundException("Client id of " + id + " does not exist!");
			}
			return c;

		} catch (NumberFormatException e) {
			throw new InvalidInputException("Entered id cannot be converted to int value! ");
		}

	}

	public boolean removeClientById(String id) throws SQLException {

		int client_id = Integer.parseInt(id);

		return clientsDao.deleteClientsById(client_id);

	}

	public Clients modifyClientsById(String cId, AddOrUpdateClientDTO dto) throws SQLException, InvalidInputException, ClientNotFoundException {

		try {
			int clientsId = Integer.parseInt(cId);

			Clients id = this.clientsDao.selectClientsById(clientsId);

			if (id == null) {
				throw new ClientNotFoundException("Client id of " + clientsId + " does not exist!");
			}
			
			Clients updateClients = this.clientsDao.updateClientsById(clientsId, dto);

			return updateClients;
			
		} catch (NumberFormatException e) {
			throw new InvalidInputException("Entered id cannot be converted to int value!");
		}

		// Clients modifyClient = clientsDao.selectClientsById(client_id);
		// AddOrUpdateClientDTO updateClient = new AddOrUpdateClientDTO(firstName,
		// lastName);

	}

}
