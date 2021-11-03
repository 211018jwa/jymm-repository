package com.revature;

import java.sql.SQLException;

import com.revature.controller.ClientsController;
import com.revature.dao.ClientsDAO;
//import com.revature.models.Clients;
import com.revature.service.ClientsService;

import io.javalin.Javalin;

public class MainDriver {

	public static void main(String[] args) throws SQLException {
		
//		Clients client = new Clients("Jymm");
//		Clients client2 = new Clients("Jymm");
//		
//		System.out.println(client);
//		System.out.println(client.equals(client2));
//		System.out.println(client.hashCode());
		
		Javalin app = Javalin.create().start(8080);
		
		ClientsController clientsController = new ClientsController();
		
//		ClientsService clientsService = new ClientsService();
//		ClientsDAO clientsDao = new ClientsDAO();

		
				
//		System.out.println(clientsDao.gettAllClients());	
//		System.out.println(clientsDao.selectClientsById(1));
//		
//		clientsDao.insertClients(new Clients("Jymm", "Enriquez"));
//		System.out.println(clientsDao.gettAllClients());
		
		clientsController.registerEndpoint(app);
	}

}
