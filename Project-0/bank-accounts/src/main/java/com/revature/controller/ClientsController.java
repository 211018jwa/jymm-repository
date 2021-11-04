package com.revature.controller;

import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.service.ClientsService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientsController {

	public ClientsService clientsService;

	public ClientsController() {

		this.clientsService = new ClientsService();
	}

	public Handler clients = (ctx) -> {
		// ctx.result("cleints lambda invoke");

//		String firstName = ctx.formParam("firstName");
//		String lastName = ctx.formParam("lastName");
//		
		AddOrUpdateClientDTO addDto = ctx.bodyAsClass(AddOrUpdateClientDTO.class);

		ctx.result(this.clientsService.addNewClient(addDto));

	};

	public Handler getAllClients = (ctx) -> {

		ctx.json(this.clientsService.getAllClients());

	};

	public Handler getClientById = (ctx) -> {
		
		String id = ctx.pathParam("client_id");

		ctx.json(this.clientsService.getClientById(id));

	};
	
	public Handler updateClientsById = (ctx) -> {
		
		String id = ctx.pathParam("client_id");
		
		int clients_id = Integer.parseInt(id);
		
//		String firstName = ctx.formParam("firstName");
//		String lastName = ctx.formParam("lastName");
		
		AddOrUpdateClientDTO dto = ctx.bodyAsClass(AddOrUpdateClientDTO.class);
		
//		if (this.clientsService.modifyClientsById(clients_id, firstName, lastName) {
//			ctx.result("success");
//		}
		if (this.clientsService.modifyClientsById(clients_id, dto)) {
			ctx.result("success");
		} else {
			ctx.status(404);
		}
		
	};
	
	public Handler deleteClientById = (ctx) -> {
		
		String id = ctx.pathParam("client_id");
		
		if (this.clientsService.removeClientById(id)) {
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
