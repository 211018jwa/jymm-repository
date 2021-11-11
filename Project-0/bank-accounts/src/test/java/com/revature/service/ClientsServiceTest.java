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
import com.revature.exceptions.InvalidInputException;
import com.revature.models.Clients;

public class ClientsServiceTest {

	private ClientsService sut;

	/*
	 * ClientsService's addNewClient() test
	 */

	// Happy Path
	@Test
	public void testAddNewClientInDTO() throws SQLException, InvalidInputException {

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
	//Happy Path
	@Test
	public void testGetAllClients() throws SQLException {

		//ARRANGE
		ClientsDAO mockClientsDao = mock(ClientsDAO.class);
		
		Clients client1 = new Clients(1, "Jymm", "Enriquez", "3737 Oday Parkway",
				"Corpus Christi", "TX", "78415", "jymm.enriquez@revature.net", "3615491621");
		Clients client2 = new Clients(2, "John", "Doe", "1234 Main Street",
				"Corpus Christi", "TX", "78414", "john.doe@gmial.com", "3617945136");
		Clients client3 = new Clients(3, "Jane", "Doe", "4321 Other Street",
				"Corpus Christi", "TX", "78414", "jane.doe@revature.net", "3615794583");
		
		List<Clients> clientsList = new ArrayList<>();
		clientsList.add(client1);
		clientsList.add(client2);
		clientsList.add(client3);
		
		when(mockClientsDao.selectAllClients()).thenReturn(clientsList);
		ClientsService clientsService = new ClientsService(mockClientsDao);
		
		//ACT
		List<Clients> actual = clientsService.getAllClients();
		
		//ASSERT
		List<Clients> expected = new ArrayList<>();
		expected.add(new Clients(1, "Jymm", "Enriquez", "3737 Oday Parkway",
				"Corpus Christi", "TX", "78415", "jymm.enriquez@revature.net", "3615491621"));
		expected.add(new Clients(2, "John", "Doe", "1234 Main Street",
				"Corpus Christi", "TX", "78414", "john.doe@gmial.com", "3617945136"));
		expected.add(new Clients(3, "Jane", "Doe", "4321 Other Street",
				"Corpus Christi", "TX", "78414", "jane.doe@revature.net", "3615794583"));
		
		Assertions.assertEquals(expected, actual);

	}

}
