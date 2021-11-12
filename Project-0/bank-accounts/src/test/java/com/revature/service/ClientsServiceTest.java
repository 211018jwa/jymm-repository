package com.revature.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.ClientsDAO;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.Clients;

public class ClientsServiceTest {

	private ClientsService sut;

	/*
	 * ClientsService's addNewClient() test
	 */

	// Happy Path
	@Test
	public void testAddNewClientInDTOPositive() throws SQLException, InvalidInputException {

		// ARRANGE
		ClientsDAO clientsDao = mock(ClientsDAO.class);

		AddOrUpdateClientDTO dtoToDao = new AddOrUpdateClientDTO("Jymm", "Enriquez", "3737 Oday Parkway",
				"Corpus Christi", "TX", "78415", "jymm.enriquez@revature.net", "3615491621");

		when(clientsDao.insertClients(eq(dtoToDao))).thenReturn(new Clients(1, "Jymm", "Enriquez", "3737 Oday Parkway",
				"Corpus Christi", "TX", "78415", "jymm.enriquez@revature.net", "3615491621"));

		ClientsService clientsService = new ClientsService(clientsDao);

		// ACT
		AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO("Jymm", "Enriquez", "3737 Oday Parkway", "Corpus Christi",
				"TX", "78415", "jymm.enriquez@revature.net", "3615491621");
		Clients actual = clientsService.addNewClient(dto);

		// ASSERT
		Clients expected = (new Clients(1, "Jymm", "Enriquez", "3737 Oday Parkway", "Corpus Christi", "TX", "78415",
				"jymm.enriquez@revature.net", "3615491621"));
		Assertions.assertEquals(expected, actual);

	}

	/*
	 * ClientsService's getAllClients() test
	 */
	// Happy Path
	@Test
	public void testGetAllClients() throws SQLException {

		// ARRANGE
		ClientsDAO mockClientsDao = mock(ClientsDAO.class);

		Clients client1 = new Clients(1, "Jymm", "Enriquez", "3737 Oday Parkway", "Corpus Christi", "TX", "78415",
				"jymm.enriquez@revature.net", "3615491621");
		Clients client2 = new Clients(2, "John", "Doe", "1234 Main Street", "Corpus Christi", "TX", "78414",
				"john.doe@gmial.com", "3617945136");
		Clients client3 = new Clients(3, "Jane", "Doe", "4321 Other Street", "Corpus Christi", "TX", "78414",
				"jane.doe@revature.net", "3615794583");

		List<Clients> clientsList = new ArrayList<>();
		clientsList.add(client1);
		clientsList.add(client2);
		clientsList.add(client3);

		when(mockClientsDao.selectAllClients()).thenReturn(clientsList);
		ClientsService clientsService = new ClientsService(mockClientsDao);

		// ACT
		List<Clients> actual = clientsService.getAllClients();

		// ASSERT
		List<Clients> expected = new ArrayList<>();
		expected.add(new Clients(1, "Jymm", "Enriquez", "3737 Oday Parkway", "Corpus Christi", "TX", "78415",
				"jymm.enriquez@revature.net", "3615491621"));
		expected.add(new Clients(2, "John", "Doe", "1234 Main Street", "Corpus Christi", "TX", "78414",
				"john.doe@gmial.com", "3617945136"));
		expected.add(new Clients(3, "Jane", "Doe", "4321 Other Street", "Corpus Christi", "TX", "78414",
				"jane.doe@revature.net", "3615794583"));

		Assertions.assertEquals(expected, actual);

	}

	/*
	 * ClientsService's getClientById test
	 */

	// Happy Path
	@Test
	public void testGetClientByIdValid() throws SQLException, InvalidInputException, ClientNotFoundException {

		// ARRANGE
		ClientsDAO mockClientsDao = mock(ClientsDAO.class);

		when(mockClientsDao.selectClientsById(eq(1))).thenReturn(new Clients(1, "Jymm", "Enriquez", "3737 Oday Parkway",
				"Corpus Christi", "TX", "78415", "jymm.enriquez@revature.net", "3615491621"));

		ClientsService clientsService = new ClientsService(mockClientsDao);

		// ACT
		Clients actual = clientsService.getClientById("1");

		// ASSERT
		Assertions.assertEquals(new Clients(1, "Jymm", "Enriquez", "3737 Oday Parkway", "Corpus Christi", "TX", "78415",
				"jymm.enriquez@revature.net", "3615491621"), actual);
	}

	// Sad Path (Negative Test)
	@Test
	public void testGetClientByIdNotValid() throws ClientNotFoundException {

		// ARRANGE
		ClientsDAO mockClientsDao = mock(ClientsDAO.class);

		ClientsService clientsService = new ClientsService(mockClientsDao);

		// ACT AND ASSERT

		Assertions.assertThrows(ClientNotFoundException.class, () -> {
			clientsService.getClientById("1");
		});
	}

	// Sad Path (Negative Test)
	@Test
	public void testGetClientByIdNotIntType() throws InvalidInputException {

		// ARRANGE
		ClientsDAO mockClientsDao = mock(ClientsDAO.class);

		ClientsService clientsService = new ClientsService(mockClientsDao);

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidInputException.class, () -> {
			clientsService.getClientById("abc");
		});
	}

	/*
	 * ClientsService's removeClientById() test
	 */

	// Happy Path
	@Test
	public void testRemoveClientById() throws SQLException, InvalidInputException, ClientNotFoundException {

		// ARRANGE
		ClientsDAO mockClientsDao = mock(ClientsDAO.class);

		// Checking if a client exist, if it does...
		when(mockClientsDao.selectClientsById(eq(1))).thenReturn(new Clients(1, "Jymm", "Enriquez", "3737 Oday Parkway",
				"Corpus Christi", "TX", "78415", "jymm.enriquez@revature.net", "3615491621"));

		// Input the clientId in this method
		when(mockClientsDao.deleteClientsById(eq(1))).thenReturn(true);

		ClientsService clientsService = new ClientsService(mockClientsDao);

		// ACT
		boolean expected = clientsService.removeClientById("1");

		Assertions.assertTrue(expected);
	}

	// Sad Path (Negative Test)
	@Test
	public void testRemoveClientByIdNegativeClientDoesNotExist() throws ClientNotFoundException {

		// ARRANGE
		ClientsDAO mockClientsDao = mock(ClientsDAO.class);

		ClientsService clientsService = new ClientsService(mockClientsDao);

		// ACT AND ASSERT
		Assertions.assertThrows(ClientNotFoundException.class, () -> {
			clientsService.removeClientById("1");
		});
	}

	// Sad Path (Negative Test)
	@Test
	public void testRemoveClientByIdNotIntType() throws InvalidInputException {

		// ARRANGE
		ClientsDAO mockClientsDao = mock(ClientsDAO.class);

		ClientsService clientsService = new ClientsService(mockClientsDao);

		// ACT AND ASSERT
		Assertions.assertThrows(InvalidInputException.class, () -> {
			clientsService.getClientById("abc");
		});
	}

	/*
	 * ClientsService's modifyClientById() tests
	 */
	
	//Happy Path
	@Test
	public void testModifyClientByIdPositive() throws SQLException, InvalidInputException, ClientNotFoundException {
		
		//ARRANGE
		ClientsDAO mockClientsDao = mock (ClientsDAO.class);
		
		when(mockClientsDao.selectClientsById(eq(1))).thenReturn(new Clients(1, "Jymm", "Enriquez", "3737 Oday Parkway",
				"Houston", "TX", "77001", "jymm.enriquez@revature.net", "3619452161"));
		
		AddOrUpdateClientDTO updateClientDto = new AddOrUpdateClientDTO("Jymm Allexzydd", "Enriquez", "3737 Oday Parkway",
				"Corpus Christi", "TX", "78415", "jymm.enriquez@revature.net", "3615491621");
		
		when(mockClientsDao.updateClientsById(eq(1), eq(updateClientDto))).thenReturn(new Clients(1, "Jymm Allexzydd", "Enriquez", 
				"3737 Oday Parkway", "Corpus Christi", "TX", "78415", "jymm.enriquez@revature.net", "3615491621"));
		
		ClientsService clientsService = new ClientsService(mockClientsDao);
		
		//ACT
		AddOrUpdateClientDTO updatedDto = new AddOrUpdateClientDTO("Jymm Allexzydd", "Enriquez", "3737 Oday Parkway",
				"Corpus Christi", "TX", "78415", "jymm.enriquez@revature.net", "3615491621");
		
		Clients actual = clientsService.modifyClientsById("1", updatedDto);
		
		Clients expected = new Clients(1, "Jymm Allexzydd", "Enriquez", 
				"3737 Oday Parkway", "Corpus Christi", "TX", "78415", "jymm.enriquez@revature.net", "3615491621");
		
		//ASSERT
		Assertions.assertEquals(expected, actual);	
	}
	
	
}
