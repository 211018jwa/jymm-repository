package com.revature;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controller.ClientsController;
import com.revature.controller.ExceptionMappingController;

import io.javalin.Javalin;

public class MainDriver {

	public static void main(String[] args) throws SQLException {

//		Clients client = new Clients("Jymm");
//		Clients client2 = new Clients("Jymm");
//		
//		System.out.println(client);
//		System.out.println(client.equals(client2));
//		System.out.println(client.hashCode());

//		ClientsService clientsService = new ClientsService();
//		ClientsDAO clientsDao = new ClientsDAO();

//		System.out.println(clientsDao.gettAllClients());	
//		System.out.println(clientsDao.selectClientsById(1));
//		
//		clientsDao.insertClients(new Clients("Jymm", "Enriquez"));
//		System.out.println(clientsDao.gettAllClients());		

		Javalin app = Javalin.create();

		Logger logger = LoggerFactory.getLogger(MainDriver.class);

		app.before(ctx -> {
			logger.info(ctx.method() + " request received to the " + ctx.path() + " endpoint");
		});

		ClientsController clientsController = new ClientsController();

		clientsController.registerEndpoint(app);

		ExceptionMappingController exceptionController = new ExceptionMappingController();

		exceptionController.mapExceptions(app);

		app.start(8080);

	}

}
