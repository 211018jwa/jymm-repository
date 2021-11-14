package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.ClientsDAO;
import com.revature.dto.AddOrUpdateBankAccountDTO;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.BankAccounts;
import com.revature.models.Clients;

public class ClientsService {

	Logger logger = LoggerFactory.getLogger(ClientsService.class);

	public ClientsDAO clientsDao;

	public ClientsService() {
		this.clientsDao = new ClientsDAO();
	}

	public ClientsService(ClientsDAO clientsDao) {
		this.clientsDao = clientsDao;
	}

	public Clients addNewClient(AddOrUpdateClientDTO addDto) throws SQLException, InvalidInputException {

		logger.info("invoked addNewClient() method");

		if (addDto.getFirstName().trim().equals("") || addDto.getLastName().trim().equals("")
				|| addDto.getStreetAddress().trim().equals("") || addDto.getCity().trim().equals("")
				|| addDto.getState().trim().equals("") || addDto.getZipCode().trim().equals("")
				|| addDto.getEmail().trim().equals("") || addDto.getPhoneNumber().trim().equals("")) {
			logger.warn("InvalidInputException was thrown: " + "All fields cannot be empty!");

			throw new InvalidInputException("All fields cannot be empty!");
		}

		Clients insertClients = this.clientsDao.insertClients(addDto);

		return insertClients;
	}

	public List<Clients> getAllClients() throws SQLException {

		logger.info("invoked getAllClients() method");

		return this.clientsDao.selectAllClients();
	}

	public Clients getClientById(String id) throws SQLException, InvalidInputException, ClientNotFoundException {

		logger.info("invoked getClientById() method");

		try {
			int clientsId = Integer.parseInt(id);

			Clients c = this.clientsDao.selectClientsById(clientsId);

			if (c == null) {
				logger.warn("ClientNotFoundException was thrown: " + "Client id does not exist!");
				throw new ClientNotFoundException("Client id of " + id + " does not exist!");
			}
			return c;

		} catch (NumberFormatException e) {
			logger.warn("InvalidInputException was thrown: " 
		+ "Entered id cannot be converted to int value!");
			throw new InvalidInputException("Entered id cannot be converted to int value!");
		}

	}

	public boolean removeClientById(String id) throws SQLException, ClientNotFoundException, InvalidInputException {
		
		logger.info("invoked removeClientById() method");

		try {
			int clientsId = Integer.parseInt(id);

			Clients c = this.clientsDao.selectClientsById(clientsId);

			if (c == null) {
				logger.warn("ClientNotFoundException was thrown: " 
						+"Can't delete a client because the client does not exist");
				throw new ClientNotFoundException(
						"Can't delete a client with the id of " + id + " because the client does not exist");
			}

			return (this.clientsDao.deleteClientsById(clientsId));

		} catch (NumberFormatException e) {
			logger.warn("InvalidInputException was thrown: " 
					+"Entered id cannot be converted to int value!");
			throw new InvalidInputException("Entered id cannot be converted to int value!");
		}
	}

	public Clients modifyClientsById(String cId, AddOrUpdateClientDTO dto)
			throws SQLException, InvalidInputException, ClientNotFoundException {
		
		logger.info("invoked modifyClientById() method");
		
		try {
			int clientsId = Integer.parseInt(cId);

			Clients id = this.clientsDao.selectClientsById(clientsId);

			if (id == null) {
				logger.warn("ClientNotFoundException was thrown: " 
						+"Can't modify a client because the client does not exist");
				throw new ClientNotFoundException("Client id of " + clientsId + " does not exist!");
			}

			Clients updateClients = this.clientsDao.updateClientsById(clientsId, dto);

			return updateClients;

		} catch (NumberFormatException e) {
			logger.warn("InvalidInputException was thrown: " 
					+"Entered id cannot be converted to int value!");
			throw new InvalidInputException("Entered id cannot be converted to int value!");
		}

		// Clients modifyClient = clientsDao.selectClientsById(client_id);
		// AddOrUpdateClientDTO updateClient = new AddOrUpdateClientDTO(firstName,
		// lastName);

	}

}
