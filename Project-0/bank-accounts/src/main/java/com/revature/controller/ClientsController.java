package com.revature.controller;

import com.revature.dto.UpdateClientDTO;
import com.revature.service.ClientsService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientsController {

	public ClientsService clientsService;

	public ClientsController(ClientsService clientsService) {

		this.clientsService = clientsService;
	}

	public Handler clients = (ctx) -> {
		// ctx.result("cleints lambda invoke");

		String firstName = ctx.formParam("firstName");
		String lastName = ctx.formParam("lastName");

		ctx.result(clientsService.addNewClient(firstName, lastName));

	};

	public Handler getAllClients = (ctx) -> {

		ctx.json(clientsService.getAllClients());

	};

	public Handler getClientById = (ctx) -> {
		
		String id = ctx.pathParam("client_id");

		ctx.json(clientsService.getClientById(id));

	};
	
	public Handler updateClientsById = (ctx) -> {
		
		String id = ctx.pathParam("client_id");
		String firstName = ctx.formParam("firstName");
		String lastName = ctx.formParam("lastName");
		
		UpdateClientDTO updateClient = new UpdateClientDTO(firstName, lastName);
		
		
		if (clientsService.modifyClientsById(id, updateClient)) {
			ctx.result("sucess");
		}
		
	};
	
	public Handler deleteClientById = (ctx) -> {
		
		String id = ctx.pathParam("client_id");
		
		if (clientsService.removeClientById(id)) {
			ctx.result("success");
		}
		
	};

	public void registerEndpoint(Javalin app) {
		app.post("/clients", clients);
		app.get("/clients", getAllClients);
		app.get("/clients/{client_id}", getClientById);
		app.put("/clients/{client_id}", updateClientsById);
		app.delete("/clients/{client_id}", deleteClientById);
	}
}